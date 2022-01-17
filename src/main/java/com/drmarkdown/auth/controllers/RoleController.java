package com.drmarkdown.auth.controllers;

import com.drmarkdown.auth.dtos.RoleDTO;
import com.drmarkdown.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleController {
    @Autowired
    private RoleService roleService;
    //Create role
    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        checkNotNull(roleDTO);
        roleService.createRole(roleDTO);
        return roleDTO;
    }

    //Get info about a specific role
    @GetMapping("/info/{roleId}")
    public RoleDTO getRole(@PathVariable String roleId) {
        return roleService.roleInfo(roleId);
    }

    //TODO: add methods
    //Delete a role

    //Modify a role
}
