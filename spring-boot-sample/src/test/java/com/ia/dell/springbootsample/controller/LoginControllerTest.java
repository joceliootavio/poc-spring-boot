package com.ia.dell.springbootsample.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ia.dell.springbootsample.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private AuthenticationProvider authenticationProvider;
    
    @MockBean
    private ConnectionFactory connectionFactory;
    
    @Test
    @WithMockUser
    public void shouldReturnOkWhenCallLogin() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
        				 .contentType(MediaType.APPLICATION_JSON_UTF8)
       					 .content("{\"username\": \"jossa2\", \"password\": 123}"))
       			.andDo(print())
       			.andExpect(status().isOk())
       			.andDo(document("login"));
    }
    
    @Test
    @WithMockUser
    public void shouldReturnOkWhenCallLogout() throws Exception {
        mockMvc.perform(get("/api/v1/auth/logout"))
       			.andDo(print())
       			.andExpect(status().isOk())
       			.andDo(document("logout"));
    }
    
    @Test
    @WithMockUser(roles="ADMIN")
    public void shouldReturnOkWhenCallChangePassword() throws Exception {
        mockMvc.perform(post("/api/v1/auth/change-password")
				 .contentType(MediaType.APPLICATION_JSON_UTF8)
				 .content("{\"username\": \"jossa2\", \"password\": 1234}"))
       			.andDo(print())
       			.andExpect(status().isOk())
       			.andDo(document("change-password", requestFields(
       													fieldWithPath("username").type(JsonFieldType.STRING).description("User login"),
       													fieldWithPath("password").type(JsonFieldType.STRING).description("User Password"))));
    }      
}