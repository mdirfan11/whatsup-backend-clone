package com.mdtech.whatsupbackendclone.response;

public class AuthResponse {

    private String jwtToken;
    private boolean isAuthenticated;

    public AuthResponse() {}

    public AuthResponse(String jwtToken, boolean isAuthenticated) {
        this.jwtToken = jwtToken;
        this.isAuthenticated = isAuthenticated;
    }
}
