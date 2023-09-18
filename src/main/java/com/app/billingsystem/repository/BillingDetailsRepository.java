package com.app.billingsystem.repository;

import com.app.billingsystem.models.entities.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {
}
