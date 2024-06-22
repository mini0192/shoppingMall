package com.example.demo.member.infrastructure;

import com.example.demo.itemDomain.item.domain.Item;
import com.example.demo.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Page<Member> findByLocked(String locked, PageRequest page);
    boolean existsByUsername(String username);
}
