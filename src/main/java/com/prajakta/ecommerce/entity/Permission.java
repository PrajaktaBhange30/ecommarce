package com.prajakta.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Role> roles = new HashSet<>();
}
