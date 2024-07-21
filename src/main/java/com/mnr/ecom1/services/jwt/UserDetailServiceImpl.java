package com.mnr.ecom1.services.jwt;


import com.mnr.ecom1.entities.User;
import com.mnr.ecom1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser=userRepository.findFirstByEmail(username);

        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("username not found",null);



        //this User is from spring security core
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(),
                optionalUser.get().getPassword(),new ArrayList<>()) ;
    }
}
