package com.example.demo.security.refreshToken;

import com.example.demo.member.domain.MemberDetails;
import com.example.demo.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> getToken(String takenToken) {
        return refreshTokenRepository.findByToken(takenToken);
    }

    @Transactional
    public void save(String takenRefreshToken, MemberDetails takenMemberDetails) {
        String username = takenMemberDetails.getUsername();
        String name = takenMemberDetails.getName();

        Collection<? extends GrantedAuthority> roleList = takenMemberDetails.getAuthorities();
        List<String> role = roleList.stream().map(GrantedAuthority::getAuthority).toList();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(takenRefreshToken)
                .username(username)
                .name(name)
                .role(role)
                .build();

        refreshToken.setTime(JwtProvider.REFRESH_TOKEN_EXPIRATION_TIME);

        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void deleteExpirationToken() {
        List<RefreshToken> savedRefreshToken = refreshTokenRepository.findAllByOrderByExpirationTime();
        long time = System.currentTimeMillis();

        for(RefreshToken token : savedRefreshToken) {
            long tokenExpirationTime = token.getExpirationTime();
            if(time < tokenExpirationTime) break;
            refreshTokenRepository.delete(token);
            log.info("만료된 토큰 삭제");
        }
    }

    @Transactional
    public void delete(RefreshToken takenRefreshToken) {
        refreshTokenRepository.delete(takenRefreshToken);
    }

    @Transactional
    public void addCount(RefreshToken takenRefreshToken) {
        takenRefreshToken.addCount();
        refreshTokenRepository.save(takenRefreshToken);
    }
}
