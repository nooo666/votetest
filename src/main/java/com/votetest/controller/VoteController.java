package com.votetest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.votetest.model.Vote;
import com.votetest.model.VoteScore;
import com.votetest.service.IVoteService;
import com.votetest.service.ObjectAlreadyExistsException;
import com.votetest.service.ObjectNotFoundException;


@RestController
public class VoteController {

    @Autowired
    private IVoteService voteService;

    @RequestMapping(value = "/votes/{id}", method = RequestMethod.GET)
    public ResponseObject<Vote> getVote(@PathVariable Integer id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        ResponseObject<Vote> responseObj = new ResponseObject<>();
        Vote vote = null;
        int statusCode = HttpServletResponse.SC_OK;
        
        try {
            vote = voteService.getVoteById(id);
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseObj.addMessage(e.getMessage());
        }
         
        response.setStatus(statusCode);
        responseObj.setData(vote);
        responseObj.setCode(statusCode);
        
        return responseObj;
    }
    
    @RequestMapping(value = "/votes", method = RequestMethod.POST)
    public ResponseObject<Vote> createVote(@RequestBody Vote vote, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseObject<Vote> responseObj = new ResponseObject<>();
        int statusCode = HttpServletResponse.SC_OK; 
        
        try {
            vote = voteService.createVote(vote);
            response.setHeader("Location", "/votes/" + vote.getId());
        } catch (ObjectAlreadyExistsException e) {
            statusCode = HttpServletResponse.SC_CONFLICT;
            responseObj.addMessage(e.getMessage());
        }
        
        response.setStatus(statusCode);
        responseObj.setData(vote);
        responseObj.setCode(statusCode);
        
        return responseObj;        
    }
    
    @RequestMapping(value = "/votes", method = RequestMethod.PUT)
    public ResponseObject<Vote> updateVote(@RequestBody Vote vote, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        ResponseObject<Vote> responseObj = new ResponseObject<>();
        int statusCode = HttpServletResponse.SC_OK;
        
        if (vote == null) {
            statusCode = HttpServletResponse.SC_NO_CONTENT; 
        } else {
            try {
                voteService.updateVote(vote);
                vote = voteService.getVoteById(vote.getId());
            } catch (ObjectNotFoundException e) {
                statusCode = HttpServletResponse.SC_NOT_FOUND;
            }
        }
        
        response.setStatus(statusCode);
        responseObj.setData(vote);
        responseObj.setCode(statusCode);
        
        return responseObj;
    }
    
    @RequestMapping(value = "/votes", method = RequestMethod.GET)
    public List<Vote> listVotes(@RequestParam(value="date") String dateStr, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = dateformat.parse(dateStr);
        
        return voteService.getVotes(date);
    }

    @RequestMapping(value = "/votes/places/{placeId}/users/{userId}", method = RequestMethod.POST)
    public ResponseObject<Vote> placeUserVote(@PathVariable Integer placeId, @PathVariable Integer userId, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseObject<Vote> responseObj = new ResponseObject<>();
        Vote vote = null;
        int statusCode = HttpServletResponse.SC_OK;
        
        try {
            vote = voteService.placeUserVote(userId, placeId);
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseObj.addMessage(e.getMessage());
        }
         
        response.setStatus(statusCode);
        responseObj.setData(vote);
        responseObj.setCode(statusCode);
        
        return responseObj;
    }

    @RequestMapping(value = "/votescores", method = RequestMethod.GET)
    public List<VoteScore> listVoteScores(@RequestParam(value="date", defaultValue="") String dateStr, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = !dateStr.equals("") ? dateformat.parse(dateStr) : new Date();
        
        return voteService.getVotesScore(date);
    }
}
