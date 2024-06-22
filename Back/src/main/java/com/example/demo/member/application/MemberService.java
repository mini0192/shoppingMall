package com.example.demo.member.application;

import com.example.demo.config.ValidationService;
import com.example.demo.config.exceotion.DuplicateDataException;
import com.example.demo.config.exceotion.NotFountDataException;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDetails;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.member.presentation.MemberDto;
import com.example.demo.config.Status;
import com.example.demo.config.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto add(MemberDto takenMemberDto) {
        if(memberRepository.existsByUsername(takenMemberDto.getUsername()))
            throw new DuplicateDataException("아이디가 중복되었습니다.");

        takenMemberDto.setPassword(passwordEncoder.encode(takenMemberDto.getPassword()));
        takenMemberDto.setLocked(Status.FALSE.getStatus());
        Member takenMember = MemberDto.toEntity(takenMemberDto, List.of(Role.USER.getRole()));

        validationService.checkValid(takenMember);
        memberRepository.save(takenMember);

        return takenMemberDto;
    }

    public Page<MemberDto> findAll(Pageable takenPageable, int takenCount, String takenLocked) {
        int page = takenPageable.getPageNumber() - 1;
        int pageLimit = takenCount;
        Page<Member> savedMemberPage;

        if(takenLocked.isBlank())
            savedMemberPage =
                    memberRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        else
            savedMemberPage =
                    memberRepository.findByLocked(takenLocked, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));


        return savedMemberPage.map(MemberDto::toDto);
    }

    public MemberDto getUserData(Authentication takenAuthentication) {
        MemberDetails memberDetails = (MemberDetails) takenAuthentication.getPrincipal();
        return MemberDto.builder()
                .name(memberDetails.getName())
                .username(memberDetails.getUsername())
                .build();
    }

    @Transactional
    public void lock(Long id, String locked) {
        Member savedMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFountDataException("유저가 존재하지 않습니다"));
        savedMember.updateLockedStatus(locked);
        memberRepository.save(savedMember);
    }

    @Transactional
    public void updateRole(Long id, String admin) {
        Member savedMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFountDataException("유저가 존재하지 않습니다"));
        List<String> savedRoleList = savedMember.getRole();

        if(admin.equals(Status.TRUE.getStatus())) {
            if(savedRoleList.contains(Role.ADMIN.getRole())) return;
            savedRoleList.add(Role.ADMIN.getRole());
        } else if(admin.equals(Status.FALSE.getStatus())) {
            savedRoleList.remove(Role.ADMIN.getRole());
        }

        savedMember.updateRole(savedRoleList);
        memberRepository.save(savedMember);
    }
}
