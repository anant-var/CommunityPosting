package com.example.feedservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @PostMapping(value="/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if(file.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error","empty file"));
            String original = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int idx = original.lastIndexOf('.');
            if(idx >= 0) ext = original.substring(idx);
            String id = UUID.randomUUID().toString();
            String filename = id + ext;
            Path dir = Paths.get(uploadDir);
            if(!Files.exists(dir)) Files.createDirectories(dir);
            Path target = dir.resolve(filename);
            try (InputStream in = file.getInputStream()){
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
            String url = "/uploads/" + filename;
            return ResponseEntity.ok(Map.of(
                    "fileId", id,
                    "name", original,
                    "type", file.getContentType(),
                    "url", url
            ));
        } catch (IOException ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error","upload failed"));
        }
    }
}
