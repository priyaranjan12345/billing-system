package com.app.billingsystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager-api")
public class ManagerController {
    @GetMapping("/get-admin-data")
    public String getData() {
        return "Get: Hello Manager";
    }

    @PostMapping("/post-admin-data")
    public String postData() {
        return "Post: Hello Manager";
    }
}

