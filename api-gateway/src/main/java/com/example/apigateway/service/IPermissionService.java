package com.example.apigateway.service;

import com.example.apigateway.entity.Permission;

import java.util.List;

public interface IPermissionService {

    /**
     * Retrieves all permissions from the database.
     *
     * @return a list of all permissions
     */
    List<Permission> getAllPermissions();

    /**
     * Retrieves permissions by role name.
     *
     * @param roleName the name of the role
     * @return a list of permissions associated with the specified role
     */
    List<Permission> getPermissionsByRoleName(String roleName);
}
