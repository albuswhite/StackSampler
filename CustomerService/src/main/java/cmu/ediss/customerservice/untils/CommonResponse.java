package cmu.ediss.customerservice.untils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CommonResponse.java
 * @andrewID wenyuc2
 * @Description TODO
 */
public class CommonResponse {
    @JsonProperty
    private String message;

    public CommonResponse(String message) {
        this.message = message;
    }
}
