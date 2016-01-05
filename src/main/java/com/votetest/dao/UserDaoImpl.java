package com.votetest.dao;

import java.io.Serializable;

import com.votetest.model.User;

public class UserDaoImpl extends BaseDao implements IUserDao {

    private static final String OBJECT_ID = "id";

    @Override
    public boolean objectExists(Serializable id) throws DaoException {
        return super.objectExists(id, OBJECT_ID, User.class);
    }

    @Override
    public User getUserById(Integer userId) throws DaoException {
        return (User) getCurrentSession().get(User.class, userId);
    }

    @Override
    public User getUserByUsername(String username) throws DaoException {
        return (User) getCurrentSession().getNamedQuery("userByUsername").setString("username", username).uniqueResult();
    }
 
    @Override
    public Integer createUser(User user) throws DaoException {
        return (Integer) getCurrentSession().save(user);
    }
    
}
