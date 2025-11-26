package com.example.demoLongbow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPassword {

    @NotNull(message = "passkey is mandatory")
    private String linkId;
    @NotNull(message = "Password is Required")
    @Size(min = 8, message = "Password is Minium 8 Characters")
    private String password;
    @NotNull(message = "Password is Required")
    @Size(min = 8, message = "Confirm Password is Minium 8 Characters")
    private String confirmPassword;

}
