package com.TTN.Ecommerce.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/*@Entity
public class Role implements GrantedAuthority {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToOne
	private Set<User> users;

	@Override
	public String getAuthority() {
		return name;
	}

}*/

@Entity
@Getter
@Setter
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
	@SequenceGenerator(name="role_gen", sequenceName = "role_seq", initialValue = 1, allocationSize = 1)
	private int role_id;

	private String authority;

}
