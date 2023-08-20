package com.mdtech.whatsupbackendclone.service.impl;

import com.mdtech.whatsupbackendclone.exception.UserException;
import com.mdtech.whatsupbackendclone.modal.User;
import com.mdtech.whatsupbackendclone.repository.UserRepository;
import com.mdtech.whatsupbackendclone.service.TokenProvider;
import com.mdtech.whatsupbackendclone.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private TokenProvider tokenProvider;

    public UserServiceImpl (UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User Not Found with id : "+id));
    }

    @Override
    public User findUserProfile(String jwt) {
        String email = tokenProvider.getEmailFromToken(jwt);
        if (email == null) {
            throw new BadCredentialsException("Received Invalid Token");
        }
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found with email : "+email));
    }

    @Override
    public User updateUser(Long id, User requestUser) throws UserException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User Not Found with id : "));
        user.setFirstName(requestUser.getFirstName());
        user.setLastName(requestUser.getLastName());
        user.setEmail(requestUser.getEmail());
        user.setContactNo(requestUser.getContactNo());
        user.setProfilePic(requestUser.getProfilePic());
        return userRepository.save(user);
    }

    @Override
    public List<User> serchUser(String query) {
        return userRepository.searchUser(query);
    }
}
