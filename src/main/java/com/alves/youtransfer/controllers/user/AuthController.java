package com.alves.youtransfer.controllers.user;

import com.alves.youtransfer.dtos.LoginRequest;
import com.alves.youtransfer.dtos.LoginResponse;
import com.alves.youtransfer.models.user.User;
import com.alves.youtransfer.services.user.AuthService;
import com.alves.youtransfer.services.user.TokenBlacklistService;
import com.alves.youtransfer.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil, TokenBlacklistService tokenBlacklistService) {
        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
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

    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            long expiration = jwtUtil.getExpirationMillis(token);
            tokenBlacklistService.blacklistToken(token, expiration);
        }
        return ResponseEntity.ok("Logout successful");
    }
}

