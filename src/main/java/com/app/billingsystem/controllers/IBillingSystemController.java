package com.app.billingsystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

public interface IBillingSystemController {
    @GetMapping("/users")
    List<String> getUsers();

    // CRUD product
    // CRUD bill
    // CRUD bill items
    // crud stock
}
