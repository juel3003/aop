package com.optum.aog.nacha.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NachaValidationServiceTest {

    private final NachaValidationService service = new NachaValidationService();

    @Test
    public void testValidFile() throws IOException {
        String content =
            "101 000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n" +
            "5000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n" +
            "9000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

        NachaValidationService.ValidationResult result = service.validate(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        Assertions.assertTrue(result.isValid());
        Assertions.assertEquals("File passed basic NACHA validation.", result.getMessage());
        Assertions.assertTrue(result.getErrors().isEmpty());
    }

    @Test
    public void testInvalidLength() throws IOException {
        String content = "101 SHORT LINE";

        NachaValidationService.ValidationResult result = service.validate(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        Assertions.assertFalse(result.isValid());
        Assertions.assertTrue(result.getErrors().get(0).contains("Invalid length"));
    }

    @Test
    public void testMissingHeader() throws IOException {
        String content =
            "5000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n" +
            "9000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

        NachaValidationService.ValidationResult result = service.validate(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        Assertions.assertFalse(result.isValid());
        Assertions.assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Missing File Header Record")));
    }

    @Test
    public void testMissingControl() throws IOException {
        String content =
            "101 000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n" +
            "5000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

        NachaValidationService.ValidationResult result = service.validate(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        Assertions.assertFalse(result.isValid());
        Assertions.assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Missing File Control Record")));
    }
}
