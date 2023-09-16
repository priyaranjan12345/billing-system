package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private int billingDate;
    private int invNo;
    private int noOfItems;
    private float discount;
    private float gst;
    private float grandTotal;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "itemSoldId")
    private SoldItems soldItems;
}
