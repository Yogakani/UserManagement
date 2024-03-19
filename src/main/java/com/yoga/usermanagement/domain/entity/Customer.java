package com.yoga.usermanagement.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "customer")
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile_num")
    private long mobileNum;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private long zipCode;

    @Column(name = "create_ts")
    private LocalDateTime createTs;

    @Column(name = "last_mod_ts")
    private LocalDateTime lastModTs = LocalDateTime.now();
}
