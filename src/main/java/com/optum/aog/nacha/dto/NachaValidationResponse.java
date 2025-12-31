package com.optum.aog.nacha.dto;

import java.util.List;

public class NachaValidationResponse {
    private boolean valid;
    private String message;
    private List<String> errors;

    public NachaValidationResponse(boolean valid, String message, List<String> errors) {
        this.valid = valid;
        this.message = message;
        this.errors = errors;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
