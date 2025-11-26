package com.example.demoLongbow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull(message = "Email is Required")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",message = "Invalid Email")
    private String email;
    @NotNull(message = "Password is Required")
    @Size(min = 8, message = "Password is Minium 8 Characters")
    private String password;

}
