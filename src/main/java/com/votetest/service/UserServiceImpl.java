package com.votetest.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.votetest.dao.IUserDao;
import com.votetest.model.User;

@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    
    @Override
    public User getUserById(Integer userId) throws ObjectNotFoundException {
        User user = userDao.getUserById(userId);
        
        if (user == null) {
            throw new ObjectNotFoundException(String.format("User with id %s not found into the system", userId));
        }
        
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws ObjectNotFoundException {
        User user = userDao.getUserByUsername(username);
        
        if (user == null) {
            throw new ObjectNotFoundException(String.format("User with username %s not found into the system", username));
        }
        
        return user;
    }

    @Override
    public User createUser(User user) throws ObjectAlreadyExistsException {
        
        if (user.getId() != null && userDao.objectExists(user.getId())) {
            throw new ObjectAlreadyExistsException(String.format("User with id %s has already been registed into the system", user.getId()));
        }
        
        if (user.getId() != null) {
            user.setId(null);
        }
        
        Integer userId = userDao.createUser(user);
        User loadedUser = userDao.getUserById(userId);

        return loadedUser;
    }

}
