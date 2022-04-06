package com.qwertcardo.consumerservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.basedomain.kafka.KafkaMessage;
import com.qwertcardo.basedomain.kafka.constants.KafkaTopicConstants;
import com.qwertcardo.consumerservice.services.LikeService;
import com.qwertcardo.consumerservice.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KafkaConsumer {
    private static final String PUBLISH_ACTION = "publish";
    private static final String DELETE_ACTION = "delete";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private LikeService likeService;

    @KafkaListener(topics = KafkaTopicConstants.PUBLICATION_TOPIC)
    private void processPublicationTopic(String message) throws JsonProcessingException {
        KafkaMessage<Publication> kafkaMessage = this.objectMapper.readValue(message, new TypeReference<KafkaMessage<Publication>>() {});

        String action = kafkaMessage.getAction();
        if (action.equals(PUBLISH_ACTION)) {
            this.publicationService.publish(kafkaMessage);
        }
        if (action.equals(DELETE_ACTION)) {
            this.publicationService.delete(kafkaMessage);
        }
    }

    @KafkaListener(topics = KafkaTopicConstants.COMMENT_TOPIC)
    private void processCommentTopic(String message) throws JsonProcessingException {
        KafkaMessage<Publication> kafkaMessage = this.objectMapper.readValue(message, new TypeReference<KafkaMessage<Publication>>() {});

        String action = kafkaMessage.getAction();
        if (action.equals(PUBLISH_ACTION)) {
            this.publicationService.publishComment(kafkaMessage);
        }
        if (action.equals(DELETE_ACTION)) {
            this.publicationService.deleteComment(kafkaMessage);
        }
    }

    @KafkaListener(topics = KafkaTopicConstants.LIKE_TOPIC)
    private void processLikeTopic(String message) throws JsonProcessingException {
        KafkaMessage<Publication> kafkaMessage = this.objectMapper.readValue(message, new TypeReference<KafkaMessage<Publication>>() {});

        String action = kafkaMessage.getAction();
        if (action.equals(PUBLISH_ACTION)) {
            this.likeService.publish(kafkaMessage);
        }
        if (action.equals(DELETE_ACTION)) {
            this.likeService.delete(kafkaMessage);
        }
    }

    @KafkaListener(topics = KafkaTopicConstants.VISUALIZATION_TOPIC)
    private void processVisualizationTopic(String message) throws JsonProcessingException {
        KafkaMessage<Publication> kafkaMessage = this.objectMapper.readValue(message, new TypeReference<KafkaMessage<Publication>>() {});

        String action = kafkaMessage.getAction();
        if (action.equals(PUBLISH_ACTION)) {
            this.publicationService.visualize(kafkaMessage);
        }
        if (action.equals(DELETE_ACTION)) {
            // PATTERN DO NOT DE-VISUALIZE A PUBLISH
        }
    }
}
