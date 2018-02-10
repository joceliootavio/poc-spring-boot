package com.ia.dell.springbootsample.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ia.dell.springbootsample.mother.UserMother;
import com.ia.dell.springbootsample.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@WithMockUser
@EnableSpringDataWebSupport
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private RabbitTemplate rabbitTemplate;
    
    @MockBean
    private AuthenticationProvider authenticationProvider;    
    
    @MockBean
    private ConnectionFactory connectionFactory;
    
    @Test
    public void shouldReturnOkWhenCallEmailPostWithoutRecipients() throws Exception {
    	when(userService.findAdmins()).thenReturn(Arrays.asList(UserMother.create("jossa")));
    	
        mockMvc.perform(post("/api/v1/emails")
        				 .contentType(MediaType.APPLICATION_JSON_UTF8)
       					 .content("{\"subject\": \"teste email\", \"body\": \"testando testando\"}"))
       			.andDo(print())
       			.andExpect(status().isOk())
       			.andDo(document("emails"));
    }
    
    @Test
    public void shouldReturnOkWhenCallEmailPostWithRecipients() throws Exception {
    	when(userService.findAdmins()).thenReturn(Arrays.asList(UserMother.create("jossa")));
    	
        mockMvc.perform(post("/api/v1/emails")
        				 .contentType(MediaType.APPLICATION_JSON_UTF8)
       					 .content("{\"subject\": \"teste email\", \"body\": \"testando testando\", \"recipients\":[\"jocelio.otavio@gmail.com\"]}"))
       			.andDo(print())
       			.andExpect(status().isOk())
       			.andDo(document("emails", requestFields(
							fieldWithPath("subject").type(JsonFieldType.STRING).description("Email Subject"),
							fieldWithPath("body").type(JsonFieldType.STRING).description("Email body"),
							fieldWithPath("recipients").type(JsonFieldType.ARRAY).description("Recipients"))));
    }

}