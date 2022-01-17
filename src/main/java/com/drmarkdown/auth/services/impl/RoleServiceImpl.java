package com.drmarkdown.auth.services.impl;

import com.drmarkdown.auth.daos.RoleDAO;
import com.drmarkdown.auth.dtos.RoleDTO;
import com.drmarkdown.auth.models.MarkdownRoleModel;
import com.drmarkdown.auth.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void createRole(RoleDTO roleDTO) {
        MarkdownRoleModel markdownRoleModel = modelMapper.map(roleDTO, MarkdownRoleModel.class);

        roleDAO.save(markdownRoleModel);

        modelMapper.map(markdownRoleModel, roleDTO);
    }


    @Override
    public RoleDTO roleInfo(String roleId) {
        Optional<MarkdownRoleModel> markdownRoleModelOptional = roleDAO.findById(roleId);

        if (markdownRoleModelOptional.isPresent()) {
            MarkdownRoleModel markdownRoleModel = markdownRoleModelOptional.get();

            return modelMapper.map(markdownRoleModel, RoleDTO.class);
        }
        return null;
    }
}
