package com.yoga.launcher.repository;

import com.yoga.launcher.domain.entity.CustomerExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerExtRepository extends JpaRepository<CustomerExt, Long> {
}
