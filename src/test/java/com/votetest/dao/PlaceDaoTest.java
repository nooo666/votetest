package com.votetest.dao;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.votetest.model.MenuItem;
import com.votetest.model.Place;

/**
 * Test case for UserDaoImpl.
 * @author Nikolay Dechev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@Transactional
public class PlaceDaoTest {
    
    @Resource(name = "placeDao")
    private IPlaceDao placeDao;
    
    /** 
     * @return
     */
    public static Place createRandomPlace() {
        Place place = new Place();
        place.setName(UUID.randomUUID().toString());
        place.addMenuItem(createRandomMenuItem());
        place.addMenuItem(createRandomMenuItem());
        
        return place;
    }
    
    public static MenuItem createRandomMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(UUID.randomUUID().toString());
        menuItem.setPrice(new Random().nextFloat());
        
        return menuItem;
    }
    
    /**
     * 
     */
    @Test 
    public void testCreatePlace() {
        Place place = createRandomPlace();
        Integer id = placeDao.createPlace(place);
        
        Assert.assertNotNull("Place id must be not null", id);
    }

    @Test 
    public void testUpdatePlace() {
        String newName = "NEW NAME " + UUID.randomUUID().toString();
        Place place = createRandomPlace();
        Integer id = placeDao.createPlace(place);
        Place loadedPlace = placeDao.getPlaceById(id);
        int itemsSize = place.getMenuItems().size();
        loadedPlace.setName(newName);
        loadedPlace.addMenuItem(createRandomMenuItem());
        
        for (MenuItem mi : loadedPlace.getMenuItems()) {
            loadedPlace.removeMenuItem(mi);
            break;
        }
        
        placeDao.updatePlace(loadedPlace);
        loadedPlace = placeDao.getPlaceById(id);
        
        Assert.assertNotNull("User should not be null", loadedPlace);
        Assert.assertEquals(newName, loadedPlace.getName());
        Assert.assertEquals(itemsSize, loadedPlace.getMenuItems().size());
    }

    @Test 
    public void testFindPlaceById() {
        Place place = createRandomPlace();
        Integer id = placeDao.createPlace(place);
        Place loadedPlace = placeDao.getPlaceById(id);
        
        Assert.assertNotNull("User should not be null", loadedPlace);
        Assert.assertEquals(place.getName(), loadedPlace.getName());
        Assert.assertEquals(place.getMenuItems().size(), loadedPlace.getMenuItems().size());
    }

    @Test 
    public void testFindAllPlaces() {
        placeDao.createPlace(createRandomPlace());
        placeDao.createPlace(createRandomPlace());
        List<Place> places = placeDao.getAllPlaces();
        
        Assert.assertNotNull("User should not be null", places);
        Assert.assertEquals(2, places.size());
    }
}

