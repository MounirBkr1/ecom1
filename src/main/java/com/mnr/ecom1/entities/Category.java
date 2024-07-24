package com.mnr.ecom1.entities;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String name;

    private String description;
}
