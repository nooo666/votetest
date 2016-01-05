package com.votetest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.votetest.model.User;
import com.votetest.service.IUserService;
import com.votetest.service.ObjectAlreadyExistsException;
import com.votetest.service.ObjectNotFoundException;


@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseObject<User> getUser(@PathVariable Integer id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        ResponseObject<User> responseObj = new ResponseObject<>();
        User user = null;
        int statusCode = HttpServletResponse.SC_OK;
        
        try {
            user = userService.getUserById(id);
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseObj.addMessage(e.getMessage());
        }
         
        response.setStatus(statusCode);
        responseObj.setData(user);
        responseObj.setCode(statusCode);
        
        return responseObj;
    }
    
    @RequestMapping(value = "/users/username/{name}", method = RequestMethod.GET)
    public ResponseObject<User> getUserByUsername(@PathVariable String name, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        ResponseObject<User> responseObj = new ResponseObject<>();
        User user = null;
        int statusCode = HttpServletResponse.SC_OK;
        
        try {
            user = userService.getUserByUsername(name);
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseObj.addMessage(e.getMessage());
        }
         
        response.setStatus(statusCode);
        responseObj.setData(user);
        responseObj.setCode(statusCode);
        
        return responseObj;
    }
    
    
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseObject<User> createUser(@RequestBody User user, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseObject<User> responseObj = new ResponseObject<>();
        int statusCode = HttpServletResponse.SC_OK; 
        
        try {
            user = userService.createUser(user);
            response.setHeader("Location", "/users/" + user.getId());
        } catch (ObjectAlreadyExistsException e) {
            statusCode = HttpServletResponse.SC_CONFLICT;
            responseObj.addMessage(e.getMessage());
        }
        
        response.setStatus(statusCode);
        responseObj.setData(user);
        responseObj.setCode(statusCode);
        
        return responseObj;        
    }
}
