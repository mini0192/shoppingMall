package com.example.demo.security.refreshToken;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @Column(unique = true)
    private String token;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private long createTime;

    @Column(nullable = false)
    private long expirationTime;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role;

    public void setTime(int expirationTime) {
        this.createTime = System.currentTimeMillis();
        this.expirationTime = this.createTime + expirationTime;
    }

    public void addCount() {
        this.count++;
    }
}
