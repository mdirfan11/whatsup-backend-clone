package com.mdtech.whatsupbackendclone.service;

import com.mdtech.whatsupbackendclone.exception.UserException;
import com.mdtech.whatsupbackendclone.modal.User;

import java.util.List;

public interface UserService {

    User findUserProfile(String jwt);

    User updateUser(Long id, User requestUser) throws UserException;

    List<User> serchUser(String query);

}
