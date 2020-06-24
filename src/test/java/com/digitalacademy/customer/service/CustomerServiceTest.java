package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list")
    @Test
    void testGetAllCustomer(){

        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getCustomerList());

        List<Customer> resq = customerService.getCustomerList();

        assertEquals(1,resq.get(0).getId().intValue());
        assertEquals("Palatip",resq.get(0).getFirstName());
        assertEquals("Wongyeekul",resq.get(0).getLastName());
        assertEquals("0123456789",resq.get(0).getPhoneNo());
        assertEquals("palatip.wongyeekul@scb.co.th",resq.get(0).getEmail());
        assertEquals(22,resq.get(0).getAge().intValue());

        assertEquals(2,resq.get(1).getId().intValue());
        assertEquals("Palatip2",resq.get(1).getFirstName());
        assertEquals("Wongyeekul2",resq.get(1).getLastName());
        assertEquals("0987654321",resq.get(1).getPhoneNo());
        assertEquals("palatip.wongyeekul2@scb.co.th",resq.get(1).getEmail());
        assertEquals(44,resq.get(1).getAge().intValue());

    }

    @DisplayName("Test get customer by id")
    @Test
    void testGetCustomerById(){
        Long reqParam = 1L;

        when(customerRepository.findAllById(reqParam)).thenReturn(CustomerSupportTest.getCustomerList().get(0));
        Customer resq = customerService.getCustomerById(reqParam);
        assertEquals(1,resq.getId().intValue());
        assertEquals("Palatip",resq.getFirstName());
        assertEquals("Wongyeekul",resq.getLastName());
        assertEquals("0123456789",resq.getPhoneNo());
        assertEquals("palatip.wongyeekul@scb.co.th",resq.getEmail());
        assertEquals(22,resq.getAge().intValue());
    }

    @DisplayName("Test get customer by name")
    @Test
    void testGetCustomerByName(){
        String name = "Palatip";
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setFirstName("Palatip");
        customerList.add(customer);
        customer = new Customer();
        customer.setFirstName("Palatip");
        customerList.add(customer);

        when(customerRepository.findByFirstName(name)).thenReturn(customerList);
        List<Customer> resq = customerService.getCustomerByFirstName(name);
        assertEquals("Palatip",resq.get(0).getFirstName());
        assertEquals("Palatip",resq.get(1).getFirstName());

    }

    @DisplayName("Test create customer should return new customer")
    @Test
    void testCreateCustomer(){

        when(customerRepository.save(CustomerSupportTest.getReqNewCustomer())).thenReturn(CustomerSupportTest.getResNewCustomer());
        Customer resq = customerService.createCustomer(CustomerSupportTest.getReqNewCustomer());
        assertEquals(1,resq.getId().intValue());
        assertEquals("Palatip",resq.getFirstName());
        assertEquals("Wongyeekul",resq.getLastName());
        assertEquals("0123456789",resq.getPhoneNo());
        assertEquals("palatip.wongyeekul@scb.co.th",resq.getEmail());
        assertEquals(22,resq.getAge().intValue());
    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer(){
        Long reqId = 2L;

        when(customerRepository.findAllById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());
        when(customerRepository.save(CustomerSupportTest.getResCustomer())).thenReturn(CustomerSupportTest.getResCustomer());

        Customer resq = customerService.updateCustomer(reqId,CustomerSupportTest.getResCustomer());
        assertEquals(2,resq.getId().intValue());
        assertEquals("Palatip",resq.getFirstName());
        assertEquals("Wongyeekul",resq.getLastName());
        assertEquals("0123456789",resq.getPhoneNo());
        assertEquals("palatip.wongyeekul@scb.co.th",resq.getEmail());
        assertEquals(22,resq.getAge().intValue());

    }

    @DisplayName("Test delete customer should return true")
    @Test
    void testDeleteCustomer(){
        Long reqId = 1L;
        doNothing().when(customerRepository).deleteById(reqId);
        boolean resq = customerService.deleteById(reqId);
        assertEquals(true, resq);
        //option1
        assertTrue(resq);
        //option2
        assertTrue(customerService.deleteById(reqId));
    }

    @DisplayName("Test delete customer should return false")
    @Test
    void testDeleteCustomerFail(){
        Long reqId = 1L;
        doThrow(EmptyResultDataAccessException.class)
                .when(customerRepository).deleteById(reqId);
        boolean resq = customerService.deleteById(reqId);
        assertEquals(false,resq);
        assertFalse(resq);
        assertFalse(customerService.deleteById(reqId));
    }
}
