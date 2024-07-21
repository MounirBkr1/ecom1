package com.mnr.ecom1.filters;

import com.mnr.ecom1.services.jwt.UserDetailServiceImpl;
import com.mnr.ecom1.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//this class is for validate the token

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    //UserDetailsService: load user by username, contain method loadUserByUsername
    private final UserDetailServiceImpl userDetailsServiceImpl;

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username=null;

        if(authHeader != null && authHeader.startsWith("Beare ")){
            token=authHeader.substring(7);
            username= jwtUtil.extractUsername(token);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername(username);

            if(jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails,null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }
        }

        filterChain.doFilter(request,response);

    }
}
