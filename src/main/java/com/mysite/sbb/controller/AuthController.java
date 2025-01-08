package com.mysite.sbb.controller;

import com.mysite.sbb.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.mysite.sbb.JwtUtil.generateToken;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        if ("user".equals(username) && "password".equals(password)) {
            String token = JwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token)); // JSON 형식으로 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
