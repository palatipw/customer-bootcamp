package com.digitalacademy.customer.support;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {
    public static List<Customer> getCustomerList(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Palatip");
        customer.setLastName("Wongyeekul");
        customer.setPhoneNo("0123456789");
        customer.setEmail("palatip.wongyeekul@scb.co.th");
        customer.setAge(22);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("Palatip2");
        customer.setLastName("Wongyeekul2");
        customer.setPhoneNo("0987654321");
        customer.setEmail("palatip.wongyeekul2@scb.co.th");
        customer.setAge(44);
        customerList.add(customer);
        return customerList;
    }
    public static Customer getReqNewCustomer(){
        Customer customerReq = new Customer();
        customerReq.setFirstName("Palatip");
        customerReq.setLastName("Wongyeekul");
        customerReq.setPhoneNo("0123456789");
        customerReq.setEmail("palatip.wongyeekul@scb.co.th");
        customerReq.setAge(22);
        return customerReq;
    }
    public static Customer getResNewCustomer(){
        Customer customerRes = new Customer();
        customerRes.setId(1L);
        customerRes.setFirstName("Palatip");
        customerRes.setLastName("Wongyeekul");
        customerRes.setPhoneNo("0123456789");
        customerRes.setEmail("palatip.wongyeekul@scb.co.th");
        customerRes.setAge(22);
        return customerRes;
    }
    public static Customer getResCustomer(){
        Customer customerRes = new Customer();
        customerRes.setId(2L);
        customerRes.setFirstName("Palatip");
        customerRes.setLastName("Wongyeekul");
        customerRes.setPhoneNo("0123456789");
        customerRes.setEmail("palatip.wongyeekul@scb.co.th");
        customerRes.setAge(22);
        return customerRes;
    }
    public static Customer getOldCustomer(){
        Customer oldCustomer = new Customer();
        oldCustomer.setId(2L);
        oldCustomer.setFirstName("OldPalatip");
        oldCustomer.setLastName("OldWongyeekul");
        oldCustomer.setPhoneNo("0213456789");
        oldCustomer.setEmail("oldpalatip.wongyeekul@scb.co.th");
        oldCustomer.setAge(20);
        return oldCustomer;
    }
}
