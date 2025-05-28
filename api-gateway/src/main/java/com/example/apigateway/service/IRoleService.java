package com.example.apigateway.service;

import com.example.apigateway.entity.Role;

import java.util.List;

public interface IRoleService {

    /**
     * Retrieves all roles from the database.
     *
     * @return a list of all roles
     */
    List<Role> getAllRoles();
}
