package cmu.ediss.bookservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName BookRelatedResponse.java
 * @andrewID wenyuc2
 * @Description TODO
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class RelatedBookDTO {
    @JsonProperty(value = "isbn")
    private String ISBN;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "authors")
    private String Author;

    private String publisher;

}
