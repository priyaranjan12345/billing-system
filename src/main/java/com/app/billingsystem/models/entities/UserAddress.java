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
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;
    private String houseNumber;
    private String areaName;
    private String locality;
    private String streetName;
    private String pinCode;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
