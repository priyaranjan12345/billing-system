package com.app.billingsystem.repository;

import com.app.billingsystem.models.entities.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingDetailsRepository extends JpaRepository<BillDetails, Long> {
    List<BillDetails> getBillingDetailsByUserId(Long userId);
}