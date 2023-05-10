package com.example.customerbff.controller;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName StatusController.java
 * @andrewID wenyuc2
 * @Description TODO
 */
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping(value = "/status", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("OK");
    }
}

