package com.drmarkdown.auth.controllers;

import com.drmarkdown.auth.dtos.UserInfoDTO;
import com.drmarkdown.auth.dtos.UserLoginDTO;
import com.drmarkdown.auth.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    // Create User
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ANONYMOUS', 'ADMIN')")
    public UserInfoDTO createUser(@RequestBody UserInfoDTO userInfoDTO) {
        checkNotNull(userInfoDTO);
        userService.createUser(userInfoDTO);
        return userInfoDTO;
    }

    //Login User
    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('ANONYMOUS')")
    public UserInfoDTO loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        checkNotNull(userLoginDTO);
        return userService.loginUser(userLoginDTO);
    }

    // Get User Info
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserInfoDTO getUserInfo(@PathVariable String userId) {
        return userService.retrieveUSerInfo(userId);
    }

    // Delete a User
    @GetMapping("/delete/{userId}")
    public UserInfoDTO deleteUser(@PathVariable String userId) {
        System.out.println(userId);
        //TODO: add service to handle logic
        return null;
    }

    //Modify a User
    @GetMapping("/edit/{userId}")
    public UserInfoDTO editUser(@PathVariable String userId) {
        System.out.println(userId);
        //TODO: add service to handle logic
        return null;
    }
}
