package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "hr")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private Long number;
    private String address;
    private String role = "HR";   // Fixed role for HR users
    private String password;

    private Boolean deleted = false;
    private Boolean active = true;

}
