package com.killiann.springMusic.models.auth;

public class AuthenticationRequest {

    private String username;
    private String password;

    // empty constructors are usually needed for Serialization. (Jackson)
    AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
