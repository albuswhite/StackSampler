package com.example.customerbff.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName Customer.java
 * @andrewID wenyuc2
 * @Description TODO
 */


@NoArgsConstructor
@Data
public class Customer {
    @JsonProperty
    private int id;
    @JsonProperty
    private String userId;
    @JsonProperty
    private String name;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String address;
    @JsonProperty
    private String address2;
    @JsonProperty
    private String city;
    @JsonProperty
    private String state;
    @JsonProperty
    private String zipcode;

}
