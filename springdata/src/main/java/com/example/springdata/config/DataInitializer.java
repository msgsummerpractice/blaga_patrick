package com.example.springdata.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.springdata.model.Role;
import com.example.springdata.model.User;
import com.example.springdata.repository.RoleRepository;
import com.example.springdata.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);

            logger.info("Roles initialized");

        }

        if(userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found"));
            Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("User role not found"));

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@admin.com");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Admin");
            adminUser.getRoles().add(adminRole);
            userRepository.save(adminUser);

            User user = new User();
            user.setUsername("user");
            user.setEmail("user@user.com");
            user.setPassword(passwordEncoder.encode("user"));
            user.setFirstName("User");
            user.setLastName("User");
            user.getRoles().add(userRole);
            userRepository.save(user);

            logger.info("Users initialized");
        }
    }
}