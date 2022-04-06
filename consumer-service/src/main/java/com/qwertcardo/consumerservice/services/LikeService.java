package com.qwertcardo.consumerservice.services;

import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.basedomain.kafka.KafkaMessage;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    public void publish(KafkaMessage<Publication> kafkaMessage) {

    }

    public void delete(KafkaMessage<Publication> kafkaMessage) {

    }
}
