package com.qwertcardo.producerservice.controllers;

import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.producerservice.services.KafkaDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class KafkaDefaultController {

    @Autowired
    private KafkaDefaultService kafkaDefaultService;

    @PostMapping(value = "/publish/publication")
    public ResponseEntity<?> publishPublication(@RequestBody Publication publication) {
        try {
            this.kafkaDefaultService.publishPublication(publication);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PostMapping(value = "/delete/publication")
    public ResponseEntity<?> deletePublication(@RequestBody Publication publication) {
        try {
            this.kafkaDefaultService.deletePublication(publication);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PostMapping(value = "/publish/comment")
    public ResponseEntity<?> commentPublication(@RequestBody Publication publication) {
        try {
            this.kafkaDefaultService.commentPublication(publication);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PostMapping(value = "/delete/comment")
    public ResponseEntity<?> deleteComment(@RequestBody Publication publication) {
        try {
            this.kafkaDefaultService.deleteComment(publication);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PostMapping(value = "/publish/like")
    public ResponseEntity<?> likePublication(@RequestBody Publication publication) {
        try {
            this.kafkaDefaultService.likePublication(publication);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PostMapping(value = "/delete/like")
    public ResponseEntity<?> dislikePublication(@RequestBody Publication publication) {
        try {
            this.kafkaDefaultService.dislikePublication(publication);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PostMapping(value = "/visualisation")
    public ResponseEntity<?> visualisePublication(@RequestBody Publication publication) {
        try {
            this.kafkaDefaultService.visualisePublication(publication);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }
}
