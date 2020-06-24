package com.digitalacademy.customer.controller;


import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;

    public static final String urlCustomer = "/customer/";
    public static final String urlCustomerList = "/customer/list/";

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .build();
    }

    @DisplayName("Test get Consumer should return customer list")
    @Test
    void testGetCustomerList() throws Exception{
        when(customerService.getCustomerList()).thenReturn(
                CustomerSupportTest.getCustomerList()
        );

        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Palatip",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("Wongyeekul",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("0123456789",jsonArray.getJSONObject(0).get("phoneNo"));
        assertEquals("palatip.wongyeekul@scb.co.th",jsonArray.getJSONObject(0).get("email"));
        assertEquals(22,jsonArray.getJSONObject(0).get("age"));


        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("Palatip2",jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("Wongyeekul2",jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("0987654321",jsonArray.getJSONObject(1).get("phoneNo"));
        assertEquals("palatip.wongyeekul2@scb.co.th",jsonArray.getJSONObject(1).get("email"));
        assertEquals(44,jsonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerId() throws Exception {
        Long reqId = 2L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());
        MvcResult mvcResult = mvc.perform(get(urlCustomer+""+reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("OldPalatip",jsonObject.get("firstName"));
        assertEquals("OldWongyeekul",jsonObject.get("lastName"));
        assertEquals("0213456789",jsonObject.get("phoneNo"));
        assertEquals("oldpalatip.wongyeekul@scb.co.th",jsonObject.get("email"));
        assertEquals(20,jsonObject.get("age"));
    }

    @DisplayName("Test get customer by id should return not found")
    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        Long reqId = 5L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer+""+reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

//    @DisplayName("Test get customer by name should return customer")
//    @Test
//    void testGetCustomerName() throws Exception {
//        String name = "Palatip";
//        when(customerService.getCustomerByFirstName(name)).thenReturn(CustomerSupportTest.getResCustomer());
//        MvcResult mvcResult = mvc.perform(get(urlCustomer))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andReturn();
//
//        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        assertEquals("2",jsonObject.get("id").toString());
//        assertEquals("OldPalatip",jsonObject.get("firstName"));
//        assertEquals("OldWongyeekul",jsonObject.get("lastName"));
//        assertEquals("0213456789",jsonObject.get("phoneNo"));
//        assertEquals("oldpalatip.wongyeekul@scb.co.th",jsonObject.get("email"));
//        assertEquals(20,jsonObject.get("age"));
//    }
//    @DisplayName("Test get customer by name is empty")
//    @Test
//    void testGetCustomerNameIsNotFound() throws Exception {
//        String name = "";
//        when(customerService.getCustomerByFirstName(name)).thenReturn(null);
//        MvcResult mvcResult = mvc.perform(get(urlCustomer+"/name/"+""+name))
//                .andExpect(status().isNotFound())
//                .andReturn();
//    }

    @DisplayName("Test create customer should return success")
    @Test
    void testCreateCustomer() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();

        // object to JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getResNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("Palatip",jsonObject.get("firstName"));
        assertEquals("Wongyeekul",jsonObject.get("lastName"));
        assertEquals("0123456789",jsonObject.get("phoneNo"));
        assertEquals("palatip.wongyeekul@scb.co.th",jsonObject.get("email"));
        assertEquals(22,jsonObject.get("age"));
    }

    @DisplayName("test create customer with name is empty")
    @Test
    void testCreateCustomerWithIsEmpty() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();
        reqCustomer.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getResNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitalacademy.customer.controller.CustomerController.createCustomer(com.digitalacademy.customer.model.Customer): [Field error in object 'customer' on field 'firstName': rejected value []; codes [Size.customer.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstName,firstName]; arguments []; default message [firstName],100,1]; default message [Please type your first name size between 1-100 words]] "
                ,mvcResult.getResolvedException().getMessage());
    }

    @DisplayName("test update customer should return success")
    @Test
    void testUpdateCustomer() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer))
                .thenReturn(CustomerSupportTest.getResCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("Palatip",jsonObject.get("firstName"));
        assertEquals("Wongyeekul",jsonObject.get("lastName"));
        assertEquals("0123456789",jsonObject.get("phoneNo"));
        assertEquals("palatip.wongyeekul@scb.co.th",jsonObject.get("email"));
        assertEquals(22,jsonObject.get("age"));
    }

    @DisplayName("test update customer should return id not found")
    @Test
    void testUpdateCustomerIdNotFound() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer))
                .thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @DisplayName("test delete customer should success")
    @Test
    void testDeleteCustomer() throws Exception{
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        verify(customerService,times(1)).deleteById(reqId);
    }
    @DisplayName("test delete customer should success")
    @Test
    void testDeleteCustomerShouldNotFound() throws Exception{
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
                .andReturn();
        verify(customerService,times(1)).deleteById(reqId);
    }

}
