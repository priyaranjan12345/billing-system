package com.app.billingsystem.models.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

public class UserCart {
    private Integer id;
    private Integer quantity;

    @OneToMany
    private List<Item> items;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
