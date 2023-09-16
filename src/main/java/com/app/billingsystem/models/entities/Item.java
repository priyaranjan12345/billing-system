package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private  String name;
    private String description;
    private  double price;
    private String image;
    @ManyToOne
    @JoinColumn(name = "item")
    private SoldItems soldItems;
    @ManyToOne
    @JoinColumn(name="user")
    private User user;



}
