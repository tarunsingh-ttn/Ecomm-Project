package com.TTN.Ecommerce;

import com.TTN.Ecommerce.Entities.Role;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Repositories.RoleRepository;
import com.TTN.Ecommerce.Repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("bootstrap this");
        logger.info("BootstrapCommandLineRunner's run method started.");

        if(roleRepository.count()<1){
            Role role1 = new Role();
            role1.setRole_id(1);
            role1.setAuthority("ADMIN");

            Role role2 = new Role();
            role2.setRole_id(2);
            role2.setAuthority("SELLER");

            Role role3 = new Role();
            role3.setRole_id(3);
            role3.setAuthority("CUSTOMER");

            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);

        }
        if(userRepository.count()<1) {

            User user = new User();
            user.setFirstName("Super");
            user.setLastName("User");
            user.setEmail("admin");
            user.setPassword("admin");
            Role role = roleRepository.findById(1).get();
            user.setRole(role);
            userRepository.save(user);
        }

    }
}
