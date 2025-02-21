package com.mnr.ecom1.repositories;

import com.mnr.ecom1.entities.User;
import com.mnr.ecom1.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole userRole);
}
