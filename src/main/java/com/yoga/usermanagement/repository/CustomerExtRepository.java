package com.yoga.usermanagement.repository;

import com.yoga.usermanagement.domain.entity.CustomerExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerExtRepository extends JpaRepository<CustomerExt, Long> {
}
