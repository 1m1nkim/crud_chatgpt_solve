package com.mysite.sbb.controller;

import com.mysite.sbb.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        if("user".equals(username) && "password".equals(password)){
            return JwtUtil.generateToken(username);
        }else{
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
}
