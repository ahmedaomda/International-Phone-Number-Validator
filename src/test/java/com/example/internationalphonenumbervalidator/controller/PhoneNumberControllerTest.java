package com.example.internationalphonenumbervalidator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.internationalphonenumbervalidator.model.Customer;
import com.example.internationalphonenumbervalidator.service.PhoneNumberService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PhoneNumberController.class)
class PhoneNumberControllerTest {

    @MockBean
    PhoneNumberService phoneNumberService;

    @Autowired
    MockMvc mockMvc;

    Page<Customer> getCustomers(){
        Customer customer = new Customer();
        customer.setName("Nada Sofie");
        customer.setPhone("(212) 654642448");
        return new PageImpl<>(List.of(customer));
    }

    @Test
    void test_getAll_basicScenario() throws Exception {
        when(phoneNumberService.getAllPhoneNumbers(any(),any())).thenReturn(getCustomers());
        mockMvc.perform(get("/getAll").queryParam("page", "1").queryParam("size","10"))
            .andExpect(status().isOk());
    }

    @Test
    void test_getAll_noPageOrSizeParams() throws Exception {
        when(phoneNumberService.getAllPhoneNumbers(any(),any())).thenReturn(getCustomers());
        mockMvc.perform(get("/getAll"))
            .andExpect(status().isOk());
    }
}
