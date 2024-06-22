package com.example.demo.member.presentation;

import com.example.demo.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String locked;
    private List<String> role;

    public static Member toEntity(MemberDto memberDto, List<String> role) {
        return Member.builder()
                .name(memberDto.getName())
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .locked(memberDto.locked)
                .role(role)
                .build();
    }

    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .username(member.getUsername())
                .locked(member.getLocked())
                .role(member.getRole())
                .build();
    }
}
