package com.example.demo.security.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDetails;
import com.example.demo.security.JwtProvider;
import com.example.demo.security.refreshToken.RefreshToken;
import com.example.demo.security.refreshToken.RefreshTokenRepository;
import com.example.demo.security.refreshToken.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtProvider.HEADER_STRING);
        if(header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = header.replace(JwtProvider.TOKEN_PREFIX, "");
            DecodedJWT decodedJWT = JwtProvider.decodeJwtToken(jwtToken);

            MemberDetails memberDetails = new MemberDetails(getMember(decodedJWT));

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch(TokenExpiredException e) {
            log.info("만료된 JWT Token");

            Member member = tokenReissue(request);
            if(member == null) {
                log.info("만료된 JWT Token 재발급 실패");
                return;
            }

            MemberDetails RefreshMemberDetails = new MemberDetails(member);

            String refreshJwtToken = JwtProvider.TOKEN_PREFIX + JwtProvider.createJwtToken(RefreshMemberDetails);
            response.setHeader(JwtProvider.HEADER_STRING, refreshJwtToken);
            log.info("만료된 JWT Token 재발급 완료");

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(RefreshMemberDetails, null, RefreshMemberDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch(JWTDecodeException e) {
            log.info("Token 값이 잘못되었습니다.");

        } catch (JWTVerificationException e) {
            log.info("Token 인증이 실패하였습니다.");

        } finally {
            filterChain.doFilter(request, response);

        }
    }

    private Member tokenReissue(HttpServletRequest request) {
        String header = request.getHeader(JwtProvider.REFRESH_HEADER_STRING);

        if(header == null) {
            log.error("Refresh Token이 존재하지 않아 토큰 재발급에 실패했습니다.");
            return null;
        }

        String refreshToken = header.replace(JwtProvider.TOKEN_PREFIX, "");

        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.getToken(refreshToken);
        if(optionalRefreshToken.isEmpty()) return null;
        RefreshToken savedRefreshToken = optionalRefreshToken.get();
        if(savedRefreshToken.getCount() >= 3) {
            refreshTokenService.delete(savedRefreshToken);
            log.error("재발급 횟수가 초과한 Refresh Token 삭제");
            return null;
        }

        Member savedMember = Member.builder()
                .username(savedRefreshToken.getUsername())
                .name(savedRefreshToken.getName())
                .role(savedRefreshToken.getRole())
                .build();

        DecodedJWT decodedRefreshToken = JwtProvider.decodeJwtToken(refreshToken);
        Member tokenMember = getMember(decodedRefreshToken);

        refreshTokenService.addCount(savedRefreshToken);

        if(Member.equals(savedMember, tokenMember)) return savedMember;

        return null;
    }

    private Member getMember(DecodedJWT decodedToken) {
        String username = decodedToken.getClaim("username").toString();
        username = username.replace("\"", "");

        String name = decodedToken.getClaim("name").toString();
        name = name.replace("\"", "");

        List<String> roleList = decodedToken.getClaim("role").asList(String.class);

        return Member.builder()
                .username(username)
                .name(name)
                .role(roleList)
                .build();
    }
}
