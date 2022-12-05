package com.TTN.Ecommerce.security;


import com.TTN.Ecommerce.entity.Role;
import com.TTN.Ecommerce.entity.User;
import com.TTN.Ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	BCryptPasswordEncoder encoder;



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepo.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("User not found for email" + email);
		}
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),user.getPassword(),true,true,true,true,
				getAuthorities(user.getRole()));
	}


	private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		return authorities;
	}


}
