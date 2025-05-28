package com.example.apigateway.service.impl;

import com.example.apigateway.entity.Permission;
import com.example.apigateway.repository.PermissionRepository;
import com.example.apigateway.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {

    private final PermissionRepository permissionRepository;
    /**
     * Retrieves all permissions from the database.
     *
     * @return a list of all permissions
     */
    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    /**
     * Retrieves permissions by role name.
     *
     * @param roleName the name of the role
     * @return a list of permissions associated with the specified role
     */
    @Override
    public List<Permission> getPermissionsByRoleName(String roleName) {
        return permissionRepository.getByRoleName(roleName);
    }
}
