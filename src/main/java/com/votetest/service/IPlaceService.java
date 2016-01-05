package com.votetest.service;

import java.util.List;

import com.votetest.model.MenuItem;
import com.votetest.model.Place;

/**
 * Interface defines business methods for place management
 * @author Nikolay Dechev
 *
 */
public interface IPlaceService {
    
    /**
     * Retrieves place with the specified identifier 
     * @param placeId - place identifier 
     * @return - retrieved place 
     * @throws ObjectNotFoundException - thrown when place with requested identifier could not be found 
     */
    public Place getPlaceById(Integer placeId) throws ObjectNotFoundException;
    
    /**
     * Retrieves all places into the system 
     * @return
     */
    public List<Place> getAllPlaces();
    
    /**
     * Creates place with the given data
     * @param place - place data to be used
     * @return - place with updated data 
     * @throws ObjectAlreadyExistsException - thrown if place already has been registed to the system 
     */
    public Place createPlace(Place place) throws ObjectAlreadyExistsException;
    
    /**
     * Updates place information 
     * @param place - new place data to be updated in the system 
     * @throws ObjectNotFoundException - thrown when place to be updated has not been found 
     */
    public void updatePlace(Place place) throws ObjectNotFoundException;
    
    /**
     * Adds menu item to specified place
     * @param placeId - identifier of the place
     * @param menuItem - menu item data to be added
     * @return - added menu item 
     * @throws ObjectNotFoundException - thrown when place was not found 
     */
    public MenuItem addMenuItem(Integer placeId, MenuItem menuItem) throws ObjectNotFoundException;
    
    /**
     * Removes menu item from specified place's menu
     * @param placeId - identifier of the place
     * @param itemId - identifier of the menu item 
     * @return - removed menu item 
     * @throws ObjectNotFoundException - thrown when either place or menu item was not found 
     */
    public MenuItem removeMenuItem(Integer placeId, Integer itemId) throws ObjectNotFoundException;
}
