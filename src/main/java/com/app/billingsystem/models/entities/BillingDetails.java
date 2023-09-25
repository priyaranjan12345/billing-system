package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BillingDetails")
public class BillingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime billingDate;

    @Column(nullable = false)
    private String invNo;

    @Column(nullable = false)
    private int noOfItems;

    @Column(nullable = false)
    private Double discount;

    @Column(nullable = false)
    private Double gst;

    @Column(nullable = false)
    private Double grandTotal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
