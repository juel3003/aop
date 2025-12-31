package com.optum.aog.nacha.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class NachaValidationService {

    public static class ValidationResult {
        private final boolean valid;
        private final String message;
        private final List<String> errors;

        public ValidationResult(boolean valid, String message, List<String> errors) {
            this.valid = valid;
            this.message = message;
            this.errors = errors;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }

        public List<String> getErrors() {
            return errors;
        }
    }

    public ValidationResult validate(InputStream inputStream) throws IOException {
        List<String> errors = new ArrayList<>();
        int lineNumber = 0;
        boolean hasFileHeader = false;
        boolean hasFileControl = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // NACHA records are typically 94 characters long
                if (line.length() != 94) {
                    errors.add("Line " + lineNumber + ": Invalid length. Expected 94, got " + line.length());
                }

                if (lineNumber == 1) {
                    if (line.startsWith("1")) {
                        hasFileHeader = true;
                    } else {
                        errors.add("Line 1: Missing File Header Record (Type 1).");
                    }
                }

                if (line.startsWith("9")) {
                    hasFileControl = true;
                    // Usually the file ends here, but there might be '9999...' filler lines
                }
            }
        }

        if (lineNumber == 0) {
            errors.add("File is empty.");
        } else if (!hasFileControl) {
            errors.add("Missing File Control Record (Type 9).");
        }

        boolean valid = errors.isEmpty();
        String message = valid ? "File passed basic NACHA validation." : "File failed validation.";

        return new ValidationResult(valid, message, errors);
    }
}
