package com.votetest.dao;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.votetest.model.User;

/**
 * Test case for UserDaoImpl.
 * @author Nikolay Dechev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@Transactional
public class UserDaoTest {
    
    @Resource(name = "userDao")
    private IUserDao userDao;
    
    /** 
     * @return
     */
    public static User createRandomUser() {
        User user = new User();
        user.setUsername(Long.toString(new Random().nextLong()));
        user.setPassword(Long.toString(new Random().nextLong()));
        user.setFullname(UUID.randomUUID().toString());
        
        return user;
    }
    
    /**
     * 
     */
    @Test 
    public void testCreateUser() {
        User user = createRandomUser();
        Integer id = userDao.createUser(user);
        
        Assert.assertNotNull("User id must be not null", id);
    }

    @Test 
    public void testFindUserById() {
        User user = createRandomUser();
        Integer id = userDao.createUser(user);
        User loadedUser = userDao.getUserById(id);
        
        Assert.assertNotNull("User should not be null", loadedUser);
        Assert.assertEquals(user.getFullname(), loadedUser.getFullname());
    }

    @Test 
    public void testFindUserByUsername() {
        User user = createRandomUser();
        userDao.createUser(user);
        User loadedUser = userDao.getUserByUsername(user.getUsername());
        
        Assert.assertNotNull("User should not be null", loadedUser);
        Assert.assertEquals(user.getFullname(), loadedUser.getFullname());
    }
}

