package com.mnr.ecom1.services.auth;

import com.mnr.ecom1.dto.SignupRequest;
import com.mnr.ecom1.dto.UserDto;
import com.mnr.ecom1.entities.User;
import com.mnr.ecom1.enums.UserRole;
import com.mnr.ecom1.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
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

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }


    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
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

    @PostConstruct //called after the constractor
    public  void createAdminAccount(){
        User adminAccount= userRepository.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            User user=new User();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);

            System.out.printf("post admin created " + user);
        }
    }

}
