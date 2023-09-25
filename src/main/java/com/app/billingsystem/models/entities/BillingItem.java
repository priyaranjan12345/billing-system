package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BillingItem")
public class BillingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int itemQnt;

    @ManyToOne
    @JoinColumn(name = "billing_details_id")
    private BillingDetails billingDetails;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
