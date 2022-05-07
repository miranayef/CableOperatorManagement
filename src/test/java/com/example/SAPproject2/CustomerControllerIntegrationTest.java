package com.example.SAPproject2;

import com.example.SAPproject2.Controllers.CustomerController;
import com.example.SAPproject2.Repositories.CustomerRepository;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestToCustomersAndValidUser_thenCorrectResponse() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
        String customer = "{\"firstName\":\"Mira\",\"lastName\":\"Nayef\", \"gender\":\"Female\", \"email\":\"mn@gmail.com\", \"phone\":\"0877427882\", \"address\": \"Sofia,Bulgaria\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void whenPostRequestToUsersAndInValidUser_thenCorrectResponse() throws Exception {
        String customer = "{\"firstName\":\"\",\"lastName\":\"Nayef\", \"gender\":\"Female\", \"email\":\"mn@gmail.com\", \"phone\":\"0877427882\", \"address\": \"Sofia,Bulgaria\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Name is mandatory")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
