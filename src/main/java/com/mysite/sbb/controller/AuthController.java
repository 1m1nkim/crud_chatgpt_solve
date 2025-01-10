package com.mysite.sbb.controller;

import com.mysite.sbb.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
        if("user".equals(username) && "password".equals(password)){
            String accessToken = JwtUtil.generateToken(username, 30);
            String apiKey = JwtUtil.generateToken(username, 7 * 24 * 60);

            ResponseCookie cookie = ResponseCookie.from("apiKey", apiKey)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();
            return ResponseEntity.ok()
                    .header("Set-Cookie", cookie.toString())
                    .body(Map.of("accessToken", accessToken));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(@CookieValue("apiKey") String apiKey){
        try{
            Claims claims = JwtUtil.validateToken(apiKey);
            String username = claims.getSubject();

            String  newAccessToken = JwtUtil.generateToken(username, 30);

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired ApiKey");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        ResponseCookie cookie = ResponseCookie.from("apiKey", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body("Logged out successfully");
    }
}
