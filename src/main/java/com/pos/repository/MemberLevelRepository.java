package com.pos.repository;

import com.pos.entity.MemberLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberLevelRepository extends JpaRepository<MemberLevel, Long> {
    List<MemberLevel> findByStatusOrderByLevel(Integer status);
}
