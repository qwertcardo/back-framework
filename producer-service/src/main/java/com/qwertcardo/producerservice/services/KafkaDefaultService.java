package com.qwertcardo.producerservice.services;

import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.basedomain.kafka.constants.KafkaTopicConstants;
import com.qwertcardo.producerservice.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaDefaultService {

    @Autowired
    private KafkaProducer kafkaProducer;

    public SendResult<String, String> publishPublication(Publication publication) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("action", "publish");

        return this.kafkaProducer.send(KafkaTopicConstants.PUBLICATION_TOPIC, publication, properties);
    }

    public SendResult<String, String> deletePublication(Publication publication) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("action", "delete");

        return this.kafkaProducer.send(KafkaTopicConstants.PUBLICATION_TOPIC, publication, properties);
    }

    public SendResult<String, String> commentPublication(Publication publication) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("action", "publish");
        properties.put("publication", publication.getReference().getId());

        return this.kafkaProducer.send(KafkaTopicConstants.COMMENT_TOPIC, publication, properties);
    }

    public SendResult<String, String> deleteComment(Publication publication) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("action", "delete");

        return this.kafkaProducer.send(KafkaTopicConstants.COMMENT_TOPIC, publication, properties);
    }

    public SendResult<String, String> likePublication(Publication publication) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("action", "publish");
        properties.put("user", publication.getCreator().getId());

        return this.kafkaProducer.send(KafkaTopicConstants.LIKE_TOPIC, publication, properties);
    }

    public SendResult<String, String> dislikePublication(Publication publication) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("action", "delete");
        properties.put("user", publication.getCreator().getId());

        return this.kafkaProducer.send(KafkaTopicConstants.LIKE_TOPIC, publication, properties);
    }

    public SendResult<String, String> visualisePublication(Publication publication) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("action", "publish");

        return this.kafkaProducer.send(KafkaTopicConstants.VISUALIZATION_TOPIC, publication, properties);
    }
}
