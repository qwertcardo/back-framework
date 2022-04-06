package com.qwertcardo.consumerservice.controllers;

import com.qwertcardo.basedomain.domain.Publication;
import com.qwertcardo.consumerservice.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping(value = "/list")
    public List<Publication> findAll() {
        return this.publicationService.findAll();
    }
}
