package com.dancesys.dancesys.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesService {
    public String uploadFile(MultipartFile file) throws IOException;
}
