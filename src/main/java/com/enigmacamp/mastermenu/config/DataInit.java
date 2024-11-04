package com.enigmacamp.mastermenu.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.utils.enums.ERole;

@Configuration
public class DataInit {
    
    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
        return args -> {
            // Check if roles exist, if not, create them
            if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
                Role role = Role.builder().name(ERole.ROLE_USER).build();
                roleRepository.save(role);
            }
            if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                Role role = Role.builder().name(ERole.ROLE_ADMIN).build();
                roleRepository.save(role);
            }
        };
    }   
}
