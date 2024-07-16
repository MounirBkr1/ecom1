package com.mnr.ecom1.services.auth;

import com.mnr.ecom1.dto.SignupRequest;
import com.mnr.ecom1.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
