package com.votetest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;
import java.util.List;

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
import com.votetest.dao.PlaceDaoTest;
import com.votetest.model.MenuItem;
import com.votetest.model.Place;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration test for PlaceController
 * @author Nikolay Dechev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@WebAppConfiguration
public class PlaceControllerIntegrationTest {
    
    @Autowired
    private WebApplicationContext wac;
 
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void createPlace() throws Exception {
        Place place = PlaceDaoTest.createRandomPlace();
        mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void updatePlace() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Place>>() {});
        
        String newval = "new name";
        Place place = PlaceDaoTest.createRandomPlace();
        String content = mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ResponseObject<Place> resObject = r.readValue(content);
        Place resultPlace = resObject.getData();
        
        resultPlace.setName(newval);
        content = mockMvc.perform(put("/places").content(new ObjectMapper().writeValueAsString(resultPlace))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        resObject = r.readValue(content);
        resultPlace = resObject.getData();
        
        assertEquals("Place is with wrong data for name", newval, resultPlace.getName());
    }
    
    @Test
    public void listPlaces() throws Exception {
        Place place1 = PlaceDaoTest.createRandomPlace();
        Place place2 = PlaceDaoTest.createRandomPlace();
        mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place1))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place2))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<List<Place>>() {});
        String content = mockMvc.perform(get("/places")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Place> resultPlace = r.readValue(content);
        
        assertTrue("place size should be", resultPlace.size() > 0);
    }
    
    
    @Test
    public void addMenuItem() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Place>>() {});
        final ObjectReader r1 = new ObjectMapper().readerFor(new TypeReference<ResponseObject<MenuItem>>() {});
        
        Place place = PlaceDaoTest.createRandomPlace();
        String content = mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ResponseObject<Place> resObject = r.readValue(content);
        Place resultPlace = resObject.getData();
        MenuItem item = PlaceDaoTest.createRandomMenuItem();
        
        content = mockMvc.perform(post("/places/{id}/items", URLEncoder.encode(Long.toString(resultPlace.getId()), "UTF-8")).content(new ObjectMapper().writeValueAsString(item))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        ResponseObject<MenuItem> resObject1 = r1.readValue(content);
        MenuItem resultItem = resObject1.getData();
        
        Assert.assertNotNull(resultItem);
        Assert.assertNotNull(resultItem.getId());
        
        content = mockMvc.perform(get("/places/{id}", URLEncoder.encode(Long.toString(resultPlace.getId()), "UTF-8"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        resObject = r.readValue(content);
        resultPlace = resObject.getData();
        
        assertEquals(place.getMenuItems().size() + 1, resultPlace.getMenuItems().size());
    }
    
    @Test
    public void getPlaceById() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Place>>() {});

        Place place = PlaceDaoTest.createRandomPlace();
        String content = mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        ResponseObject<Place> resObject = r.readValue(content);
        Place resultPlace = resObject.getData();
        
        Assert.assertNotNull("Id must be not null", resultPlace.getId());
        
        content = mockMvc.perform(get("/places/{id}", URLEncoder.encode(Long.toString(resultPlace.getId()), "UTF-8"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        resObject = r.readValue(content);
        resultPlace = resObject.getData();
        
        Assert.assertEquals("Place is with wrong data for Code", resultPlace.getName(), place.getName());
    }
    
    @Test
    public void removeMenuItem() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Place>>() {});
        final ObjectReader r1 = new ObjectMapper().readerFor(new TypeReference<ResponseObject<MenuItem>>() {});
        
        Place place = PlaceDaoTest.createRandomPlace();
        String content = mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ResponseObject<Place> resObject = r.readValue(content);
        Place resultPlace = resObject.getData();
        MenuItem item = null;
        
        for (MenuItem i : resultPlace.getMenuItems()) {
            item = i;
            break;
        }
        
        content = mockMvc.perform(delete("/places/{id}/items/{itemId}", URLEncoder.encode(Long.toString(resultPlace.getId()), "UTF-8"), URLEncoder.encode(Long.toString(item.getId()), "UTF-8")).content(new ObjectMapper().writeValueAsString(item))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        ResponseObject<MenuItem> resObject1 = r1.readValue(content);
        MenuItem resultItem = resObject1.getData();
        
        Assert.assertNotNull(resultItem);
        
        content = mockMvc.perform(get("/places/{id}", URLEncoder.encode(Long.toString(resultPlace.getId()), "UTF-8"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        resObject = r.readValue(content);
        resultPlace = resObject.getData();
        
        assertEquals(place.getMenuItems().size() - 1, resultPlace.getMenuItems().size());
    }
}
