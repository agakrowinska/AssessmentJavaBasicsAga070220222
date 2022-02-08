package com.example.demo.appDoctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppDoctorRepository
        extends JpaRepository<AppDoctor, Long> {

    Optional<AppDoctor> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppDoctor a" + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppDoctor (String email);
}
