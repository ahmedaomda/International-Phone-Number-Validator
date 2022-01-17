package com.example.internationalphonenumbervalidator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    String phone;
    @Transient
    String countryCode;
    @Transient
    String country;
    @Transient
    String state;
}
