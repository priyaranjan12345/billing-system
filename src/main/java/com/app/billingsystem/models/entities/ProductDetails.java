package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
public class ProductDetails {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String desc;
    private String unit;
    private double unitPrice;
    private String qrcode;
    private String createdDate;
    private String lastModifiedDate; // keep history
    private String imagePath;
    // fk userId
}
