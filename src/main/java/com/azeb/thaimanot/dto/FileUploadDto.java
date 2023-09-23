package com.azeb.thaimanot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileUploadDto {
    private boolean success;
    private String fileUrl;
    private String message;
    
    // Constructors, getters, and setters omitted for brevity
}
