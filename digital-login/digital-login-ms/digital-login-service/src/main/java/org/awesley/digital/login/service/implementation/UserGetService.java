package org.awesley.digital.login.service.implementation;

import java.util.Collection;
import java.util.stream.Collectors;

import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserGetService implements IUserGetService, UserDetailsService {

	@Autowired
	private IUserRepository userRepository;
	
	public User GetUser(Long id) {
		return userRepository.getById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getByUsername(username);
		
		return new UserDetails() {

			private static final long serialVersionUID = 1L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return user.getAuthorities()
						.stream()
						.map(a -> new SimpleGrantedAuthority(a.getName()))
						.collect(Collectors.toList());
			}

			@Override
			public String getPassword() {
				return user.getPassword();
			}

			@Override
			public String getUsername() {
				return user.getUsername();
			}

			@Override
			public boolean isAccountNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isEnabled() {
				return true;
			}
			
		};
	}
}
