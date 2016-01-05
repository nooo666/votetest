package com.votetest.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.votetest.dao.UserDaoTest;
import com.votetest.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@Transactional
public class UserServiceTest {
    
    @Resource(name = "userService")
    private IUserService userService;

    @Test
    public void createUser() throws ObjectAlreadyExistsException {
        User user = UserDaoTest.createRandomUser();
        user = userService.createUser(user);
        
        Assert.assertNotNull("User should not be null", user);
        Assert.assertNotNull("User id should not be null", user.getId());
        
        try {
            userService.createUser(user);
            Assert.fail("Exception should be thrown");
        } catch (ObjectAlreadyExistsException e) {
        }
    }

    @Test
    public void getUserById() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        User user = UserDaoTest.createRandomUser();
        user = userService.createUser(user);
        user = userService.getUserById(user.getId());
        
        Assert.assertNotNull("User should not be null", user);
        
        try {
            userService.getUserById(user.getId() + 1);
            Assert.fail("Exception should be thrown");
        } catch (ObjectNotFoundException e) {
        }
    }

    @Test
    public void getUserByUsername() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        User user = UserDaoTest.createRandomUser();
        user = userService.createUser(user);
        user = userService.getUserByUsername(user.getUsername());
        
        Assert.assertNotNull("User should not be null", user);
        
        try {
            userService.getUserByUsername(user.getUsername() + 1);
            Assert.fail("Exception should be thrown");
        } catch (ObjectNotFoundException e) {
        }
    }
}
