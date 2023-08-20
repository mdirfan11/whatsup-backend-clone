package com.mdtech.whatsupbackendclone.controller;

import com.mdtech.whatsupbackendclone.exception.UserException;
import com.mdtech.whatsupbackendclone.modal.User;
import com.mdtech.whatsupbackendclone.repository.UserRepository;
import com.mdtech.whatsupbackendclone.request.LoginRequest;
import com.mdtech.whatsupbackendclone.response.AuthResponse;
import com.mdtech.whatsupbackendclone.service.CustomUserService;
import com.mdtech.whatsupbackendclone.service.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserService customUserService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, CustomUserService customUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.customUserService = customUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User A_user) {
        Optional<User> user = userRepository.findByEmail(A_user.getEmail());
        if (user.isPresent()) {
            throw new UserException("email "+A_user.getEmail()+" already registered");
        }
        A_user.setPassword(passwordEncoder.encode(A_user.getPassword()));
        userRepository.save(A_user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(A_user.getEmail(), A_user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = tokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwtToken, true);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = tokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwtToken, true);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Username or Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
