package com.example.bookbff.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@AllArgsConstructor
public class Book {
    @JsonProperty(value = "ISBN")
    private String ISBN;
    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "Author")
    private String Author;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "genre")
    private String genre;
    @JsonProperty(value = "price")
    private double price;
    @JsonProperty(value = "quantity")
    private int quantity;
}
