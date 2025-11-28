package com.example.demoLongbow.controller;

import com.example.demoLongbow.dto.ForgotPassword;
import com.example.demoLongbow.dto.LoginDto;
import com.example.demoLongbow.dto.ResetPassword;
import com.example.demoLongbow.dto.SignupApi;
import com.example.demoLongbow.entity.Employee;
import com.example.demoLongbow.service.EmailService;
import com.example.demoLongbow.service.EmployeeService;
import com.example.demoLongbow.service.JwtToken;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private EmployeeService employeeService;
    
 @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> createAccount(@Valid @RequestBody SignupApi signupApi) throws Exception{
        Employee employee= employeeService.createAccount(signupApi);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("data",employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAll() throws Exception{
       List< Employee> employee= employeeService.getAllEmp();
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("data",employee);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<Map<String, Object>> getByEmp(@PathVariable Long id) throws Exception{
        Employee employee= employeeService.getByEmp(id);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("data",employee);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateEmp(@PathVariable Long id,@RequestBody Employee employee) throws Exception{
         Employee employee1= employeeService.updateEmp(id,employee);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("data",employee1);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteByEmp(@PathVariable Long id) throws Exception{
         employeeService.deleteByEmp(id);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("data","Employee removed Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Map<String, Object>> updatePatch(@PathVariable Long id,@RequestBody Map<String,Object> data) throws Exception{
        Employee employee1= employeeService.updatePatch(id,data);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("data",employee1);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }




    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginEmp(@RequestHeader("Authorization") String JwtToken,@Valid @RequestBody LoginDto loginDto) throws Exception{
         if(JwtToken==null || !JwtToken.startsWith("Bearer")){
             throw new Exception("unauthorized");
         }
         JwtToken=JwtToken.substring(7);
         Boolean isVerify=jwtToken.verifyJwtToken(JwtToken);
        Claims claims=jwtToken.getJwtClaims(JwtToken);
        claims.get("name");
        Map<String,Object> employee= employeeService.loginEmp(loginDto);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("data",employee);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@Valid @RequestBody ForgotPassword forgotPassword) throws Exception{
        employeeService.forgotPassword(forgotPassword);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("message","we sent an email and password reset below link. please check your span emails");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@Valid @RequestBody ResetPassword resetPassword) throws Exception{
        employeeService.resetPassword(resetPassword);
        Map<String,Object> res=new HashMap<>();
        res.put("result","success");
        res.put("message","Password is Updated Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @GetMapping("/demo")
     public ResponseEntity<String> getDemo() {
     return ResponseEntity.status(HttpStatus.OK).body("Longbow Technologies Successfully  helo");
 }

    @GetMapping("/sample")
    public ResponseEntity<String> getSample() {
        return ResponseEntity.status(HttpStatus.OK).body("Sample Code!!!!");
    }


}
