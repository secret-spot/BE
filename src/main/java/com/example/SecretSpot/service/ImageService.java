package com.example.SecretSpot.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile file) throws IOException {
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String type = file.getContentType();

            String gcsFilename = uuid + "_" + originalFilename;
            storage.create(BlobInfo.newBuilder(BlobId.of(bucketName, gcsFilename))
                    .setContentType(type).build(), file.getBytes());
            return "https://storage.googleapis.com/" + bucketName + "/" + gcsFilename;
    }

    public String getFile(String filename) {
        Blob blob = storage.get(BlobId.of(bucketName, filename));
        if (blob == null || !blob.exists()) {
            throw new IllegalArgumentException("gcs에서 파일을 찾을 수 없음");
        }
        return "https://storage.googleapis.com/" + bucketName + "/" + filename;
    }
}
