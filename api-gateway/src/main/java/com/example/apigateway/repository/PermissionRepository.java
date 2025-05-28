package com.example.apigateway.repository;

import com.example.apigateway.entity.Permission;
import com.example.apigateway.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * Retrieves permissions associated with a specific role name.
     *
     * @param roleName the name of the role
     * @return a list of permissions associated with the specified role
     */
    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.name = :roleName")
    List<Permission> getByRoleName(String roleName);
}
