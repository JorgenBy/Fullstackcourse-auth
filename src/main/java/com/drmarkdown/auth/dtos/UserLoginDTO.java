package com.drmarkdown.auth.dtos;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String username;
    private String password;
}
