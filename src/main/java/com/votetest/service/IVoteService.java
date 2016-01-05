package com.votetest.service;

import java.util.Date;
import java.util.List;

import com.votetest.model.Vote;
import com.votetest.model.VoteScore;

/**
 * Interface defines business methods for vote management
 * @author Nikolay Dechev
 *
 */
public interface IVoteService {
    
    /**
     * Retrieves vote by given identifier 
     * @param voteId - vote identifier 
     * @return - vote data
     * @throws ObjectNotFoundException - thrown when vote has not been found 
     */
    public Vote getVoteById(Integer voteId) throws ObjectNotFoundException;

    /**
     * Retrieves all votes for specified user 
     * @param userId - user identifier 
     * @return - list of user votes
     */
    public List<Vote> getVotesByUserId(Integer userId);

    /**
     * Retrieves all votes registed into the system 
     * @param date TODO
     * @return
     */
    public List<Vote> getVotes(Date date);

    /**
     * Retrieves votes scores per place 
     * @param date TODO
     * @return
     */
    public List<VoteScore> getVotesScore(Date date);

    /**
     * Registers new vote into the system 
     * @param vote - vote data
     * @return - registed voted with updated data
     * @throws ObjectAlreadyExistsException - thrown if vote already has been registered into the system
     */
    public Vote createVote(Vote vote) throws ObjectAlreadyExistsException;
    
    /**
     * Updates vote data
     * @param vote - vote data to be updated
     * @throws ObjectNotFoundException - thrown when vote could not be found into the system 
     */
    public void updateVote(Vote vote) throws ObjectNotFoundException;
    
    /**
     * Creates or updates existing vote of the user for given place
     * @param userId - user identifier 
     * @param placeId - place identifier 
     * @return - created vote object
     * @throws ObjectNotFoundException - thrown either when user or place was not found 
     */
    public Vote placeUserVote(Integer userId, Integer placeId) throws ObjectNotFoundException;
}
