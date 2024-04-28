package com.seroja.restUserApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "user_app")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "first_name", nullable = false, length = 250)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 250)
    private String secondName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "address", nullable = false, length = 250)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

}