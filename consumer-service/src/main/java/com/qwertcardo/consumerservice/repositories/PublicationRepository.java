package com.qwertcardo.consumerservice.repositories;

import com.qwertcardo.basedomain.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
