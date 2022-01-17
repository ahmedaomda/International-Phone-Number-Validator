package com.example.internationalphonenumbervalidator.controller;

import com.example.internationalphonenumbervalidator.model.Customer;
import com.example.internationalphonenumbervalidator.service.PhoneNumberService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("/getAll")
    public String getAllPhones(Model model,@RequestParam Optional<Integer> page,@RequestParam Optional<Integer> size){
        Page<Customer> customers = phoneNumberService.getAllPhoneNumbers(page,size);
        int totalPages = customers.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("customers",customers);
        return "phoneList.html";
    }

}
