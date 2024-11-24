package com.enigmacamp.mastermenu.serviceTests;

import com.enigmacamp.mastermenu.model.dto.request.CustomerReq;
import com.enigmacamp.mastermenu.model.dto.request.EmployeeReq;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeRes;
import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.repository.EmployeeRepository;
import com.enigmacamp.mastermenu.service.impl.EmployeeImpl;
import com.enigmacamp.mastermenu.utils.enums.EGender;
import com.enigmacamp.mastermenu.utils.enums.EPosition;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeImpl employeeService;

    private Employee employee;
    private Employee updatedEmployee;
    private EmployeeReq employeeReq;
    private EmployeeReq updatedEmployeeReq;
    private EmployeeRes employeeRes;
    private EmployeeDetailRes employeeDetailRes;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId("1");
        employee.setNip("EMP123");
        employee.setFullName("John Doe");
        employee.setDateOfBirth(new Date());
        employee.setAddress("123 Main St");
        employee.setPhone("1234567890");
        employee.setGender(EGender.MALE);
        employee.setPosition(EPosition.CASHIER);

        employeeReq = new EmployeeReq();
        employeeReq.setId("1");
        employeeReq.setNip("EMP123");
        employeeReq.setFullName("John Doe");
        employeeReq.setDateOfBirth(new Date());
        employeeReq.setAddress("123 Main St");
        employeeReq.setPhone("1234567890");
        employeeReq.setGender(EGender.MALE);

        employeeRes = new EmployeeRes();
        employeeRes.setId("1");

        employeeDetailRes = new EmployeeDetailRes();
        employeeDetailRes.setId("1");
        employeeDetailRes.setNip("EMP123");
        employeeDetailRes.setFullName("John Doe");

        updatedEmployeeReq = new EmployeeReq();
        updatedEmployeeReq.setId("1");
        updatedEmployeeReq.setNip("EMP543");
        updatedEmployeeReq.setFullName("John Doe");
        updatedEmployeeReq.setDateOfBirth(new Date());
        updatedEmployeeReq.setAddress("123 Main St");
        updatedEmployeeReq.setPhone("1234567890");
        updatedEmployeeReq.setGender(EGender.MALE);
        updatedEmployeeReq.setPosition(EPosition.WAITER);
       
        updatedEmployee = new Employee();
        updatedEmployee.setId("1");
        updatedEmployee.setNip("EMP543");
        updatedEmployee.setFullName("John Doe");
        updatedEmployee.setDateOfBirth(new Date());
        updatedEmployee.setAddress("123 Main St");
        updatedEmployee.setPhone("1234567890");
        updatedEmployee.setGender(EGender.MALE);
        updatedEmployee.setPosition(EPosition.WAITER);

    }

    @Test
    void saveEmployee_ShouldSaveAndReturnEmployeeRes() {
        when(modelMapper.map(employeeReq, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeRes.class)).thenReturn(employeeRes);

        EmployeeRes result = employeeService.saveEmployee(employeeReq);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void updateEmployee_ShouldUpdateAndReturnEmployeeRes() {
        when(employeeRepository.findEmployeeByDeletedFalse("1")).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(updatedEmployee);
        doAnswer(invocation -> {
            EmployeeReq req = invocation.getArgument(0);
            Employee target = invocation.getArgument(1);
            target.setFullName(req.getFullName());
            target.setNip(req.getNip());
            target.setAddress(req.getAddress());
            target.setPhone(req.getPhone());
            target.setPosition(req.getPosition());
            target.setGender(req.getGender());
            return null;
        }).when(modelMapper).map(any(EmployeeReq.class), any(Employee.class));
        when(modelMapper.map(eq(updatedEmployee), eq(EmployeeRes.class))).thenReturn(employeeRes);

        EmployeeRes result = employeeService.updateEmployee(updatedEmployeeReq);

        assertNotNull(result);
        assertEquals("1", result.getId());
        
        verify(employeeRepository, times(1)).findEmployeeByDeletedFalse("1");
        verify(employeeRepository,  times(1)).save(employee);
        verify(modelMapper, times(1)).map(updatedEmployeeReq, employee);
        verify(modelMapper, times(1)).map(updatedEmployee, EmployeeRes.class);
    }

    @Test
    void getAllEmployee_ShouldReturnListOfEmployeeDetailRes() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.getAllEmployee()).thenReturn(employees);
        when(modelMapper.map(employee, EmployeeDetailRes.class)).thenReturn(employeeDetailRes);

        List<EmployeeDetailRes> result = employeeService.getAllEmployee();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        verify(employeeRepository, times(1)).getAllEmployee();
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeDetailRes() {
        when(employeeRepository.findEmployeeByDeletedFalse("1")).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeDetailRes.class)).thenReturn(employeeDetailRes);

        EmployeeDetailRes result = employeeService.getEmployeeById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void getEmployeeById_ShouldThrowEntityNotFoundException() {
        when(employeeRepository.findEmployeeByDeletedFalse("1")).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                employeeService.getEmployeeById("1"));

        assertEquals("Employee Not Found", exception.getMessage());
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee() {
        when(employeeRepository.findEmployeeByDeletedFalse("1")).thenReturn(employee);

        employeeService.deleteEmployee("1");

        verify(employeeRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteEmployee_ShouldThrowEntityNotFoundException() {
        when(employeeRepository.findEmployeeByDeletedFalse("1")).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                employeeService.deleteEmployee("1"));

        assertEquals("Employee Not Found", exception.getMessage());
    }
}

