package org.awesley.digital.login.persistence.implementation.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.awesley.digital.login.service.model.Authority;
import org.awesley.digital.login.service.model.User;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER")
public class JpaUser implements User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private List<JpaAuthority> authorities;

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getId()
	 */
    @Override
	public Long getId() {
        return id;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setId(java.lang.Long)
	 */
    @Override
	public void setId(Long id) {
        this.id = id;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getUsername()
	 */
    @Override
	public String getUsername() {
        return username;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setUsername(java.lang.String)
	 */
    @Override
	public void setUsername(String username) {
        this.username = username;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getPassword()
	 */
    @Override
	public String getPassword() {
        return password;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setPassword(java.lang.String)
	 */
    @Override
	public void setPassword(String password) {
        this.password = password;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getFirstname()
	 */
    @Override
	public String getFirstname() {
        return firstname;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setFirstname(java.lang.String)
	 */
    @Override
	public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getLastname()
	 */
    @Override
	public String getLastname() {
        return lastname;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setLastname(java.lang.String)
	 */
    @Override
	public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getEmail()
	 */
    @Override
	public String getEmail() {
        return email;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setEmail(java.lang.String)
	 */
    @Override
	public void setEmail(String email) {
        this.email = email;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getEnabled()
	 */
    @Override
	public Boolean getEnabled() {
        return enabled;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setEnabled(java.lang.Boolean)
	 */
    @Override
	public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getAuthorities()
	 */
    @Override
	public List<JpaAuthority> getAuthorities() {
        return authorities;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void setAuthorities(List<? extends Authority> authorities) {
        this.authorities = (List<JpaAuthority>) authorities;
	}
	
    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#getLastPasswordResetDate()
	 */
    @Override
	public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.User#setLastPasswordResetDate(java.util.Date)
	 */
    @Override
	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

}