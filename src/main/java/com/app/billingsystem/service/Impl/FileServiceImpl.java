package com.app.billingsystem.service.Impl;

import com.app.billingsystem.exception.BadApiRequestException;
import com.app.billingsystem.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service

public class FileServiceImpl implements FileService {
    @Value("${image.directory.path}")
    private String imagePath;
    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName :{}",originalFilename);
        String filename=UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=filename+extension;
        String fullPathWithFileName=imagePath+ File.separator+fileNameWithExtension;
        if ((extension.equalsIgnoreCase(".png")) ||
                (extension.equalsIgnoreCase(".jpg")) ||
                (extension.equalsIgnoreCase(".jpeg"))){
            File folder=new File(imagePath);
            if (!folder.exists()){
//                create folder
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return  fileNameWithExtension;
        }else {
            throw  new BadApiRequestException("File with this"+extension+"not allowed !!!");

        }
    }

//    @Override
//    public InputStream getResource(String path, String name) throws FileNotFoundException {
//        String fullPath=path+File.separator+name;
//        InputStream inputStream =new FileInputStream(fullPath);
//        return inputStream;
//    }
}
