package com.enigmacamp.mastermenu.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.utils.enums.ERole;

@ActiveProfiles("test")
@DataJpaTest
public class CustomerRepositoryTest {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveCustomer(){
        Role role = Role.builder().name(ERole.ROLE_USER).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("user1@gmail.com",Set.of(savedRole));
        User savedUser = userRepository.save(user);

        String fullName = "cust";
        Customer customer = TestDataFactory.createCustomer(fullName, savedUser);
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals(savedUser.getId(), savedCustomer.getUser().getId());
        assertEquals(fullName, savedCustomer.getFullName());
    
    }

    @Test
    public void testGetAllCutomer(){
        Role role = Role.builder().name(ERole.ROLE_USER).build();
        Role savedRole = roleRepository.save(role);

        String fullName1 = "testcustomer1";
        String fullName2 = "testcustomer2";


        User user1 = TestDataFactory.createUser("user1@gmail.com",Set.of(savedRole));
        User savedUser1 = userRepository.save(user1);
        User user2 = TestDataFactory.createUser("user2@gmail.com", Set.of(savedRole));
        User savedUser2 = userRepository.save(user2);

        Customer customer1 = TestDataFactory.createCustomer(fullName1, savedUser1);
        Customer customer2 = TestDataFactory.createCustomer(fullName2, savedUser2);

        customerRepository.save(customer1);
        customerRepository.save(customer2);


        List<Customer> customers = customerRepository.getAllCustomer();

        assertNotNull(customers);
        assertEquals(2, customers.size());

    }

    @Test
    public void testFindCustomerByName(){
        String fullName = "testcustomer";
        Role role = Role.builder().name(ERole.ROLE_USER).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("user1@gmail.com", Set.of(savedRole));
        User savedUser = userRepository.save(user);

        Customer customer = TestDataFactory.createCustomer(fullName, savedUser);
        customerRepository.save(customer);

        List<Customer> foundCustomers = customerRepository.getAllCustomerByName(fullName);

        assertNotNull(foundCustomers);
        assertEquals(1, foundCustomers.size());
        assertEquals(fullName, foundCustomers.get(0).getFullName());

    }

     // Test Soft Delete
     @Test
     public void testSoftDeleteCustomer(){
        String fullName = "testcustomer";
        Role role = Role.builder().name(ERole.ROLE_USER).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("user1@gmail.com", Set.of(savedRole));
        User savedUser = userRepository.save(user);

        Customer customer = TestDataFactory.createCustomer(fullName, savedUser);
        Customer savedCustomer = customerRepository.save(customer);

        customerRepository.deleteById(savedCustomer.getId());

        List<Customer> customers = customerRepository.getAllCustomer();
        assertTrue(customers.isEmpty());

        Optional <Customer> deleteCustomer = customerRepository.findById(savedCustomer.getId());
        assertTrue(deleteCustomer.isPresent());
        assertTrue(deleteCustomer.get().isDeleted());

     }



    
}
