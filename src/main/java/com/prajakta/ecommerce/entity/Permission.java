package com.prajakta.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@EqualsAndHashCode(of = "id")
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

    public Permission(String name){
        this.name=name;
    }
    public Permission(){

    }

}
