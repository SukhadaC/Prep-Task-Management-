package com.preparation.prep.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private final String SECRET="mysecretkeymysecretkeymysecretkey";

    private final long EXPIRATION= 1000*60*60;

    private Key getKey()
    {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }


    //Generate Token
    public String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //Parse the JWT
    public Claims extractAllCLaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }



    // Extract Username
    public String extractUsername(String token)
    {
    return extractAllCLaims(token).getSubject();

    }

    public boolean isTokenValid(String token, String username)
    {
        final String EXTRACTEDUSERNAME=extractUsername(token);

        return EXTRACTEDUSERNAME.equals(username) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token)
    {
        return extractAllCLaims(token).getExpiration().before(new Date());
    }




}
