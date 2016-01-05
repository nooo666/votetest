package com.votetest.dao;

import com.votetest.model.User;

/**
 * Interface defines operations related to user management 
 * @author Nikolay Dechev
 *
 */
public interface IUserDao extends IBaseDao {

    /**
     * Retrieves user by its system identifier 
     * @param userId - user identifier 
     * @return - loaded user object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public User getUserById(Integer userId) throws DaoException;
    
    /**
     * Retrieves user by its username
     * @param username - unique username  
     * @return - loaded user object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public User getUserByUsername(String username) throws DaoException;
    
    /**
     * Creates new user with the specified data 
     * @param user - user data to be used 
     * @return - created user identifier
     * @throws DaoException
     */
    public Integer createUser(User user) throws DaoException;
}

