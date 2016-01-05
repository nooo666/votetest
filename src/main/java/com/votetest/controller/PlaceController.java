package com.votetest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.votetest.model.MenuItem;
import com.votetest.model.Place;
import com.votetest.service.IPlaceService;
import com.votetest.service.ObjectAlreadyExistsException;
import com.votetest.service.ObjectNotFoundException;


@RestController
public class PlaceController {

    @Autowired
    private IPlaceService placeService;

    @RequestMapping(value = "/places/{id}", method = RequestMethod.GET)
    public ResponseObject<Place> getPlace(@PathVariable Integer id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        ResponseObject<Place> responseObj = new ResponseObject<>();
        Place place = null;
        int statusCode = HttpServletResponse.SC_OK;
        
        try {
            place = placeService.getPlaceById(id);
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseObj.addMessage(e.getMessage());
        }
         
        response.setStatus(statusCode);
        responseObj.setData(place);
        responseObj.setCode(statusCode);
        
        return responseObj;
    }
    
    @RequestMapping(value = "/places", method = RequestMethod.POST)
    public ResponseObject<Place> createPlace(@RequestBody Place place, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseObject<Place> responseObj = new ResponseObject<>();
        int statusCode = HttpServletResponse.SC_OK; 
        
        try {
            place = placeService.createPlace(place);
            response.setHeader("Location", "/places/" + place.getId());
        } catch (ObjectAlreadyExistsException e) {
            statusCode = HttpServletResponse.SC_CONFLICT;
            responseObj.addMessage(e.getMessage());
        }
        
        response.setStatus(statusCode);
        responseObj.setData(place);
        responseObj.setCode(statusCode);
        
        return responseObj;        
    }
    
    @RequestMapping(value = "/places", method = RequestMethod.PUT)
    public ResponseObject<Place> updatePlace(@RequestBody Place place, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        ResponseObject<Place> responseObj = new ResponseObject<>();
        int statusCode = HttpServletResponse.SC_OK;
        
        if (place == null) {
            statusCode = HttpServletResponse.SC_NO_CONTENT; 
        } else {
            try {
                placeService.updatePlace(place);
                place = placeService.getPlaceById(place.getId());
            } catch (ObjectNotFoundException e) {
                statusCode = HttpServletResponse.SC_NOT_FOUND;
            }
        }
        
        response.setStatus(statusCode);
        responseObj.setData(place);
        responseObj.setCode(statusCode);
        
        return responseObj;
    }
    
    @RequestMapping(value = "/places", method = RequestMethod.GET)
    public List<Place> listPlaces(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return placeService.getAllPlaces();
    }

    
    @RequestMapping(value = "/places/{id}/items", method = RequestMethod.POST)
    public ResponseObject<MenuItem> addMenuItem(@PathVariable Integer id, @RequestBody MenuItem menuItem, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseObject<MenuItem> responseObj = new ResponseObject<>();
        int statusCode = HttpServletResponse.SC_OK; 
        MenuItem item = null;
        
        try {
            item = placeService.addMenuItem(id, menuItem);
            response.setHeader("Location", "/places/" + id +"/items/"+ item.getId());
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseObj.addMessage(e.getMessage());
        }
        
        response.setStatus(statusCode);
        responseObj.setData(item);
        responseObj.setCode(statusCode);
        
        return responseObj;        
    }
    
    @RequestMapping(value = "/places/{id}/items/{itemId}", method = RequestMethod.DELETE)
    public ResponseObject<MenuItem> removeMenuItem(@PathVariable Integer id, @PathVariable Integer itemId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseObject<MenuItem> responseObj = new ResponseObject<>();
        int statusCode = HttpServletResponse.SC_OK; 
        MenuItem item = null;
        
        try {
            item = placeService.removeMenuItem(id, itemId);
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseObj.addMessage(e.getMessage());
        }
        
        response.setStatus(statusCode);
        responseObj.setData(item);
        responseObj.setCode(statusCode);
        
        return responseObj;        
    }
}
