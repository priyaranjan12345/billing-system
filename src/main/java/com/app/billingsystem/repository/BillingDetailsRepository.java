package com.app.billingsystem.repository;

import com.app.billingsystem.models.entities.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {
    List<BillingDetails> getBillingDetailsByUserId(Long userId);
}