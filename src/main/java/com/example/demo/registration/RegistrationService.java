package com.example.demo.registration;

import com.example.demo.appDoctor.AppDoctor;
import com.example.demo.appDoctor.AppDoctorRole;
import com.example.demo.appDoctor.AppDoctorService;
import com.example.demo.email.EmailSender;
import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppDoctorService appDoctorService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;



    public String register(RegistrationRequest request) {
        //i want to check if the email is actually valid
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email address is not valid.");
        }
        String token = appDoctorService.signUpEmployee(
                new AppDoctor(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppDoctorRole.USER
                )
        );
        String link = "http://localhost:8080/api/v1/hospital/confirm?token=" + token;
        emailSender.send (request.getEmail(), buildEmail(request.getFirstName(), link));
        return token;
    }

    @Transactional
    //first im looking fir a token
    public String confirmToken (String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token is not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("This email address is already confirmed");
        }

        LocalDateTime willExpireAt = confirmationToken.getWillExpireAt();

        if (willExpireAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Sorry, your token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appDoctorService.enableAppDoctor(
                confirmationToken.getAppDoctor().getEmail()):
        return "Confirmed!";
    }







    }

}
