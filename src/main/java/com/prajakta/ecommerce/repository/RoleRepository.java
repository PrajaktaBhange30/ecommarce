package com.prajakta.ecommerce.repository;

import com.prajakta.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

//    @Query("SELECT r FROM Role r JOIN FETCH r.permissions WHERE r.id = :id")
//    Optional<Role> findByIdWithPermissions(@Param("id") Long id);
//
//    @Query("SELECT r FROM Role r JOIN FETCH r.users WHERE r.id = :id")
//    Optional<Role> findByIdWithUsers(@Param("id") Long id);
}


