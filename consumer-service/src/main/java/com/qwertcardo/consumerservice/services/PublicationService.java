package com.qwertcardo.consumerservice.services;

import com.qwertcardo.basedomain.domain.PhotoFile;
import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.basedomain.domain.enums.PublicationTypeEnum;
import com.qwertcardo.basedomain.kafka.KafkaMessage;
import com.qwertcardo.consumerservice.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {
    private static final Long INITIAL_VIZUALIZATIONS = 0L;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PhotoFileService photoFileService;

    // METHODS FROM KAFKA

    public void publish(KafkaMessage<Publication> kafkaMessage) {
        List<PhotoFile> photoFiles = kafkaMessage.getEntity().getFiles();

        Publication publication = kafkaMessage.getEntity();
        publication.setVisualizations(INITIAL_VIZUALIZATIONS);
        publication.setType(PublicationTypeEnum.PUBLICATION);
        publication.setComments(Collections.EMPTY_LIST);
        publication.setFiles(Collections.EMPTY_LIST);
        publication.setLikes(Collections.EMPTY_LIST);
        publication.setCreationDate(new Date());
        publication.setReference(null);

        publication = this.publicationRepository.save(publication);

        this.photoFileService.publishPhotoFiles(photoFiles, publication);
    }

    public void delete(KafkaMessage<Publication> kafkaMessage) {
        List<PhotoFile> photoFiles = kafkaMessage.getEntity().getFiles();
        this.photoFileService.deletePhotoFiles(photoFiles);

        Publication publication = kafkaMessage.getEntity();
        this.publicationRepository.deleteById(publication.getId());
    }

    public void publishComment(KafkaMessage<Publication> kafkaMessage) {
        Optional<Publication> publication = this.publicationRepository.findById(((Integer) kafkaMessage.getProperties().get("publication")).longValue());
        if (publication.isPresent()) {
            Publication comment = kafkaMessage.getEntity();
            comment.setVisualizations(null);
            comment.setType(PublicationTypeEnum.COMMENT);
            comment.setComments(Collections.EMPTY_LIST);
            comment.setFiles(Collections.EMPTY_LIST);
            comment.setLikes(Collections.EMPTY_LIST);
            comment.setCreationDate(new Date());
            comment.setReference(publication.get());

            this.publicationRepository.save(comment);
        }
    }

    public void deleteComment(KafkaMessage<Publication> kafkaMessage) {
        Publication comment = kafkaMessage.getEntity();
        this.publicationRepository.deleteById(comment.getId());
    }

    public void visualize(KafkaMessage<Publication> kafkaMessage) {
        Optional<Publication> publication = this.publicationRepository.findById(kafkaMessage.getEntity().getId());
        if (publication.isPresent()) {
            this.publicationRepository.deleteById(publication.get().getId());
        }
    }

    // METHODS FROM CONTROLLER

    public List<Publication> findAll() {
        return this.publicationRepository.findAll();
    }
}
