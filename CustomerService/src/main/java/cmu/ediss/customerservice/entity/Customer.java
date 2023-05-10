package cmu.ediss.customerservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
@TableName(value = "Customer", autoResultMap = true)
@NoArgsConstructor
@Data
@Entity
public class Customer {
    @Id
    @TableId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Email
    @JsonProperty
    @TableField(value = "userId")
    private String userId;
    @NotNull
    @TableField(value = "name")
    private String name;
    @NotNull
    @TableField(value = "phone")
    private String phone;
    @NotNull
    @TableField(value = "address")
    private String address;
    @TableField(value = "address2")
    private String address2;
    @NotNull
    @TableField(value = "city")
    private String city;
    @NotNull
    @TableField(value = "state")
    private String state;
    @NotNull
    @TableField(value = "zipcode")
    private String zipcode;

}
