package com.example.New_bazario.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	private String token;

	

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
