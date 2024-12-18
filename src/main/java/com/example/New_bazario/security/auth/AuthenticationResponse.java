package com.example.New_bazario.security.auth;


import com.example.New_bazario.security.user.User;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private User user;

    public AuthenticationResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Builder {
        private String token;
        private User user;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(this.token, this.user);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}