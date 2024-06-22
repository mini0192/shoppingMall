package com.example.demo.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 1, max = 100)
    private String name;

    @Column
    @Size(min = 1, max = 100)
    private String username;

    @Column
    @Size(min = 1, max = 100)
    private String password;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role;

    @Column
    private String locked;

    public void updateLockedStatus(String locked) {
        this.locked = locked;
    }

    public void updateRole(List<String> role) {
        this.role = role;
    }

    public static boolean equals(Member m1, Member m2) {
        if(!m1.getUsername().equals(m2.getUsername())) return false;
        if(!m1.getName().equals(m2.getName())) return false;
        if(!Arrays.equals(m1.getRole().toArray(), m2.getRole().toArray())) return false;

        return true;
    }
}
