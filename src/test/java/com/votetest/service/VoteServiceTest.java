package com.votetest.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.votetest.dao.IPlaceDao;
import com.votetest.dao.IUserDao;
import com.votetest.dao.PlaceDaoTest;
import com.votetest.dao.UserDaoTest;
import com.votetest.model.Place;
import com.votetest.model.User;
import com.votetest.model.Vote;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@Transactional
public class VoteServiceTest {
    
    @Resource(name = "voteService")
    private IVoteService voteService;

    @Resource(name = "placeDao")
    private IPlaceDao placeDao;
    
    @Resource(name = "userDao")
    private IUserDao userDao;
    
    private Vote createRandomVote() {
        Vote vote = new Vote();
        vote.setVoteDate(new Date());
        User user = UserDaoTest.createRandomUser();
        userDao.createUser(user);
        vote.setVoteUser(user);
        Place place = PlaceDaoTest.createRandomPlace();
        placeDao.createPlace(place);
        vote.setVotePlace(place);
        
        return vote;
    }
    
    @Test
    public void createVote() throws ObjectAlreadyExistsException {
        Vote vote = createRandomVote();
        vote = voteService.createVote(vote);
        
        Assert.assertNotNull("Vote should not be null", vote);
        Assert.assertNotNull("Vote id should not be null", vote.getId());
        
        try {
            voteService.createVote(vote);
            Assert.fail("Exception should be thrown");
        } catch (ObjectAlreadyExistsException e) {
        }
    }

    @Test
    public void updateVote() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        Vote vote = createRandomVote();
        Vote vote1 = createRandomVote();
        vote = voteService.createVote(vote);
        vote.setVotePlace(vote1.getVotePlace());
        voteService.updateVote(vote);
        vote = voteService.getVoteById(vote.getId());
        
        Assert.assertEquals(vote1.getVotePlace().getId(), vote.getVotePlace().getId());
        
        
        try {
            Vote newVote = createRandomVote();
            newVote.setId(vote.getId() + 1);
            voteService.updateVote(newVote);
            Assert.fail("Exception should be thrown");
        } catch (ObjectNotFoundException e) {
        }
    }

    @Test
    public void getPlaceById() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        Vote vote = createRandomVote();
        vote = voteService.createVote(vote);
        vote = voteService.getVoteById(vote.getId());
        
        Assert.assertNotNull("Vote should not be null", vote);
        
        try {
            voteService.getVoteById(vote.getId() + 1);
            Assert.fail("Exception should be thrown");
        } catch (ObjectNotFoundException e) {
        }
    }
    
    @Test
    public void placeUserVote() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        Vote vote = createRandomVote();
        vote = voteService.placeUserVote(vote.getVoteUser().getId(), vote.getVotePlace().getId());
        vote = voteService.getVoteById(vote.getId());
        
        Assert.assertNotNull("Vote should not be null", vote);
    }

}
