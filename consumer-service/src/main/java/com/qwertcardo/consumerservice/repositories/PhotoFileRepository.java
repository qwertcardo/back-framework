package com.qwertcardo.consumerservice.repositories;

import com.qwertcardo.basedomain.domain.PhotoFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoFileRepository extends JpaRepository<PhotoFile, Long> {

    @Modifying
    @Query(value = "delete from photo_file where publication_id = :id", nativeQuery = true)
    public void deletePhotoFilesByPublicationId(@Param("id") Long id);
}
