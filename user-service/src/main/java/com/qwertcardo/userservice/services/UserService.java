package com.qwertcardo.userservice.services;

import com.qwertcardo.basedomain.domain.User;
import com.qwertcardo.basedomain.security.JwtUtil;
import com.qwertcardo.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) throws Exception {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found!"));
    }

    public User register(User user) throws Exception {
        Optional<User> existent = this.userRepository.findByUsername(user.getUsername());
        if (existent.isPresent()) {
            throw new Exception("User is already registered!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }
}
