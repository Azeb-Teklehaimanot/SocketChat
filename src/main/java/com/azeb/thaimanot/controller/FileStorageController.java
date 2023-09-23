package com.azeb.thaimanot.controller;

import com.azeb.thaimanot.service.FileStorageService;
import com.azeb.thaimanot.dto.FileUploadDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/files")
@Api(value = "File Storage Management", tags = "File Storage Management")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    @ApiOperation(value = "Upload a file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File uploaded successfully"),
            @ApiResponse(code = 500, message = "Server error while uploading file")
    })
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String fileUrl = null;
        try {
            fileUrl = fileStorageService.storeFile(file);
            FileUploadDto fileUploadResponseDto = new FileUploadDto();
            fileUploadResponseDto.setFileUrl(fileUrl);
            fileUploadResponseDto.setSuccess(true);
            fileUploadResponseDto.setMessage("Success");
            return ResponseEntity.ok(fileUploadResponseDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download")
    @ApiOperation(value = "Download a file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File downloaded successfully"),
            @ApiResponse(code = 404, message = "File not found"),
            @ApiResponse(code = 500, message = "Server error while downloading file")
    })
    public ResponseEntity<Resource> downloadFile(@RequestParam Long messageId) throws IOException {
        Resource resource = fileStorageService.loadFile(messageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
