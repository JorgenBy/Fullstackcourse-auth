package com.drmarkdown.auth.services;

import com.drmarkdown.auth.dtos.UserInfoDTO;
import com.drmarkdown.auth.dtos.UserLoginDTO;

public interface UserService {

    void createUser(UserInfoDTO userInfoDTO);

    UserInfoDTO retrieveUSerInfo(String userId);

    UserInfoDTO loginUser(UserLoginDTO userLoginDTO);
}
