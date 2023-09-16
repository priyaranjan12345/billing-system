package com.app.billingsystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String uploadImage(MultipartFile file) throws IOException;}
