package com.example.internationalphonenumbervalidator.repository;

import com.example.internationalphonenumbervalidator.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
