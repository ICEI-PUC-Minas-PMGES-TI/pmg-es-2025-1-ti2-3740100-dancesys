package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin("*")
public class FilesController {
    @Autowired
    private FilesService filesService;

    @PostMapping(value = "upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = filesService.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao enviar arquivo: " + e.getMessage());
        }
    }
}
