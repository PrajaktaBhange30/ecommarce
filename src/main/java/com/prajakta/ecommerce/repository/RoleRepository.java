package com.prajakta.ecommerce.repository;

import com.prajakta.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Query("SELECT r FROM Role r JOIN FETCH r.permissions WHERE r.name = :name")
//    Optional<Role> findByName(@Param("name") String name);

    @Query("SELECT r FROM Role r JOIN FETCH r.permissions WHERE r.id = :id")
    Optional<Role> findByIdWithPermissions(@Param("id") Long id);

    @Query("SELECT r FROM Role r JOIN FETCH r.users WHERE r.id = :id")
    Optional<Role> findByIdWithUsers(@Param("id") Long id);

    boolean existsByName(String name);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions")
    List<Role> findAllWithPermissions();

    @Query("SELECT r FROM Role r JOIN FETCH r.permissions WHERE r.name = :name")
    Optional<Role> findByName(@Param("name") String name);

}


