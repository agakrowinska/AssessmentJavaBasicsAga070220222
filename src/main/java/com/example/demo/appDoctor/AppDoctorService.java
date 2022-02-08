package com.example.demo.appDoctor;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
//generates constructor with no parameters

public class AppDoctorService implements UserDetailsService {

    private final static String DOCTOR_NOT_FOUND = "hospital employee with email %s is not found";

    private final AppDoctorRepository appDoctorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //after having a token below i have to save it
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appDoctorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(DOCTOR_NOT_FOUND, email)));
                //Thrown if an UserDetailsService implementation cannot
                // locate a Hospital Employee by its email.
    }

    //here the user is being created and the password
    public String signUpEmployee(AppDoctor appDoctor){
        boolean employeeKnown = appDoctorRepository.findByEmail(appDoctor.getEmail()).isPresent();

        if (employeeKnown){
            throw new IllegalStateException("This Email is already existing and in use.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appDoctor.getPassword());

        appDoctor.setPassword(encodedPassword);

        //saving the user in the database
        appDoctorRepository.save(appDoctor);

        //i am sending confirmation token
        //creating the token and saving it using the method from ctService
        String token = UUID.randomUUID().toString();
        ConfirmationToken confToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),appDoctor
        );

        confirmationTokenService.saveConfirmationToken(confToken);

        //i have to send email with token

        return token;
    }

    public int enableAppDoctor (String email){
        return appDoctorRepository.enableAppDoctor(email);
    }
}
