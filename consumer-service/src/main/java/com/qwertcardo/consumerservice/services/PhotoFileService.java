package com.qwertcardo.consumerservice.services;

import com.qwertcardo.basedomain.domain.PhotoFile;
import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.consumerservice.repositories.PhotoFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoFileService {

    @Autowired
    private PhotoFileRepository photoFileRepository;

    public void publishPhotoFiles(List<PhotoFile> photoFiles, Publication publication) {
        photoFiles.forEach(file -> {
            file.setPublication(publication);
            this.photoFileRepository.save(file);
        });
    }

    public void deletePhotoFilesByPublicationId(Long id) {
        this.photoFileRepository.deletePhotoFilesByPublicationId(id);
    }
}
