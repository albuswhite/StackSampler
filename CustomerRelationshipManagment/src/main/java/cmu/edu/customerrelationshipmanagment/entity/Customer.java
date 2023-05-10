package cmu.edu.customerrelationshipmanagment.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName Customer.java
 * @andrewID wenyuc2
 * @Description TODO
 */


@EqualsAndHashCode
@NoArgsConstructor
@Data
public class Customer {
    private int id;

    @JsonProperty
    private String userId;

    private String name;

    private String phone;

    private String address;

    private String address2;

    private String city;

    private String state;

    private String zipcode;

}
