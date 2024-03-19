package com.yoga.launcher.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customer_ext")
@NoArgsConstructor
public class CustomerExt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "mobile_verified")
    private boolean mobileVerified;

    @Column(name = "create_ts")
    private LocalDateTime createTs;

    @Column(name = "last_mod_ts")
    private LocalDateTime lastModTs = LocalDateTime.now();

}
