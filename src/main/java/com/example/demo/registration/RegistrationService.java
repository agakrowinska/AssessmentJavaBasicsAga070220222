package com.example.demo.registration;

import com.example.demo.appDoctor.AppDoctor;
import com.example.demo.appDoctor.AppDoctorRole;
import com.example.demo.appDoctor.AppDoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppDoctorService appDoctorService;

    public String register(RegistrationRequest request) {
        //i want to check if the email is actually valid
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email address is not valid.");
        }
        return appDoctorService.signUpEmployee(
                new AppDoctor(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppDoctorRole.USER
                )
        );
    }
}
