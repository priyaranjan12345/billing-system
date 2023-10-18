package com.app.billingsystem.service;

import com.app.billingsystem.exception.BadApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${image.directory.path}")
    private String imageFolderPath;
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public String uploadImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName :{}", originalFilename);
        String filename = UUID.randomUUID().toString();
        assert originalFilename != null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = imageFolderPath + File.separator + fileNameWithExtension;
        if ((extension.equalsIgnoreCase(".png")) ||
                (extension.equalsIgnoreCase(".jpg")) ||
                (extension.equalsIgnoreCase(".jpeg"))) {
            File folder = new File(imageFolderPath);
            if (!folder.exists()) {
                var result = folder.mkdirs();
                logger.info("folder created result: {}", result);
            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        } else {
            throw new BadApiRequestException("File with this" + extension + "not allowed !!!");

        }
    }

    public void deleteFile(String imagePath) throws IOException {
        String fullPath = imageFolderPath + imagePath;
        Path path = Paths.get(fullPath);
        Files.delete(path);
    }

    public InputStream getImage(String name) throws FileNotFoundException {
        try {
            String fullPathWithFileName = imageFolderPath + name;
            return new FileInputStream(fullPathWithFileName);
        } catch (Exception e) {
            logger.info("exception on fetch file :{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
