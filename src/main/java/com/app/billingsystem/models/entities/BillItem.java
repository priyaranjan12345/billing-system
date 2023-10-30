package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill_item")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int itemQnt;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "billing_details_id")
    private BillDetails billDetails;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
