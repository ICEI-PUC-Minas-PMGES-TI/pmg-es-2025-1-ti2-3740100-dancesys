package com.dancesys.dancesys.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String blobUrl = "https://" + accountName + ".blob.core.windows.net/" + containerName;
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        String connectionString = "DefaultEndpointsProtocol=https;AccountName=" + accountName +
                ";AccountKey=" + accountKey + ";EndpointSuffix=core.windows.net";

        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobUrl + "/" + fileName;
    }
}
