package com.example.bookbff.controller;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CustomerController.java
 * @andrewID wenyuc2
 * @Description TODO
 */

import com.example.bookbff.api.BookApi;
import com.example.bookbff.entity.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookApi backendApi;

    @Autowired
    public BookController(Retrofit retrofit) {
        backendApi = retrofit.create(BookApi.class);
    }

    @PostMapping("")
    public ResponseEntity<JsonNode> addBook(@RequestBody Book book) throws IOException {
        Response<JsonNode> response = backendApi.addBook(book).execute();
        if (response.code()==200) {
            return ResponseEntity.ok(response.body());
        } else {
            return ResponseEntity.status(response.code()).body(response.body());
        }
    }

    @PutMapping("/{ISBN}")
    public ResponseEntity<JsonNode> updateBook(@RequestBody Book book, @PathVariable String ISBN) throws IOException {
        Response<JsonNode> response = backendApi.updateBook(book, ISBN).execute();
        if (response.code()==200) {
            return ResponseEntity.ok(response.body());
        } else {
            return ResponseEntity.status(response.code()).body(response.body());
        }
    }


    @GetMapping({"/{ISBN}", "/isbn/{ISBN}"})
    public ResponseEntity<?> retrieveBookByIsbn(@PathVariable String ISBN, @RequestHeader("User-Agent") String userAgent) throws IOException {
        log.info("retrieve begin");
        Response<JsonNode> response = backendApi.retrieveBook(ISBN).execute();
        if (response.code()==200) {
            JsonNode responseBody = response.body();
            if (userAgent.contains("Mobile")) {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode modifiedBody = objectMapper.createObjectNode();
                responseBody.fields().forEachRemaining(entry -> {
                    JsonNode value = entry.getValue();
                    if (value.isInt() || value.isLong()) {
                        modifiedBody.put(entry.getKey(), value.asInt());
                    } else if (value.isFloat() || value.isDouble()) {
                        modifiedBody.put(entry.getKey(), value.asDouble());
                    } else {
                        String stringValue = value.asText();
                        if (stringValue.contains("non-fiction")) {
                            int intValue = Integer.parseInt(stringValue.replace("non-fiction", "3"));
                            modifiedBody.put(entry.getKey(), intValue);
                        }
                        else {
                            modifiedBody.put(entry.getKey(), stringValue);
                        }
                    }
                });
                return ResponseEntity.ok(modifiedBody);
            } else {
                return ResponseEntity.ok(responseBody);
            }
        } else {
            return ResponseEntity.status(response.code()).body(response.body());
        }
    }
}

