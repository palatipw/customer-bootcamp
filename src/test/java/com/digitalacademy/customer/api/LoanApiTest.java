package com.digitalacademy.customer.api;

import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanApiTest {
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    LoanApi loanApi;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        loanApi = new LoanApi(restTemplate);
    }
    @DisplayName("Test get loan info should return loan information")
    @Test
    void getLoanInfo() throws Exception{
        Long reqId = 1L;
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseSuccess());
        ;
        GetLoanInfoResponse res = loanApi.getLoanInfo(reqId);
        assertEquals("1",res.getId().toString());
        assertEquals("OK",res.getStatus());
        assertEquals("101-222-2200",res.getAccountPayable());
        assertEquals("101-333-2020",res.getAccountReceivable());
        assertEquals(3400000.0,res.getPrincipalAmount());
        verify(restTemplate,times(1)).exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any());
    }
    @DisplayName("Test get loan info by id 2 should Loan information not found")
    @Test
    void getLoanInfoLOAN4002() throws Exception{
        Long reqParam = 2L;
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseEntityLOAN4002());
        ;
        GetLoanInfoResponse res = loanApi.getLoanInfo(reqParam);
        assertEquals(null , res.getId());
        assertEquals(null , res.getStatus());
        assertEquals(null , res.getAccountPayable());
        assertEquals(null , res.getAccountReceivable());
        assertEquals(0 , res.getPrincipalAmount());
    }
    @DisplayName("Test get loan info by id 1 should Loan information not found")
    @Test
    void getLoanInfoLOAN4001() throws Exception{
        Long reqParam = 3L;
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseEntityLOAN4002());
        ;
        GetLoanInfoResponse res = loanApi.getLoanInfo(reqParam);
        assertEquals(null , res.getId());
        assertEquals(null , res.getStatus());
        assertEquals(null , res.getAccountPayable());
        assertEquals(null , res.getAccountReceivable());
        assertEquals(0 , res.getPrincipalAmount());
    }
    public static ResponseEntity<String> prepareResponseSuccess() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"0\",\n" +
                "        \"message\": \"success\"\n" +
                "    },\n" +
                "    \"data\": {\n" +
                "        \"id\": 1,\n" +
                "        \"status\": \"Ok\",\n" +
                "        \"account_payable\": \"101-220-2200\",\n" +
                "        \"account_receivable\": \"101-220-2200\",\n" +
                "        \"principle_amount\": 400000.0\n" +
                "    }\n" +
                "}");
    }
    public static ResponseEntity<String> prepareResponseEntityLOAN4002() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN4002\",\n" +
                "        \"message\": \"Loan information not found.\"\n" +
                "    }\n" +
                "}");
    }
    public static ResponseEntity<String> prepareResponseEntityLOAN4001() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN4001\",\n" +
                "        \"message\": \"Cannot get loan information\"\n" +
                "    },\n" +
                "    \"data\": \"Cannot get loan information\"\n" +
                "}");
    }
}
