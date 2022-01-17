package com.example.internationalphonenumbervalidator.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.internationalphonenumbervalidator.model.Customer;
import com.example.internationalphonenumbervalidator.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceTest {

    @InjectMocks
    PhoneNumberService phoneNumberService;

    @Mock
    CustomerRepository customerRepository;


    Page<Customer> getCustomers(){
        Customer customer = new Customer();
        customer.setName("Nada Sofie");
        customer.setPhone("(212) 654642448");
        return new PageImpl<>(List.of(customer));
    }
    @Test
    void test_getAllPhoneNumbers_basicScenario(){
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(getCustomers());
        Page<Customer> customers = phoneNumberService.getAllPhoneNumbers(Optional.of(1), Optional.of(10));
        Assertions.assertEquals("Valid",customers.getContent().get(0).getState());
    }

    @Test
    void test_extractCountryCode_basicScenario(){
        Object returnedObject = ReflectionTestUtils.invokeMethod(phoneNumberService, "extractCountryCode", "(212) 698054317");
        Assertions.assertEquals("+212",String.valueOf(returnedObject));
    }

    @Test
    void test_extractCountryCode_invalidNumber(){
        Object returnedObject = ReflectionTestUtils.invokeMethod(phoneNumberService, "extractCountryCode", "(212) 6007989253");
        Assertions.assertNull(returnedObject);
    }

    @Test
    void test_getCountryByCode_basicScenario(){
        Object returnedObject = ReflectionTestUtils.invokeMethod(phoneNumberService, "getCountryByCode", "+237");
        Assertions.assertEquals("Cameroon",String.valueOf(returnedObject));
    }

    @Test
    void test_getCountryByCode_invalidCode(){
        try {
            ReflectionTestUtils.invokeMethod(phoneNumberService, "getCountryByCode", "+555");
        }catch (Exception e){
            Assertions.assertInstanceOf(IllegalStateException.class,e);
        }
    }

}
