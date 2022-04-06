package com.qwertcardo.consumerservice.repositories;

import com.qwertcardo.basedomain.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
