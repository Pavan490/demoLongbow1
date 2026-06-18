package com.example.demoLongbow;

import com.example.demoLongbow.dto.SignupApi;
import com.example.demoLongbow.entity.Employee;
import com.example.demoLongbow.repository.EmployeeRepo;
import com.example.demoLongbow.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepo  empRepo;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void createEmployeeShouldEmployeeSuccessfully() throws Exception {
        System.out.println("createEmployeeShouldEmployeeSuccessfully");
//        Employee employee = new Employee();
//        employee.setId(1L);
//        employee.setName("test");
//        employee.setMobile("9908767659");
//        employee.setEmail("email");
//        employee.setPassword("password");
//        Mockito.when(empRepo.save(employee)).thenReturn(employee);
//        Employee createAccount = employeeService.createAccount(employee);
//        Assertions.assertEquals(employee.getId(), createAccount.getId());
    }
}
