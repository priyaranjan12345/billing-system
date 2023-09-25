package com.app.billingsystem.service;

import com.app.billingsystem.exception.BadApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
                folder.mkdirs();
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


//    public InputStream getResource(String path, String name) throws FileNotFoundException {
//        String fullPath=path+File.separator+name;
//        InputStream inputStream =new FileInputStream(fullPath);
//        return inputStream;
//    }
}
