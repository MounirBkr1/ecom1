package com.mnr.ecom1.services.auth;

import com.mnr.ecom1.dto.SignupRequest;
import com.mnr.ecom1.dto.UserDto;
import com.mnr.ecom1.entities.User;
import com.mnr.ecom1.enums.UserRole;
import com.mnr.ecom1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getEmail()));
        user.setRole(UserRole.CUSTOMER);

        User createUser= userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());
        return userDto;
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
