package com.alves.youtransfer.dtos;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginRequest {
    private String email;
    private String password;
}