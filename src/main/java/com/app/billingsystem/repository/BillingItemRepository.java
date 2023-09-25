package com.app.billingsystem.repository;

import com.app.billingsystem.models.entities.BillingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingItemRepository extends JpaRepository<BillingItem, Long> {

}
