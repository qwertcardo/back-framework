package com.qwertcardo.consumerservice.services;

import com.netflix.discovery.converters.Auto;
import com.qwertcardo.basedomain.domain.Like;
import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.basedomain.domain.User;
import com.qwertcardo.basedomain.kafka.KafkaMessage;
import com.qwertcardo.consumerservice.repositories.LikeRepository;
import com.qwertcardo.consumerservice.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LikeService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    public void publish(KafkaMessage<Publication> kafkaMessage) {
        Optional<Publication> optional = this.publicationRepository.findById(kafkaMessage.getEntity().getId());
        if (optional.isPresent()) {
            Long userId = ((Integer) kafkaMessage.getProperties().get("user")).longValue();
            ResponseEntity<User> request = restTemplate.exchange("http://localhost:8080/user/user/id/" + userId, HttpMethod.GET, null, User.class);
            User user = request.getBody();

            Publication publication = optional.get();

            Like like = new Like();
            like.setPublication(publication);
            like.setUser(user);

            this.likeRepository.save(like);
        }
    }

    public void delete(KafkaMessage<Publication> kafkaMessage) {
        Optional<Publication> optional = this.publicationRepository.findById(kafkaMessage.getEntity().getId());
        if (optional.isPresent()) {
            Long userId = ((Integer) kafkaMessage.getProperties().get("user")).longValue();
            ResponseEntity<User> request = restTemplate.exchange("http://localhost:8080/user/user/id/" + userId, HttpMethod.GET, null, User.class);
            User user = request.getBody();

            Publication publication = optional.get();

            this.likeRepository.deleteLikeFromPublicationAndUser(publication.getId(), user.getId());
        }
    }

    public void deleteLikesFromPublicationId(Long id) {
        this.likeRepository.deleteLikesFromPublicationId(id);
    }
}
