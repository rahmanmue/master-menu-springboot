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

import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.repository.EmployeeRepository;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.utils.enums.EPosition;
import com.enigmacamp.mastermenu.utils.enums.ERole;

@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveEmployee(){
        Role role = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("admin1@gmail.com",Set.of(savedRole));
        User savedUser = userRepository.save(user);

        String fullName = "employee";
        String nip = "W-89763PP";
        EPosition position = EPosition.CASHIER;
        Employee employee = TestDataFactory.createEmployee(fullName, nip, position, savedUser);
        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getId());
        assertEquals(savedUser.getId(), savedEmployee.getUser().getId());
        assertEquals(fullName, savedEmployee.getFullName());
    
    }

    @Test
    public void testGetAllEmployee(){
        Role role = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role savedRole = roleRepository.save(role);

        String fullName1 = "testemployee1";
        String nip1 = "W-89763PP";
        EPosition position1 = EPosition.CASHIER;

        String fullName2 = "testemployee2";
        String nip2 = "L-89763JK";
        EPosition position2 = EPosition.WAITER;


        User admin1 = TestDataFactory.createUser("admin1@gmail.com",Set.of(savedRole));
        User savedUser1 = userRepository.save(admin1);
        User admin2 = TestDataFactory.createUser("admin2@gmail.com", Set.of(savedRole));
        User savedUser2 = userRepository.save(admin2);

        Employee customer1 = TestDataFactory.createEmployee(fullName1,nip1, position1, savedUser1);
        Employee customer2 = TestDataFactory.createEmployee(fullName2, nip2, position2, savedUser2);

        employeeRepository.save(customer1);
        employeeRepository.save(customer2);


        List<Employee> employees = employeeRepository.getAllEmployee();

        assertNotNull(employees);
        assertEquals(2, employees.size());

    }

    @Test
    public void testFindEmployeeByName(){
        String fullName = "testemployee";
        String nip = "W-89763PP";
        EPosition position = EPosition.CASHIER;

        Role role = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("admin1@gmail.com", Set.of(savedRole));
        User savedUser = userRepository.save(user);

        Employee employee = TestDataFactory.createEmployee(fullName, nip, position,  savedUser);
        employeeRepository.save(employee);

        List<Employee> foundEmployees = employeeRepository.getAllEmployeeByName(fullName);

        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        assertEquals(fullName, foundEmployees.get(0).getFullName());

    }

    @Test
    public void testFindEmployeeByNip(){
        String fullName = "testemployee";
        String nip = "W-89763PP";
        EPosition position = EPosition.CASHIER;

        Role role = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("admin1@gmail.com", Set.of(savedRole));
        User savedUser = userRepository.save(user);

        Employee employee = TestDataFactory.createEmployee(fullName,nip, position,  savedUser);
        employeeRepository.save(employee);

        Employee foundEmployees = employeeRepository.getEmployeeByNip(nip);

        assertNotNull(foundEmployees);
        assertEquals(nip, foundEmployees.getNip());

    }

    @Test
    public void testFindEmployeeByPosition(){
        String fullName = "testemployee";
        String nip = "W-89763PP";
        EPosition position = EPosition.CASHIER;


        Role role = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("admin1@gmail.com", Set.of(savedRole));
        User savedUser = userRepository.save(user);

        Employee employee = TestDataFactory.createEmployee(fullName,nip, position,  savedUser);
        employeeRepository.save(employee);

        List<Employee> foundEmployees = employeeRepository.getEmployeeByPosition(position);

        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        assertEquals(fullName, foundEmployees.get(0).getFullName());

    }

     // Test Soft Delete
     @Test
     public void testSoftDeleteCustomer(){
        String fullName = "testemployee";
        String nip = "W-89763PP";
        EPosition position = EPosition.CASHIER;

        Role role = Role.builder().name(ERole.ROLE_ADMIN).build();
        Role savedRole = roleRepository.save(role);

        User user = TestDataFactory.createUser("admin1@gmail.com", Set.of(savedRole));
        User savedUser = userRepository.save(user);

        Employee employee = TestDataFactory.createEmployee(fullName, nip, position,  savedUser);
        Employee savedEmployee = employeeRepository.save(employee);

        employeeRepository.deleteById(savedEmployee.getId());

        List<Employee> employees = employeeRepository.getAllEmployee();
        assertTrue(employees.isEmpty());

        Optional <Employee> deleteCustomer = employeeRepository.findById(savedEmployee.getId());
        assertTrue(deleteCustomer.isPresent());
        assertTrue(deleteCustomer.get().isDeleted());

     }


    
}
