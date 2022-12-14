package com.TTN.Ecommerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
	@Override
	public String toString() {
		return "Role{" +
				"role_id=" + role_id +
				", authority='" + authority + '\'' +
				'}';
	}
}
