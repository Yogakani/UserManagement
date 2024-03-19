package com.yoga.usermanagement.repository;

import com.yoga.usermanagement.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserName(String userName);

    Optional<Customer> findByEmailId(String emailId);

    Optional<Customer> findByMobileNum(long mobileNum);
}
