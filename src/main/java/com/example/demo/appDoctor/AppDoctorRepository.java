package com.example.demo.appDoctor;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppDoctorRepository {

    Optional<AppDoctor> findByEmail(String email);
}
