package com.mdtech.whatsupbackendclone.controller;

import com.mdtech.whatsupbackendclone.modal.User;
import com.mdtech.whatsupbackendclone.response.ApiResponse;
import com.mdtech.whatsupbackendclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/whatsup-clone/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(userService.findUserProfile(token), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable String query) {
        return new ResponseEntity<>(userService.serchUser(query), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody User A_user,
                                                         @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        userService.updateUser(user.getId(), A_user);
        ApiResponse apiResponse = new ApiResponse("User updated successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);

    }

}
