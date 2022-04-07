package com.qwertcardo.consumerservice.controllers;

import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.consumerservice.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping(value = "/list")
    public List<Publication> findAll() {
        return this.publicationService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            Publication publication = this.publicationService.findByIdWithThrows(id);
            return ResponseEntity.status(HttpStatus.OK).body(publication);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/page")
    public ResponseEntity<?> findAllPageable(@RequestParam(required = false) Map<String, String> params) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.publicationService.findAllPageable(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
