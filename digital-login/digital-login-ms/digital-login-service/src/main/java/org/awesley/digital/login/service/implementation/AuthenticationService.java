package org.awesley.digital.login.service.implementation;

import java.util.stream.Collectors;

import org.awesley.digital.login.service.interfaces.IAuthenticationService;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.JwtToken;
import org.awesley.digital.login.service.model.User;
import org.awesley.digital.login.service.model.UserPasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

	@Override
	public JwtToken authenticate(UserPasswordCredentials credentials) {
        authenticationManager.authenticate(
        	new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        
        User user = userRepository.getByUsername(credentials.getUsername());
        
        PreAuthenticatedAuthenticationToken authenticatedAuthentication = 
        		new PreAuthenticatedAuthenticationToken(user.getUsername(), null,
        				user.getAuthorities()
        					.stream()
        					.map(a -> new SimpleGrantedAuthority(a.getName().name()))
        					.collect(Collectors.toList()));
        
        SecurityContextHolder.getContext().setAuthentication(authenticatedAuthentication);

         final String token = jwtTokenUtil.generateToken(user.getUsername(), user.getAuthorities());

        return new JwtToken(token);
	}
}
