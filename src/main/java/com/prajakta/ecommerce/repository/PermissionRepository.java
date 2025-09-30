package com.prajakta.ecommerce.repository;

import com.prajakta.ecommerce.entity.Permission;
import com.prajakta.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);

//    @Query("SELECT p FROM permissions p JOIN FETCH p.roles WHERE p.name = :name")
//  Optional<User> findByName(@Param("name") String name);
}
