package com.alves.youtransfer.dtos;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String token;
    private String email;
    private String name;

    public LoginResponse(String token, String email, String name) {
        this.token = token;
        this.email = email;
        this.name = name;
    }
}

