package com.alves.youtransfer.controllers.user;

import com.alves.youtransfer.dtos.LoginRequest;
import com.alves.youtransfer.dtos.LoginResponse;
import com.alves.youtransfer.models.user.User;
import com.alves.youtransfer.services.user.AuthService;
import com.alves.youtransfer.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = authService.save(user);
        newUser.setPassword(null);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = authService.findByEmail(request.getEmail());

        if (user == null || !authService.checkPassword(user, request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        LoginResponse response = new LoginResponse(token, user.getEmail(), user.getName());

        return ResponseEntity.ok(token);
    }

}

