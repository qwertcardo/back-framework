package com.qwertcardo.userservice.controllers;

import com.qwertcardo.basedomain.domain.User;
import com.qwertcardo.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/find/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable("username") String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.findByUsername(username));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.register(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
