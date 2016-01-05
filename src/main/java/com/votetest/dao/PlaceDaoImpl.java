package com.votetest.dao;

import java.io.Serializable;
import java.util.List;

import com.votetest.model.Place;

public class PlaceDaoImpl extends BaseDao implements IPlaceDao {

    private static final String OBJECT_ID = "id";

    @Override
    public boolean objectExists(Serializable id) throws DaoException {
        return super.objectExists(id, OBJECT_ID, Place.class);
    }

    @Override
    public Place getPlaceById(Integer placeId) throws DaoException {
        return (Place) getCurrentSession().get(Place.class, placeId);
    }

    @Override
    public List<Place> getAllPlaces() throws DaoException {
        return getCurrentSession().getNamedQuery("allActivePlaces").list();
    }

    @Override
    public Integer createPlace(Place place) throws DaoException {
        return (Integer) getCurrentSession().save(place);
    }

    @Override
    public void updatePlace(Place place) throws DaoException {

        if (!getCurrentSession().contains(place)) {
            place = (Place) getCurrentSession().merge(place);
        }
        
        getCurrentSession().update(place);
    }
}
