package com.enigmacamp.mastermenu.repositoryTests;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.utils.enums.EGender;
import com.enigmacamp.mastermenu.utils.enums.EPosition;

public class TestDataFactory {
    @Autowired
    RoleRepository roleRepository;

    public static User createUser(String email, Set<Role> roles){
        return User.builder()
                .email(email)
                .password("test123")
                .roles(roles)
                .build();
    }


     public static Customer createCustomer(String fullName, User user) {
        return Customer.builder()
            .fullName(fullName)
            .dateOfBirth(java.sql.Date.valueOf("2000-01-01"))
            .address("123 Main Street")
            .phone("123456789")
            .gender(EGender.MALE)
            .user(user) // Relasi dengan User
            .build();
    }

    public static Employee createEmployee(String fullName, String nip, EPosition position, User user){
        return Employee.builder()
            .fullName(fullName)
            .nip(nip)
            .position(position)
            .dateOfBirth(java.sql.Date.valueOf("1999-01-01"))
            .address("54321 Last Street")
            .phone("123456679")
            .gender(EGender.FEMALE)
            .user(user)
            .build();
    }
}
