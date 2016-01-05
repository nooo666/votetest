package com.votetest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.votetest.dao.UserDaoTest;
import com.votetest.model.User;

/**
 * Integration test for UserController
 * @author Nikolay Dechev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@WebAppConfiguration
public class UserControllerIntegrationTest {
    
    @Autowired
    private WebApplicationContext wac;
 
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void createUser() throws Exception {
        User user = UserDaoTest.createRandomUser();
        mockMvc.perform(post("/users").content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    @Test
    public void getUserById() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<User>>() {});

        User user = UserDaoTest.createRandomUser();
        String content = mockMvc.perform(post("/users").content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        ResponseObject<User> resObject = r.readValue(content);
        User resultUser = resObject.getData();
        
        Assert.assertNotNull("Id must be not null", resultUser.getId());
        
        content = mockMvc.perform(get("/users/{id}", URLEncoder.encode(Long.toString(resultUser.getId()), "UTF-8"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        resObject = r.readValue(content);
        resultUser = resObject.getData();
        
        
        Assert.assertEquals("User is with wrong data for Code", resultUser.getFullname(), user.getFullname());
    }

    @Test
    public void getUserByUsername() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<User>>() {});

        User user = UserDaoTest.createRandomUser();
        String content = mockMvc.perform(post("/users").content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        ResponseObject<User> resObject = r.readValue(content);
        User resultUser = resObject.getData();
        
        Assert.assertNotNull("Id must be not null", resultUser.getId());
        
        content = mockMvc.perform(get("/users/username/{name}", URLEncoder.encode(resultUser.getUsername(), "UTF-8"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        resObject = r.readValue(content);
        resultUser = resObject.getData();
        
        
        Assert.assertEquals("User is with wrong data for Code", resultUser.getFullname(), user.getFullname());
    }
}
