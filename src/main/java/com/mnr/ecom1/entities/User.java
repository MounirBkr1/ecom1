package com.mnr.ecom1.entities;


import com.mnr.ecom1.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String email;
    private String password;
    private String name;
    //ENUMERATION
    private UserRole role;

    @Lob //help us to map large binary objects or large character objects to a specific entity in our database
    //@Column(columnDefinition = "longLob")
    private byte[] img;

}
