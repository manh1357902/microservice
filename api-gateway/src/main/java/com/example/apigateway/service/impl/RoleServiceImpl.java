package com.example.apigateway.service.impl;

import com.example.apigateway.entity.Role;
import com.example.apigateway.repository.RoleRepository;
import com.example.apigateway.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

    /**
     * Retrieves all roles from the database.
     *
     * @return a list of all roles
     */
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
