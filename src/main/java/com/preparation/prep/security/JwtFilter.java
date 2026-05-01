package com.preparation.prep.security;

import com.preparation.prep.exception.ExpiredTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;
    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");

        if(header==null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token=header.substring(7);
        try{
            String username=jwtUtil.extractUsername(token);

                if (!jwtUtil.isTokenExpired(token)) {

                    // 🔥 THIS IS THE MISSING PIECE
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    new ArrayList<>()
                            );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

             System.out.println("Username: "+username);

        }
        catch (Exception e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        //Continue Request
        filterChain.doFilter(request,response);
    }
}
