package com.example.customerbff.api;

import com.example.customerbff.entity.Customer;
import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CustomerApi.java
 * @andrewID wenyuc2
 * @Description TODO
 */
public interface CustomerApi {
    @POST("/customers")
    Call<JsonNode> addCustomer(@Body Customer customer);

    @GET("/customers/{id}")
    Call<JsonNode> retrieveCustomerById(@Path("id") int id);

    @GET("/customers")
    Call<JsonNode> retrieveCustomerByUserId(@Query("userId") String userId);
}
