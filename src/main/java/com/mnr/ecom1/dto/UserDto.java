package com.mnr.ecom1.dto;

import com.mnr.ecom1.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private int id;
    private String email;
    private String name;
    private UserRole userRole;
}
