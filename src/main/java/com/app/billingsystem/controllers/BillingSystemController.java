package com.app.billingsystem.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/billing-system")
@Tag(name = "Billings System")
public class BillingSystemController implements IBillingSystemController {
    @Override
    public List<String> getUsers() {
        return List.of("Rama", "Sita", "Syaama", "Priyaranjan", "Deba", "Arjun");
    }
}
