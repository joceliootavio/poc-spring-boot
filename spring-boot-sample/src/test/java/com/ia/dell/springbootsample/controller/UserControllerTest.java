package com.ia.dell.springbootsample.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ia.dell.springbootsample.model.User;
import com.ia.dell.springbootsample.mother.UserMother;
import com.ia.dell.springbootsample.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@WithMockUser
@EnableSpringDataWebSupport
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private AuthenticationProvider authenticationProvider;    
    
    @MockBean
    private ConnectionFactory connectionFactory;
    
    @Test
    public void shouldReturnOkWhenCallCreate() throws Exception {
    	when(userService.save(any(User.class))).thenReturn(new User());
    	
        mockMvc.perform(post("/api/v1/users")
        				 .contentType(MediaType.APPLICATION_JSON_UTF8)
       					 .content("{\"login\": \"jossa3\", \"name\": \"jocelio\"}"))
       			.andDo(print())
       			.andExpect(status().isCreated())
       			.andDo(document("users", requestFields(
							fieldWithPath("login").type(JsonFieldType.STRING).description("User login"),
							fieldWithPath("name").type(JsonFieldType.STRING).description("User name"))));
    }
    
    @Test
    public void shouldReturnOkWhenCallIndex() throws Exception {
		List<User> users = Arrays.asList(UserMother.create("user1"), UserMother.create("user2"));    	
		when(userService.findAll(any(PageRequest.class))).thenReturn(new PageImpl<User>(users));
    	
        mockMvc.perform(get("/api/v1/users"))
       			.andDo(print())
       			.andExpect(status().isOk())
       			.andExpect(jsonPath("$.numberOfElements").value(2))
       			.andExpect(jsonPath("$.content[0].name").value("user1"))       			
       			.andExpect(jsonPath("$.content[0].email").value("j@ia.com"))       			
       			.andExpect(jsonPath("$.content[1].name").value("user2"))       			
       			.andExpect(jsonPath("$.content[1].admin").value(false))       			
       			.andDo(document("users"));
    }

}