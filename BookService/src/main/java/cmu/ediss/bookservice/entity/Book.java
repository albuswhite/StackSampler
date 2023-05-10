package cmu.ediss.bookservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;




@EqualsAndHashCode
@TableName(value = "Book", autoResultMap = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Book {
    @TableField(value = "ISBN")
    @JsonProperty(value = "ISBN")
    private String ISBN;

    @NotNull
    @TableField(value = "title")
    private String title;

    @NotNull
    @TableField(value = "Author")
    @JsonProperty(value = "Author")
    private String Author;

    @NotNull
    @TableField(value = "description")
    private String description;

    @NotNull
    @TableField(value = "genre")
    private String genre;

    @NotNull
    @Digits(integer = 7, fraction = 2)
    @Min(value = 0)
    @TableField(value = "price")
    private double price;

    @NotNull
    @TableField(value = "quantity")
    private int quantity;
}
