package com.votetest.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.votetest.dao.IVoteDao;
import com.votetest.model.Place;
import com.votetest.model.User;
import com.votetest.model.Vote;
import com.votetest.model.VoteScore;
import com.votetest.utils.DateHelper;

@Transactional
public class VoteServiceImpl implements IVoteService {

    @Autowired
    private IVoteDao voteDao; 
    
    @Autowired
    private IUserService userService; 
    
    @Autowired
    private IPlaceService placeService; 
    
    @Override
    public Vote getVoteById(Integer voteId) throws ObjectNotFoundException {
        Vote vote = voteDao.getVoteById(voteId);
        
        if (vote == null) {
            throw new ObjectNotFoundException(String.format("Vote with id %s not found into the system", voteId));
        }
        
        return vote;
    }

    @Override
    public List<Vote> getVotesByUserId(Integer userId) {
        return voteDao.getVotesByUserId(userId);
    }

    @Override
    public List<Vote> getVotes(Date voteDate) {
        Date startDate = DateHelper.getStartDate(voteDate);
        Date endDate = DateHelper.getEndDate(startDate);
        
        return voteDao.getVotes(startDate, endDate);
    }

    @Override
    public List<VoteScore> getVotesScore(Date voteDate) {
        Date startDate = DateHelper.getStartDate(voteDate);
        Date endDate = DateHelper.getEndDate(startDate);

        return voteDao.getVotesScore(startDate, endDate);
    }

    @Override
    public Vote createVote(Vote vote) throws ObjectAlreadyExistsException {
        
        if (vote.getId() != null && voteDao.objectExists(vote.getId())) {
            throw new ObjectAlreadyExistsException(String.format("Vote with id %s has already been registed into the system", vote.getId()));
        }
        
        if (vote.getId() != null) {
            vote.setId(null);
        }
        
        Integer voteId = voteDao.createVote(vote);
        Vote loadedVote = voteDao.getVoteById(voteId);

        return loadedVote;
    }

    @Override
    public void updateVote(Vote vote) throws ObjectNotFoundException {

        if (!voteDao.objectExists(vote.getId())) {
            throw new ObjectNotFoundException(String.format("Vote with id %s not found into the system", vote.getId()));
        }
        
        voteDao.updateVote(vote);
    }

    @Override
    public Vote placeUserVote(Integer userId, Integer placeId) throws ObjectNotFoundException {
        User user = userService.getUserById(userId);
        Place place = placeService.getPlaceById(placeId);
        Date voteDate = new Date();
        Date startDate = DateHelper.getStartDate(voteDate);
        Date endDate = DateHelper.getEndDate(startDate);
        
        List<Vote> existingVotes = voteDao.getVotesByUserIdAndDate(userId, startDate, endDate);
        Vote vote = new Vote();
        vote.setVoteUser(user);
        
        if (existingVotes != null) {
            
            if (existingVotes.size() > 1) {
                System.err.println("Database is inconsistent. Only first vote will be used");
            }
            
            for (Vote v : existingVotes) {
                vote = v;
                break;
            }
        }
        
        vote.setVoteDate(voteDate);
        vote.setVotePlace(place);

        if (vote.getId() == null) {
            Integer voteId = voteDao.createVote(vote);
            vote = voteDao.getVoteById(voteId);
        } else {
            updateVote(vote);
        }
        
        return vote;
    }

}
