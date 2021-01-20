package com.vetologic.medbill.jwt_security_config.beans;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String username;
	private final String jwtToken;
	private final String role;

	public JwtResponse(String username, String jwtToken, String role) {
		this.username = username;
		this.jwtToken = jwtToken;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public String getRole() {
		return role;
	}
}