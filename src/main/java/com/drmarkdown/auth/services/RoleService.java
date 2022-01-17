package com.drmarkdown.auth.services;

import com.drmarkdown.auth.dtos.RoleDTO;

public interface RoleService {
    // role creation
    void createRole(RoleDTO roleDTO);

    // role information
    RoleDTO roleInfo(String roleId);
}
