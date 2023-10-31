package com.example.app.validation;

import com.example.app.http.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegisterReqValidator implements ConstraintValidator<ValidRegisterReq,RegisterRequest> {

    public boolean isValid(){
        return false;
    }

    @Override
    public boolean isValid(RegisterRequest req, ConstraintValidatorContext constraintValidatorContext) {
        if (req.getPassword().length() < 8){
            return false;
        }
        if(req.getPassword().equals(req.getConfirmPassword()) != true){
            return false;
        }
        return true;
    }
}
