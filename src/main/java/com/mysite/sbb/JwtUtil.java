package com.mysite.sbb;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "my-secret-key";

    public static String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60)) // 1시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    //토큰 생성, 정보를 담는건가 ?

    public static Claims validateToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    //토큰 검증 및 정보 추출, 

    public static String extractUsername(String token){
        return validateToken(token).getSubject();
    }
    //토큰에서 사용자 이름 추출
}
