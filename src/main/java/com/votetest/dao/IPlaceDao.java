package com.votetest.dao;

import java.util.List;

import com.votetest.model.Place;

/**
 * Interface defines operations related to place management 
 * @author Nikolay Dechev
 *
 */
public interface IPlaceDao extends IBaseDao {

    /**
     * Retrieves place by its system identifier 
     * @param placeId - place identifier 
     * @return - loaded place object or null if not found
     * @throws DaoException - exception thrown on system failure 
     */
    public Place getPlaceById(Integer placeId) throws DaoException;
    
    /**
     * Retrieves all places 
     * @return - list of all places stored into the system
     * @throws DaoException - exception thrown on system failure 
     */
    public List<Place> getAllPlaces() throws DaoException;
    
    /**
     * Creates new place with the specified data 
     * @param place - place data to be used 
     * @return - created place identifier
     * @throws DaoException - exception thrown on system failure 
     */
    public Integer createPlace(Place place) throws DaoException;
    
    /**
     * Updates existing place with the specified data 
     * @param place - place data to be used 
     * @throws DaoException - exception thrown on system failure 
     */
    public void updatePlace(Place place) throws DaoException;
}

