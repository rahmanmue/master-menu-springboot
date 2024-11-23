package com.enigmacamp.mastermenu.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.utils.enums.ERole;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveUser(){
        String email = "test@gmail.com";
        String password = "test123";
        Role role = Role.builder()
                    .name(ERole.ROLE_USER)
                    .build();
        Set<Role> roles = Set.of(role);

        User user = User.builder()
                    .email(email)
                    .password(password)
                    .roles(roles)
                    .build();

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(email, savedUser.getUsername());
        assertEquals(password, savedUser.getPassword());
        assertEquals(roles.size(), savedUser.getRoles().size());
    
    }

    @Test
    public void testFindByEmail(){
        String email = "test@gmail.com";
        String password = "test123";

        Role role = Role.builder()
                    .name(ERole.ROLE_USER)
                    .build();
        
        roleRepository.save(role);

        User user = User.builder()
                    .email(email)
                    .password(password)
                    .roles( Set.of(role))
                    .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail(email);

        assertNotNull(foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getUsername());
    
    }


    // Test Tanpa Role lebih baik dilakukan di service layer
    @Test
    public void testFindByEmailWithoutRole(){
        String email = "test@gmail.com";
        String password = "test123";

        User user = User.builder()
                    .email(email)
                    .password(password)
                    .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail(email);

        assertNotNull(foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getUsername());
    
    }



}
