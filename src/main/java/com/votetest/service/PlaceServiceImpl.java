package com.votetest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.votetest.dao.IPlaceDao;
import com.votetest.model.MenuItem;
import com.votetest.model.Place;

@Transactional
public class PlaceServiceImpl implements IPlaceService {

    @Autowired
    private IPlaceDao placeDao;
    
    @Override
    public Place getPlaceById(Integer placeId) throws ObjectNotFoundException {
        Place place = placeDao.getPlaceById(placeId);
        
        if (place == null) {
            throw new ObjectNotFoundException(String.format("Place with id %s not found into the system", placeId));
        }
        
        return place;
    }

    @Override 
    public List<Place> getAllPlaces() {
        return placeDao.getAllPlaces();
    }

    @Override
    public Place createPlace(Place place) throws ObjectAlreadyExistsException {
        
        if (place.getId() != null && placeDao.objectExists(place.getId())) {
            throw new ObjectAlreadyExistsException(String.format("Place with id %s has already been registed into the system", place.getId()));
        }
        
        if (place.getId() != null) {
            place.setId(null);
        }
        
        Integer placeId = placeDao.createPlace(place);
        Place loadedPlace = placeDao.getPlaceById(placeId);

        return loadedPlace;
    }

    @Override
    public void updatePlace(Place place) throws ObjectNotFoundException {
        
        if (!placeDao.objectExists(place.getId())) {
            throw new ObjectNotFoundException(String.format("Place with id %s not found into the system", place.getId()));
        }
        
        placeDao.updatePlace(place);
    }

    @Override
    public MenuItem addMenuItem(Integer placeId, MenuItem menuItem) throws ObjectNotFoundException {
        Place place = getPlaceById(placeId);
        place.addMenuItem(menuItem);
        updatePlace(place);
        place = getPlaceById(placeId);
        MenuItem result = null;
        
        for (MenuItem mi : place.getMenuItems()) {
            if (mi == menuItem) {
                result = mi;
                break;
            }
        }
        
        return result;
    }

    @Override
    public MenuItem removeMenuItem(Integer placeId, Integer itemId) throws ObjectNotFoundException {
        Place place = getPlaceById(placeId);
        MenuItem item = place.removeMenuItemById(itemId);
        
        if (item == null) {
            throw new ObjectNotFoundException(String.format("Menu item with id %s not found into the system", itemId));
        }
        
        updatePlace(place);
        
        return item;
    }

}
