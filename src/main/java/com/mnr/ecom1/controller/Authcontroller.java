package com.mnr.ecom1.controller;


import com.mnr.ecom1.dto.AuthenticationRequest;
import com.mnr.ecom1.dto.SignupRequest;
import com.mnr.ecom1.dto.UserDto;
import com.mnr.ecom1.entities.User;
import com.mnr.ecom1.repositories.UserRepository;
import com.mnr.ecom1.services.auth.AuthService;
import com.mnr.ecom1.services.jwt.UserDetailServiceImpl;
import com.mnr.ecom1.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class Authcontroller {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    //from springframework security core
    private  final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final AuthService authService;

    @PostMapping("/authenticate")
    public void createAuthenticationtoken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        Optional<User> optionalUser= userRepository.findFirstByEmail(userDetails.getUsername());

        final String jwt= jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.get().getId())
                    .put("role",optionalUser.get().getRole())
                    .toString()
            );

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User Already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto=authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }
}
