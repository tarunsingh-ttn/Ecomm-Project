/*
package com.TTN.Ecommerce.config;

import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException("Invalid credentials");
        }
        if(user.isIS_LOCKED()){
            throw new BadCredentialsException("Account is locked"); // change the exception
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            int counter = user.getINVALID_ATTEMPT_COUNT();
            if(counter < 2){
                user.setINVALID_ATTEMPT_COUNT(++counter);
                userRepository.save(user);
            } else{
                user.setIS_LOCKED(true);
                user.setINVALID_ATTEMPT_COUNT(0);
                userRepository.save(user);
            }
            throw new BadCredentialsException("Invalid Credentials");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));

        user.setINVALID_ATTEMPT_COUNT(0);
        userRepository.save(user);
        return new UsernamePasswordAuthenticationToken(username, password,authorities);
    }
}*/
