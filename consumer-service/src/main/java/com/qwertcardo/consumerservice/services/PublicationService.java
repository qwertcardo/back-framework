package com.qwertcardo.consumerservice.services;

import com.qwertcardo.basedomain.domain.PhotoFile;
import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.basedomain.domain.enums.PublicationTypeEnum;
import com.qwertcardo.basedomain.kafka.KafkaMessage;
import com.qwertcardo.consumerservice.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PublicationService {
    private static final Long INITIAL_VIZUALIZATIONS = 0L;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PhotoFileService photoFileService;

    @Autowired
    private LikeService likeService;

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
        Optional<Publication> optional = this.publicationRepository.findById(kafkaMessage.getEntity().getId());
        if (optional.isPresent()) {
            Publication publication = optional.get();

            this.photoFileService.deletePhotoFilesByPublicationId(publication.getId());
            deleteCommentsByMainPublicationId(publication.getId());
            this.publicationRepository.deleteById(publication.getId());
        }
    }

    private void deleteCommentsByMainPublicationId(Long id) {
        Optional<Publication> optional = this.publicationRepository.findById(id);
        if (optional.isPresent()) {
            Publication publication = optional.get();

            for (Publication comment : publication.getComments()) {
                this.likeService.deleteLikesFromPublicationId(comment.getId());
                this.publicationRepository.deleteById(comment.getId());
            }
        }
    }

    public void publishComment(KafkaMessage<Publication> kafkaMessage) {
        Optional<Publication> publication = this.publicationRepository.findById(((Integer) kafkaMessage.getProperties().get("publication")).longValue());
        if (publication.isPresent()) {
            Publication comment = kafkaMessage.getEntity();
            comment.setVisualizations(null);
            comment.setType(PublicationTypeEnum.COMMENT);
            comment.setTitle(null);
            comment.setComments(Collections.EMPTY_LIST);
            comment.setFiles(Collections.EMPTY_LIST);
            comment.setLikes(Collections.EMPTY_LIST);
            comment.setCreationDate(new Date());
            comment.setReference(publication.get());

            this.publicationRepository.save(comment);
        }
    }

    public void deleteComment(KafkaMessage<Publication> kafkaMessage) {
        Optional<Publication> optional = this.publicationRepository.findById(kafkaMessage.getEntity().getId());
        if (optional.isPresent()) {
            Publication comment = optional.get();

            this.likeService.deleteLikesFromPublicationId(comment.getId());
            this.publicationRepository.deleteById(comment.getId());
        }
    }

    public void visualize(KafkaMessage<Publication> kafkaMessage) {
        Optional<Publication> publication = this.publicationRepository.findById(kafkaMessage.getEntity().getId());
        if (publication.isPresent()) {
            Publication ref = publication.get();
            ref.setVisualizations(ref.getVisualizations() + 1);

            this.publicationRepository.save(ref);
        }
    }

    // METHODS FROM CONTROLLER

    public List<Publication> findAll() {
        return this.publicationRepository.findAll();
    }

    public Optional<Publication> findById(Long id) {
        return this.publicationRepository.findById(id);
    }

    public Publication findByIdWithThrows(Long id) throws Exception {
        return this.publicationRepository.findById(id)
                .orElseThrow(() -> new Exception("Publication not Found for id" + id));
    }

    public Page<Publication> findAllPageable(Map<String, String> params) {
        int pageNumber = Integer.parseInt(params.getOrDefault("page", "0"));
        int pageSize = Integer.parseInt(params.getOrDefault("size", "10"));

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Specification<Publication> specifications =
                Specification.where(new Specification<Publication>() {
                    @Override
                    public Predicate toPredicate(Root<Publication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                        return criteriaBuilder.equal(root.get("type"), PublicationTypeEnum.valueOf(params.get("type")));
                    }
                })
                .and(params.containsKey("main") ? new Specification<Publication>() {
                    @Override
                    public Predicate toPredicate(Root<Publication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.equal(root.get("reference").get("id"), Long.parseLong(params.get("main")));
                    }
                } : null);

        return this.publicationRepository.findAll(specifications, pageRequest);
    }

}
