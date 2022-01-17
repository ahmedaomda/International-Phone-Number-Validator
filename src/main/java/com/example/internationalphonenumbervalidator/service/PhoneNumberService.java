package com.example.internationalphonenumbervalidator.service;

import com.example.internationalphonenumbervalidator.model.Customer;
import com.example.internationalphonenumbervalidator.repository.CustomerRepository;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {

    private final CustomerRepository repository;

    public PhoneNumberService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Page<Customer> getAllPhoneNumbers(Optional<Integer> page, Optional<Integer> size){
        Page<Customer> customers = repository.findAll(PageRequest.of(page.orElse(1)-1, size.orElse(10)));
        customers.getContent().forEach(customer -> {
            String countryCode = extractCountryCode(customer.getPhone());
            if(countryCode == null){
                customer.setState("Not Valid");
            }else{
                customer.setCountry(getCountryByCode(countryCode));
                customer.setCountryCode(countryCode);
                customer.setState("Valid");
            }
        });
        return customers;
    }

    /**
     * This function used for extracting the country code from phone numbers based on predefined regex for each country code
     * @param phoneNumber the phone number
     * @return the country code or null if the phoneNumber doesn't match any regex
     */
    private String extractCountryCode(String phoneNumber){
        if(Pattern.matches("\\(237\\) ?[2368]\\d{7,8}$",phoneNumber)){
            return "+237";
        }else if(Pattern.matches("\\(251\\) ?[1-59]\\d{8}$",phoneNumber)){
            return "+251";
        }else if(Pattern.matches("\\(212\\) ?[5-9]\\d{8}$",phoneNumber)){
            return "+212";
        }else if(Pattern.matches("\\(258\\) ?[28]\\d{7,8}$",phoneNumber)){
            return "+258";
        }else if(Pattern.matches("\\(256\\) ?\\d{9}$",phoneNumber)){
            return "+256";
        }else{
            return null;
        }
    }

    /**
     * This function is used to get the Country based on the country code found in the phone number
     * @param countryCode The Country Code returned by extractCountryCode
     * @return The Country
     * @throws IllegalStateException in case of different country code other than expected
     */
    private String getCountryByCode(String countryCode) {
        switch (countryCode){
            case "+237":
                return "Cameroon";
            case "+251":
                return "Ethiopia";
            case "+212":
                return "Morocco";
            case "+258":
                return "Mozambique";
            case "+256":
                return "Uganda";
            default:
                throw new IllegalStateException("Unexpected value: " + countryCode);
        }
    }
}
