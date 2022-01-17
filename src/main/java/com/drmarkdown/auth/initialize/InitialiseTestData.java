package com.drmarkdown.auth.initialize;

import com.drmarkdown.auth.daos.RoleDAO;
import com.drmarkdown.auth.daos.UserDAO;
import com.drmarkdown.auth.models.MarkdownRoleModel;
import com.drmarkdown.auth.models.MarkdownUserModel;
import com.drmarkdown.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Profile({"dev", "test"})
@Component
public class InitialiseTestData {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenService tokenService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        addRoles();
        addUsers();

    }

    void addRoles() {
        roleDAO.deleteAll();
        MarkdownRoleModel markdownRoleModel = new MarkdownRoleModel();
        markdownRoleModel.setRole("ADMIN");

        MarkdownRoleModel markdownRoleModel2 = new MarkdownRoleModel();
        markdownRoleModel2.setRole("USER");

        roleDAO.save(markdownRoleModel);
        roleDAO.save(markdownRoleModel2);
    }

    void addUsers() {
        userDAO.deleteAll();

        MarkdownUserModel markdownUserModel1 = new MarkdownUserModel();
        markdownUserModel1.setUsername("admin");
        markdownUserModel1.setEmail("admin@test.com");
        markdownUserModel1.setPassword(bCryptPasswordEncoder.encode("admin"));
        markdownUserModel1.setRoles(Collections.singletonList("ADMIN"));
        tokenService.generateToken(markdownUserModel1);
        markdownUserModel1.setDisplayName("AdminDisplayName");

        userDAO.save(markdownUserModel1);

        MarkdownUserModel markdownUserModel2 = new MarkdownUserModel();
        markdownUserModel2.setUsername("user");
        markdownUserModel2.setEmail("user@test.com");
        markdownUserModel2.setPassword(bCryptPasswordEncoder.encode("user"));
        markdownUserModel2.setRoles(Collections.singletonList("USER"));
        tokenService.generateToken(markdownUserModel2);
        markdownUserModel2.setDisplayName("UserDisplayName");

        userDAO.save(markdownUserModel2);
    }
}
