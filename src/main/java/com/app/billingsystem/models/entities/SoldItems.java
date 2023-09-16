package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ItemsSold")
public class SoldItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int itemSoldId;
    private int itemQnt;
    private float itemUnitPrice;

    @OneToMany(mappedBy = "soldItems")
    private List<Orders> orders;

    @OneToMany(mappedBy = "soldItems")
    private List<Item> items;
}
