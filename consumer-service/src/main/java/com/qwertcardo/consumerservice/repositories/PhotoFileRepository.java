package com.qwertcardo.consumerservice.repositories;

import com.qwertcardo.basedomain.domain.PhotoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoFileRepository extends JpaRepository<PhotoFile, Long> {
}
