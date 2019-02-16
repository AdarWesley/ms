package org.awesley.digital.login.service.implementation;

import java.time.Instant;
import java.util.Date;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.awesley.digital.login.service.interfaces.IUserChangePasswordService;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.PasswordChangeRequest;
import org.awesley.digital.login.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserChangePasswordService implements IUserChangePasswordService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void changePassword(Long id, PasswordChangeRequest modelPasswordChangeRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null  || !auth.isAuthenticated()) {
			throw new NotAuthorizedException("Must be authenticated to change password", Response.status(Status.UNAUTHORIZED).build());
		}
		
		User user = userRepository.getByUsername(auth.getName());
		if (!(user.getId().equals(id) ||
			  auth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")))) {
			throw new RuntimeException("Not permitted to change password");
		}
		
		if (!passwordEncoder.matches(modelPasswordChangeRequest.getOldPassword(), user.getPassword())) {
			throw new RuntimeException("Old password did not match");
		}
		
		user.setPassword(modelPasswordChangeRequest.getNewPassword());
		user.setLastPasswordResetDate(Date.from(Instant.now()));
		
		userRepository.save(user);
	}
}
