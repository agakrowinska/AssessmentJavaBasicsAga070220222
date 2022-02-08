package com.example.demo.registration.token;

import com.example.demo.appDoctor.AppDoctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"

    )


    //I prevent null values by setting the nullable of the column annotation to false.
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime willExpireAt;

    private LocalDateTime confirmedAt;

    //i have to connotate token to particular user

    //app doctor can have many conf tokens
    @ManyToOne
    @JoinColumn(nullable = false,name = "app_doctor_id")
    private AppDoctor appDoctor;


    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime willExpireAt,
                             AppDoctor appDoctor) {
        this.token = token;
        this.createdAt = createdAt;
        this.willExpireAt = willExpireAt;
        this.appDoctor = appDoctor;
    }
}
