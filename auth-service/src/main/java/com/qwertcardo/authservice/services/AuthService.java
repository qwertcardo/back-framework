package com.qwertcardo.authservice.services;

import com.qwertcardo.basedomain.domain.User;
import com.qwertcardo.basedomain.security.AuthRequest;
import com.qwertcardo.basedomain.security.AuthResponse;
import com.qwertcardo.basedomain.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest authRequest) throws Exception {
        ResponseEntity<User> request = restTemplate.exchange("http://localhost:8080/user/user/find/" + authRequest.getUsername(), HttpMethod.GET, null, User.class);
        User user = request.getBody();
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new Exception("Bad Credentials!");
        }

        return AuthResponse.builder()
                .token(jwtUtil.generateToken(user))
                .user(user)
                .build();
    }
}
