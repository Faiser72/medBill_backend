package com.vetologic.medbill.jwt_security_config.model.repository;

import com.vetologic.medbill.beans.users.AdminBean;

public interface UserRepository {
	AdminBean findByUsername(String username);
}