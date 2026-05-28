package com.pos.repository;

import com.pos.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByPhone(String phone);
    Page<Member> findByNameContaining(String name, Pageable pageable);
    Page<Member> findByMemberNoContaining(String memberNo, Pageable pageable);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.status = 1")
    long countActive();
}
