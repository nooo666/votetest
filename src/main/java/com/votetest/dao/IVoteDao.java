package com.votetest.dao;

import java.util.Date;
import java.util.List;

import com.votetest.model.Vote;
import com.votetest.model.VoteScore;

/**
 * Interface defines operations related to vote management 
 * @author Nikolay Dechev
 *
 */
public interface IVoteDao extends IBaseDao {

    /**
     * Retrieves vote by its system identifier 
     * @param voteId - vote identifier 
     * @return - loaded vote object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public Vote getVoteById(Integer voteId) throws DaoException;
    
    /**
     * Retrieves vote by user identifier
     * @param voteId - vote identifier 
     * @return - loaded vote object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public List<Vote> getVotesByUserId(Integer userId) throws DaoException;
    
    /**
     * Retrieves vote by user identifier
     * @param voteId - vote identifier
     * @param startDate - filter start data
     * @param endDate - filter end data 
     * @return - loaded vote object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public List<Vote> getVotesByUserIdAndDate(Integer userId, Date startDate, Date endDate) throws DaoException;
    
    /**
     * Retrieves all votes
     * @param startDate TODO
     * @param endDate TODO
     * @return - loaded vote object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public List<Vote> getVotes(Date startDate, Date endDate) throws DaoException;

    /**
     * Retrieves votes score
     * @param startDate TODO
     * @param endDate TODO
     * @return - loaded vote object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public List<VoteScore> getVotesScore(Date startDate, Date endDate) throws DaoException;

    /**
     * Creates new vote with the specified data 
     * @param vote - vote data to be used 
     * @return - created vote identifier
     * @throws DaoException - exception thrown on system failure 
     */
    public Integer createVote(Vote vote) throws DaoException;
    
    /**
     * Updates an existing vote with the specified data 
     * @param vote - vote data to be used 
     * @throws DaoException - exception thrown on system failure 
     */
    public void updateVote(Vote vote) throws DaoException;
}

