package com.example.bookbff.api;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CustomerApi.java
 * @andrewID wenyuc2
 * @Description TODO
 */

import com.example.bookbff.entity.Book;
import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.*;

public interface BookApi {

    @POST("/books")
    Call<JsonNode> addBook(@Body Book book);

    @PUT("/books/{ISBN}")
    Call<JsonNode> updateBook(@Body Book book, @Path("ISBN") String ISBN);

    @GET("/books/{ISBN}")
    Call<JsonNode> retrieveBook(@Path("ISBN") String ISBN);
}

