package com.optum.aog.nacha.controller;

import com.optum.aog.nacha.dto.NachaValidationResponse;
import com.optum.aog.nacha.service.NachaValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/nacha")
public class NachaController {

    private final NachaValidationService nachaValidationService;

    @Autowired
    public NachaController(NachaValidationService nachaValidationService) {
        this.nachaValidationService = nachaValidationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<NachaValidationResponse> validateFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(
                    new NachaValidationResponse(false, "File is empty.", List.of("No file provided."))
                );
            }

            NachaValidationService.ValidationResult result = nachaValidationService.validate(file.getInputStream());

            return ResponseEntity.ok(
                new NachaValidationResponse(result.isValid(), result.getMessage(), result.getErrors())
            );

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(
                new NachaValidationResponse(false, "Error reading file.", List.of(e.getMessage()))
            );
        }
    }
}
