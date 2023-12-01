package com.example.app.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = RegisterReqValidator.class)
public @interface ValidRegisterReq {
    String message() default "{invalid Registration request}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
