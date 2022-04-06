package com.qwertcardo.basedomain.security;

import com.qwertcardo.basedomain.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private User user;
    private String token;
}
