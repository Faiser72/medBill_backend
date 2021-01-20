package com.vetologic.medbill.jwt_security_config.model.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.beans.users.AdminBean;
import com.vetologic.medbill.jwt_security_config.model.repository.UserRepository;



@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminBean userAdmin = userDao.findByUsername(username);
		System.err.println("test");
		if (userAdmin == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(userAdmin.getUsername(), userAdmin.getPassword(), getAuthority(userAdmin));
	}

	private Collection<? extends GrantedAuthority> getAuthority(AdminBean userAdmin) {
		Set<SimpleGrantedAuthority> authority = new HashSet<>();
		authority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authority;
	}
	
}
