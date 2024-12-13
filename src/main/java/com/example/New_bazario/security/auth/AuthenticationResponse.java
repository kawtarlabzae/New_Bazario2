package com.example.New_bazario.security.auth;


import lombok.Builder;
import lombok.Data;


@Data
@Builder

public class AuthenticationResponse {

	private String token;

	

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    public static class Builder {
        private String token;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(this.token);
        }
    }

    // Static method to access the builder
    public static Builder builder() {
        return new Builder();
    }
}
