package com.example.demoLongbow.service;

import com.example.demoLongbow.dto.ForgotPassword;
import com.example.demoLongbow.dto.LoginDto;
import com.example.demoLongbow.dto.ResetPassword;
import com.example.demoLongbow.dto.SignupApi;
import com.example.demoLongbow.entity.Employee;
import com.example.demoLongbow.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    public EmailService emailService;
    @Autowired
    public JwtToken jwtToken;
    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Employee createAccount(SignupApi signupApi) throws Exception {
        Optional<Employee> employee = employeeRepo.findByEmail(signupApi.getEmail());
        if (employee.isPresent()) {
            throw new Exception("Email already exist. please login");
        }
        Employee employee1 = new Employee();
        employee1.setName(signupApi.getName());
        employee1.setEmail(signupApi.getEmail());
        employee1.setMobile(signupApi.getMobile());
        employee1.setPassword(passwordEncoder.encode(signupApi.getPassword()));
        return employeeRepo.save(employee1);

    }

    public List<Employee> getAllEmp() {
        return employeeRepo.findAll();
    }

    public Employee getByEmp(Long id) throws Exception {
        return employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found:" + id));
    }

    public Employee updateEmp(Long id, Employee employee) {
        Employee employee1 = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found:" + id));
        employee1.setName(employee.getName());
        employee1.setEmail(employee.getEmail());
        employee1.setMobile(employee.getMobile());
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {

            employee1.setPassword(passwordEncoder.encode(employee.getPassword()));
        }

        return employeeRepo.save(employee1);
    }

    public void deleteByEmp(Long id) {
        Employee deleteEmp = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found:" + id));
        employeeRepo.delete(deleteEmp);
    }

    public Employee updatePatch(Long id, Map<String, Object> updates) {
        Employee emp = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found:" + id));
        if (updates.containsKey("name")) {
            emp.setName((String) updates.get("name"));
        }
        if (updates.containsKey("email")) {
            emp.setName((String) updates.get("email"));
        }
        if (updates.containsKey("mobile")) {
            emp.setName((String) updates.get("mobile"));
        }

        if (updates.containsKey("password")) {
            String newPassword = (String) updates.get("password");
            emp.setName(passwordEncoder.encode(newPassword));
        }

        return employeeRepo.save(emp);
    }

    public Map<String,Object> loginEmp(LoginDto loginDto) throws Exception {
        Optional<Employee> employee = employeeRepo.findByEmail(loginDto.getEmail());
        if (employee.isEmpty()) {
            throw new Exception("Email not register. please signup");
        } else {
            Employee employee1 = employee.get();
            boolean isMatched = passwordEncoder.matches(loginDto.getPassword(), employee1.getPassword());
            if (!isMatched) {
                Map<String,Object>res=new HashMap<>();
                res.put("employeeData",employee1);
                res.put("token",jwtToken.generateToken(employee1));
                return res;
            }else {
                throw new Exception("Invalid Password,please try again");
            }

        }
    }

    public void forgotPassword(ForgotPassword forgotPassword) throws Exception {
        Optional<Employee> employee = employeeRepo.findByEmail(forgotPassword.getEmail());
        if (employee.isEmpty()) {
            throw new Exception("Email not register. please signup");
        } else {
            String passkey = UUID.randomUUID().toString();
            Employee employee1 = employee.get();
            employee1.setLinkId(passkey);
            employeeRepo.save(employee1);
            emailService.templateEmailFormat("kattapavankumar490@gmail.com",employee1.getEmail(),"Forgot Password","welocome");
        }

    }
    public void resetPassword(ResetPassword resetPassword) throws Exception {
        if(resetPassword.getPassword().equals(resetPassword.getConfirmPassword())==false){
            throw new Exception("Passwords not matched.please check it");
        }
      Optional<Employee> dbData=employeeRepo.linkId(resetPassword.getLinkId());
        if(dbData.isEmpty()){
            throw new Exception("passkey does not match");
        }
        Employee ddDta=dbData.get();
        ddDta.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        ddDta.setLinkId("");
        employeeRepo.save(ddDta);
    }
}
