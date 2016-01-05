package com.votetest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.votetest.dao.PlaceDaoTest;
import com.votetest.dao.UserDaoTest;
import com.votetest.model.Place;
import com.votetest.model.User;
import com.votetest.model.Vote;
import com.votetest.model.VoteScore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration test for VoteController
 * @author Nikolay Dechev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class VoteControllerIntegrationTest {
    
    @Autowired
    private WebApplicationContext wac;
 
    private MockMvc mockMvc;
    
    private Vote createRandomVote() throws UnsupportedEncodingException, JsonProcessingException, Exception {
        Vote vote = new Vote();
        vote.setVoteDate(new Date());
        User user = UserDaoTest.createRandomUser();
        
        ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<User>>() {});

        String content = mockMvc.perform(post("/users").content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        ResponseObject<User> resObject = r.readValue(content);
        user = resObject.getData();

        vote.setVoteUser(user);
        Place place = PlaceDaoTest.createRandomPlace();
        
        r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Place>>() {});
        content = mockMvc.perform(post("/places").content(new ObjectMapper().writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseObject<Place> resObject1 = r.readValue(content);
        place = resObject1.getData();

        vote.setVotePlace(place);
        
        return vote;
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void createVote() throws Exception {
        Vote vote = createRandomVote();
        mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void updateVote() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Vote>>() {});
        
        Vote vote = createRandomVote();
        String content = mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ResponseObject<Vote> resObject = r.readValue(content);
        Vote resultVote = resObject.getData();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = sdf.parse("21/12/2015 12:00:00");
        
        resultVote.setVoteDate(d);
        content = mockMvc.perform(put("/votes").content(new ObjectMapper().writeValueAsString(resultVote))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        resObject = r.readValue(content);
        resultVote = resObject.getData();
        
        assertEquals("Vote is with wrong data for name", d, resultVote.getVoteDate());
    }
    
    @Test
    public void getVoteById() throws Exception {
        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Vote>>() {});

        Vote vote = createRandomVote();
        String content = mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        ResponseObject<Vote> resObject = r.readValue(content);
        Vote resultVote = resObject.getData();
        
        Assert.assertNotNull("Id must be not null", resultVote.getId());
        
        content = mockMvc.perform(get("/votes/{id}", URLEncoder.encode(Long.toString(resultVote.getId()), "UTF-8"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        resObject = r.readValue(content);
        resultVote = resObject.getData();
        
        Assert.assertEquals("Vote is with wrong data for Code", resultVote.getVoteDate(), vote.getVoteDate());
    }
    
    @Test
    public void listVotes() throws Exception {
        Vote vote1 = createRandomVote();
        Vote vote2 = createRandomVote();
        mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote1))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote2))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<List<Vote>>() {});
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        String dateStr = dateformat.format(new Date());
        String content = mockMvc.perform(get("/votes").param("date", dateStr)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Vote> resultVotes = r.readValue(content);
        
        assertTrue(resultVotes.size() > 0);
    }
    
    @Test
    public void listVoteScores() throws Exception {
        Vote vote1 = createRandomVote();
        Vote vote2 = createRandomVote();
        Vote vote3 = createRandomVote();
        
        vote3.setVotePlace(vote1.getVotePlace());
        
        mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote1))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote2))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        mockMvc.perform(post("/votes").content(new ObjectMapper().writeValueAsString(vote3))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<List<VoteScore>>() {});
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        String dateStr = dateformat.format(new Date());
        String content = mockMvc.perform(get("/votescores").param("date", dateStr)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<VoteScore> resultVotes = r.readValue(content);
        
        assertTrue(resultVotes.size() == 2);
    }

    @Test
    public void placeUserVote() throws Exception {
        Vote vote1 = createRandomVote();
        Vote vote2 = createRandomVote();
        Vote vote3 = createRandomVote();
        vote3.setVotePlace(vote1.getVotePlace());
        
        String content1 = mockMvc.perform(post("/votes/places/{placeId}/users/{userId}", URLEncoder.encode(Long.toString(vote1.getVotePlace().getId()), "UTF-8"), URLEncoder.encode(Long.toString(vote1.getVoteUser().getId()), "UTF-8")).content(new ObjectMapper().writeValueAsString(vote1))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        final ObjectReader r1 = new ObjectMapper().readerFor(new TypeReference<ResponseObject<Vote>>() {});
        ResponseObject<Vote> resObject1 = r1.readValue(content1);
        Vote resultVote1 = resObject1.getData();
        
        Assert.assertNotNull(resultVote1);
        Assert.assertNotNull(resultVote1.getId());
        
        mockMvc.perform(post("/votes/places/{placeId}/users/{userId}", URLEncoder.encode(Long.toString(vote2.getVotePlace().getId()), "UTF-8"), URLEncoder.encode(Long.toString(vote2.getVoteUser().getId()), "UTF-8")).content(new ObjectMapper().writeValueAsString(vote1))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/votes/places/{placeId}/users/{userId}", URLEncoder.encode(Long.toString(vote3.getVotePlace().getId()), "UTF-8"), URLEncoder.encode(Long.toString(vote3.getVoteUser().getId()), "UTF-8")).content(new ObjectMapper().writeValueAsString(vote1))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        final ObjectReader r = new ObjectMapper().readerFor(new TypeReference<List<VoteScore>>() {});
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        String dateStr = dateformat.format(new Date());
        String content = mockMvc.perform(get("/votescores").param("date", dateStr)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<VoteScore> resultVotes = r.readValue(content);
        
        assertTrue(resultVotes.size() == 2);
    }
}
