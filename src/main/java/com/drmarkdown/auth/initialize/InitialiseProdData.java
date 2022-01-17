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
import java.util.Optional;

@Profile({"prod"})
@Component
public class InitialiseProdData {

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
        Optional<MarkdownRoleModel> optionalMarkdownRoleModelAdmin = roleDAO.findByRole("ADMIN");

        if (optionalMarkdownRoleModelAdmin.isEmpty()) {
            MarkdownRoleModel markdownRoleModelAdmin = new MarkdownRoleModel();
            markdownRoleModelAdmin.setRole("ADMIN");

            roleDAO.save(markdownRoleModelAdmin);
        }

        Optional<MarkdownRoleModel> optionalMarkdownRoleModelUser = roleDAO.findByRole("USER");

        if (optionalMarkdownRoleModelUser.isEmpty()) {
            MarkdownRoleModel markdownRoleModelUser = new MarkdownRoleModel();
            markdownRoleModelUser.setRole("USER");


            roleDAO.save(markdownRoleModelUser);
        }



    }

    void addUsers() {
        System.out.println("adding users prod 1");
        Optional<MarkdownUserModel> optionalMarkdownUserModelAdmin = userDAO.findByUsername("admin");

        if (optionalMarkdownUserModelAdmin.isEmpty()) {
            MarkdownUserModel markdownUserModelAdmin = new MarkdownUserModel();
            markdownUserModelAdmin.setUsername("admin");
            markdownUserModelAdmin.setEmail("admin@test.com");
            markdownUserModelAdmin.setPassword(bCryptPasswordEncoder.encode("admin"));
            markdownUserModelAdmin.setRoles(Collections.singletonList("ADMIN"));
            tokenService.generateToken(markdownUserModelAdmin);
            markdownUserModelAdmin.setDisplayName("AdminDisplayName");

            userDAO.save(markdownUserModelAdmin);
        }

        Optional<MarkdownUserModel> optionalMarkdownUserModelUser = userDAO.findByUsername("user");

        if (optionalMarkdownUserModelUser.isEmpty()) {
            MarkdownUserModel markdownUserModelUser = new MarkdownUserModel();
            markdownUserModelUser.setUsername("user");
            markdownUserModelUser.setEmail("user@test.com");
            markdownUserModelUser.setPassword(bCryptPasswordEncoder.encode("user"));
            markdownUserModelUser.setRoles(Collections.singletonList("USER"));
            tokenService.generateToken(markdownUserModelUser);
            markdownUserModelUser.setDisplayName("UserDisplayName");

            userDAO.save(markdownUserModelUser);
        }

    }
}
