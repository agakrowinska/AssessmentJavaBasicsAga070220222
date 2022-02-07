package com.example.demo.appDoctor;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
//generates constructor with no parameters
public class AppDoctorService implements UserDetailsService {

    private final static String DOCTOR_NOT_FOUND = "hospital employee with email %s is not found";
    private final AppDoctorRepository appDoctorRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appDoctorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(DOCTOR_NOT_FOUND, email)));
                //Thrown if an UserDetailsService implementation cannot
                // locate a Hospital Employee by its email.
    }
}
