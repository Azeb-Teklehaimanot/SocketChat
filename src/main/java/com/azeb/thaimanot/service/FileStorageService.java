package com.azeb.thaimanot.service;

import com.azeb.thaimanot.entity.Message;
import com.azeb.thaimanot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @Value("${video.upload-dir}")
    private String videoUploadDir;

    @Value("${video.min-size}")
    private long videoMinSize;

    @Autowired
    MessageRepository messageService;

    public String storeFile(MultipartFile file) throws IOException {
        String uploadDir = null;
        if (file.getContentType().startsWith("video")) {
            uploadDir = videoUploadDir;
            if (file.getSize() < videoMinSize) {
                throw new IOException("Video file size is below the minimum allowed size.");
            }
        } else {
            uploadDir = fileUploadDir;
        }
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(uploadDir, uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toAbsolutePath().toFile().getAbsolutePath();
    }

    private String generateUniqueFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        return uniqueFileName;
    }

    public Resource loadFile(Long messageId) {
        Optional<Message> messageOptional = messageService.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            Path filePath = Paths.get(message.getFilePath()).normalize();
            Resource resource = null;
            try {
                resource = new UrlResource(filePath.toUri());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + filePath);
            }
        }
        return null;
    }
}
