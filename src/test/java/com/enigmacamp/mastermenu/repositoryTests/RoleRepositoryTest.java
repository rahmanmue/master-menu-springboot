package com.enigmacamp.mastermenu.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.utils.enums.ERole;

@ActiveProfiles("test")
@DataJpaTest
public class RoleRepositoryTest {
    
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveRole(){

        Role roleAdmin = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role roleUser = Role.builder().name(ERole.ROLE_USER).build();

        Role savedAdminRole = roleRepository.save(roleAdmin);
        Role savedUserRole = roleRepository.save(roleUser);

        assertNotNull(savedAdminRole);
        assertNotNull(savedUserRole);

        assertEquals(ERole.ROLE_ADMIN, savedAdminRole.getName());
        assertEquals(ERole.ROLE_USER, savedUserRole.getName());

    }

    @Test
    public void testFindRoleByName(){
        Role roleAdmin = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role savedRole = roleRepository.save(roleAdmin);

        Optional<Role> foundRole = roleRepository.findByName(ERole.ROLE_ADMIN);

        assertNotNull(foundRole);
        assertTrue(foundRole.isPresent());
        assertEquals(savedRole.getName(), foundRole.get().getName());

    }

}
