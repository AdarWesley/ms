package org.awesley.digital.login.persistence.implementation.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.awesley.digital.login.service.model.Authority;
import org.awesley.digital.login.service.model.AuthorityName;

@Entity
@Table(name = "AUTHORITY")
public class JpaAuthority implements Authority {

	public JpaAuthority() {
		
	}
	
	public JpaAuthority(AuthorityName name) {
		this.name = name;
	}
	
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.Authority#getId()
	 */
    @Override
	public Long getId() {
        return id;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.Authority#setId(java.lang.Long)
	 */
    @Override
	public void setId(Long id) {
        this.id = id;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.Authority#getName()
	 */
    @Override
	public AuthorityName getName() {
        return name;
    }

    /* (non-Javadoc)
	 * @see org.awesley.digital.login.persistence.implementation.jpa.entities.Authority#setName(org.awesley.digital.login.persistence.implementation.jpa.entities.AuthorityName)
	 */
    @Override
	public void setName(AuthorityName name) {
        this.name = name;
    }
}