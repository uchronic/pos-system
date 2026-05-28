package com.pos.repository;

import com.pos.entity.PickupCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupCardRepository extends JpaRepository<PickupCard, Long> {
    PickupCard findByCardNo(String cardNo);
}
