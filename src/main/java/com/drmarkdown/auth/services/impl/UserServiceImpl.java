package com.drmarkdown.auth.services.impl;

import com.drmarkdown.auth.daos.RoleDAO;
import com.drmarkdown.auth.daos.UserDAO;
import com.drmarkdown.auth.dtos.UserInfoDTO;
import com.drmarkdown.auth.dtos.UserLoginDTO;
import com.drmarkdown.auth.models.MarkdownRoleModel;
import com.drmarkdown.auth.models.MarkdownUserModel;
import com.drmarkdown.auth.services.TokenService;
import com.drmarkdown.auth.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserServiceImpl implements UserService {

    private static final String UNKNOWN_USERNAME_OR_BAD_PASSWORD = "Unknown username or bad password";
    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createUser(UserInfoDTO userInfoDTO) {

        checkNotNull(checkEmptyString(userInfoDTO.getPassword()));

        // transform userInfoDTO to markdownUserModel
        MarkdownUserModel markdownUserModel =  modelMapper.map(userInfoDTO, MarkdownUserModel.class);

        // hash the password
        markdownUserModel.setPassword(bCryptPasswordEncoder.encode(userInfoDTO.getPassword()));

        // assign default role -> USER
        markdownUserModel.setRoles(
                roleDAO.findAll().stream()
                        .map(MarkdownRoleModel::getRole)
                        .filter(role -> role.contains("USER"))
                        .collect(Collectors.toList())
        );

        // generate a new token for the user
        tokenService.generateToken(markdownUserModel);

        // save markdownUserModel
        userDAO.save(markdownUserModel);
        // update userInfoDTO after saving
        userInfoDTO.setPassword("");
        modelMapper.map(markdownUserModel, userInfoDTO);
    }

    @Override
    public UserInfoDTO retrieveUSerInfo(String userId) {
        Optional<MarkdownUserModel> optionalMarkdownUserModel =  userDAO.findById(userId);
        return optionalMarkdownUserModel.map(markdownUserModel -> modelMapper.map(markdownUserModel, UserInfoDTO.class)).orElse(null);
    }

    @Override
    public UserInfoDTO loginUser(UserLoginDTO userLoginDTO) {
        Optional<MarkdownUserModel> optionalMarkdownUserModel =  userDAO.findByUsername(userLoginDTO.getUsername());
        if (optionalMarkdownUserModel.isPresent()) {
            MarkdownUserModel markdownUserModel = optionalMarkdownUserModel.get();

            // check password
            if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), markdownUserModel.getPassword())) {
                return modelMapper.map(markdownUserModel, UserInfoDTO.class);
            } else {
                throw new BadCredentialsException(UNKNOWN_USERNAME_OR_BAD_PASSWORD);
            }

        } else {
            throw new BadCredentialsException(UNKNOWN_USERNAME_OR_BAD_PASSWORD);
        }
    }

    private Object checkEmptyString(String content) {
        if (content.isBlank()) {
            return null;
        }
        return content;
    }
}
