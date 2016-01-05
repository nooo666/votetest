package com.votetest.service;

import com.votetest.model.User;

/**
 * Interface defines business methods for user management
 * @author Nikolay Dechev
 *
 */
public interface IUserService {
    
    /**
     * Retrieves user by user identifier 
     * @param userId - user identifier 
     * @return - loaded user
     * @throws ObjectNotFoundException - if user with specified identifier could not be found
     */
    public User getUserById(Integer userId) throws ObjectNotFoundException;
    
    /**
     * Retrieves user by username 
     * @param username - username to identify user 
     * @return - loaded user
     * @throws ObjectNotFoundException - if user with specified username could not be found
     */
    public User getUserByUsername(String username) throws ObjectNotFoundException;
    
    /**
     * Registers new user into the system 
     * @param user - user to be registered 
     * @return - registered user with updated data
     * @throws ObjectAlreadyExistsException - thrown if user has already been registed into the system 
     */
    public User createUser(User user) throws ObjectAlreadyExistsException;
}
