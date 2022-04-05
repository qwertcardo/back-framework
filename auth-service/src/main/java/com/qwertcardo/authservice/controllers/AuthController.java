package com.qwertcardo.authservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class AuthController {

    @GetMapping(value = "/test")
    public ResponseEntity<String> get() {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Return with I Am A TEAPOT");
    }
}
