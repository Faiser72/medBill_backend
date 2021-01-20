package com.vetologic.medbill.jwt_security_config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.jwt_security_config.beans.JwtRequest;
import com.vetologic.medbill.jwt_security_config.beans.JwtResponse;
import com.vetologic.medbill.jwt_security_config.model.service.JwtUserDetailsService;




@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {
	private static Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

			final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			final String jwtToken = jwtTokenUtil.generateToken(userDetails);
//			final String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
			log.info("JWT Token generated successfully.");
			log.info(userDetails.getUsername() + " Logged successfully.");
			return ResponseEntity.ok(new JwtResponse(userDetails.getUsername(), jwtToken, "ROLE_ADMIN"));
		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseEntity.ok(null);
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("BAD_CREDENTIALS", e);
		}
	}
}
