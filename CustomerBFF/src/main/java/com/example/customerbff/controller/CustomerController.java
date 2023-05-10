package com.example.customerbff.controller;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CustomerController.java
 * @andrewID wenyuc2
 * @Description TODO
 */
import com.example.customerbff.api.CustomerApi;
import com.example.customerbff.entity.Customer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerApi backendApi;

    @Autowired
    public CustomerController(Retrofit retrofit) {
        backendApi = retrofit.create(CustomerApi.class);
    }

    @PostMapping("")
    public ResponseEntity<JsonNode> addCustomer(@RequestBody Customer customer) throws IOException {
        Response<JsonNode> response = backendApi.addCustomer(customer).execute();
        if (response.code()==200) {
            return ResponseEntity.ok(response.body());
        } else {
            return ResponseEntity.status(response.code()).body(response.body());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonNode> retrieveCustomerById(@PathVariable int id, @RequestHeader("User-Agent") String userAgent) throws IOException {
        Response<JsonNode> response = backendApi.retrieveCustomerById(id).execute();
        if (response.code()==200) {
            JsonNode responseBody = response.body();
            JsonNode modifiedBody = removeAddressFields(responseBody, userAgent);
            return ResponseEntity.ok(modifiedBody);
        } else {
            return ResponseEntity.status(response.code()).body(response.body());
        }
    }

    @GetMapping("")
    public ResponseEntity<JsonNode> retrieveCustomerByUserId(@RequestParam String userId, @RequestHeader("User-Agent") String userAgent) throws IOException {
        Response<JsonNode> response = backendApi.retrieveCustomerByUserId(userId).execute();
        if (response.code()==200) {
            JsonNode responseBody = response.body();
            JsonNode modifiedBody = removeAddressFields(responseBody, userAgent);
            return ResponseEntity.ok(modifiedBody);
        } else {
            return ResponseEntity.status(response.code()).body(response.body());
        }
    }
    private JsonNode removeAddressFields(JsonNode customer, String userAgent) {
        if (userAgent.contains("Mobile")) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode modifiedCustomer = objectMapper.createObjectNode();

            customer.fields().forEachRemaining(entry -> {
                if (!Arrays.asList("address", "address2", "city", "state", "zipcode").contains(entry.getKey())) {
                    modifiedCustomer.set(entry.getKey(), entry.getValue());
                }
            });

            return modifiedCustomer;
        } else {
            return customer;
        }
    }

}

