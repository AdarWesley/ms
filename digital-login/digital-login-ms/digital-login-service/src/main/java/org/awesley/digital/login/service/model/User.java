package org.awesley.digital.login.service.model;
import java.util.Date;
import java.util.List;

public interface User {

	Long getId();

	void setId(Long id);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	String getFirstname();

	void setFirstname(String firstname);

	String getLastname();

	void setLastname(String lastname);

	String getEmail();

	void setEmail(String email);

	Boolean getEnabled();

	void setEnabled(Boolean enabled);

	List<? extends Authority> getAuthorities();

	void setAuthorities(List<? extends Authority> authorities);

	Date getLastPasswordResetDate();

	void setLastPasswordResetDate(Date lastPasswordResetDate);
}