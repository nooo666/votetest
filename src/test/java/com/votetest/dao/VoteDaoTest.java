package com.votetest.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.votetest.model.Place;
import com.votetest.model.User;
import com.votetest.model.Vote;
import com.votetest.model.VoteScore;
import com.votetest.utils.DateHelper;

/**
 * Test case for UserDaoImpl.
 * @author Nikolay Dechev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@Transactional
public class VoteDaoTest {
    
    @Resource(name = "voteDao")
    private IVoteDao voteDao;
    @Resource(name = "placeDao")
    private IPlaceDao placeDao;
    @Resource(name = "userDao")
    private IUserDao userDao;
    
    /** 
     * @return
     */
    public Vote createRandomVote() {
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
    
    /**
     * 
     */
    @Test 
    public void testCreateVote() {
        Vote vote = createRandomVote();
        Integer id = voteDao.createVote(vote);
        
        Assert.assertNotNull("Vote id must be not null", id);
    }
    
    @Test 
    public void testUpdateVote() {
        Vote vote = createRandomVote();
        Vote vote1 = createRandomVote();
        Integer id = voteDao.createVote(vote);
        Vote loadedVote = voteDao.getVoteById(id);
        loadedVote.setVotePlace(vote1.getVotePlace());
        voteDao.updateVote(loadedVote);
        loadedVote = voteDao.getVoteById(id);
        
        Assert.assertNotNull("Vote should not be null", loadedVote);
        Assert.assertEquals(vote.getVoteDate(), loadedVote.getVoteDate());
        Assert.assertNotNull("Vote user should not be null", loadedVote.getVoteUser());
        Assert.assertNotNull("Vote place should not be null", vote1.getVotePlace());
    }

    @Test 
    public void testFindVoteById() {
        Vote vote = createRandomVote();
        Integer id = voteDao.createVote(vote);
        Vote loadedVote = voteDao.getVoteById(id);
        
        Assert.assertNotNull("Vote should not be null", loadedVote);
        Assert.assertEquals(vote.getVoteDate(), loadedVote.getVoteDate());
        Assert.assertNotNull("Vote user should not be null", loadedVote.getVoteUser());
        Assert.assertNotNull("Vote place should not be null", loadedVote.getVotePlace());
    }

    @Test 
    public void testFindVoteByUserId() {
        Vote vote = createRandomVote();
        Vote vote1 = createRandomVote();
        Vote vote2 = createRandomVote();
        vote1.setVoteUser(vote.getVoteUser());
        voteDao.createVote(vote);
        voteDao.createVote(vote1);
        voteDao.createVote(vote2);
        List<Vote> loadedVote = voteDao.getVotesByUserId(vote.getVoteUser().getId());
        
        Assert.assertNotNull("Vote should not be null", loadedVote);
        Assert.assertEquals(2, loadedVote.size());
    }

    
    @Test 
    public void testFindVoteByUserIdAndDate() throws ParseException {
        Vote vote = createRandomVote();
        Vote vote1 = createRandomVote();
        Vote vote2 = createRandomVote();
        vote1.setVoteUser(vote.getVoteUser());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse("21/12/2012");
        vote1.setVoteDate(d);
        voteDao.createVote(vote);
        voteDao.createVote(vote1);
        voteDao.createVote(vote2);
        List<Vote> loadedVote = voteDao.getVotesByUserIdAndDate(vote.getVoteUser().getId(), sdf.parse("20/12/2012"), sdf.parse("22/12/2012"));
                
        Assert.assertNotNull("Vote should not be null", loadedVote);
        Assert.assertEquals(1, loadedVote.size());

        loadedVote = voteDao.getVotesByUserIdAndDate(vote.getVoteUser().getId(), sdf.parse("25/12/2012"), null);
        
        Assert.assertNotNull("Vote should not be null", loadedVote);
        Assert.assertEquals(1, loadedVote.size());
    }

    @Test 
    public void testFindAllVotes() {
        Vote vote = createRandomVote();
        Vote vote1 = createRandomVote();
        Vote vote2 = createRandomVote();
        vote1.setVoteUser(vote.getVoteUser());
        voteDao.createVote(vote);
        voteDao.createVote(vote1);
        voteDao.createVote(vote2);
        Date voteDate = new Date();
        Date startDate = DateHelper.getStartDate(voteDate);
        Date endDate = DateHelper.getEndDate(startDate);
        List<Vote> loadedVote = voteDao.getVotes(startDate, endDate);
        
        Assert.assertNotNull("Vote should not be null", loadedVote);
        Assert.assertEquals(3, loadedVote.size());
    }

    @Test 
    public void testFindVotesScore() {
        Vote vote = createRandomVote();
        Vote vote1 = createRandomVote();
        Vote vote2 = createRandomVote();
        vote1.setVotePlace(vote.getVotePlace());
        voteDao.createVote(vote);
        voteDao.createVote(vote1);
        voteDao.createVote(vote2);
        Date voteDate = new Date();
        Date startDate = DateHelper.getStartDate(voteDate);
        Date endDate = DateHelper.getEndDate(startDate);
        List<VoteScore> loadedVote = voteDao.getVotesScore(startDate, endDate);
        
        Assert.assertNotNull("Vote should not be null", loadedVote);
        Assert.assertEquals(2, loadedVote.size());
    }
}

