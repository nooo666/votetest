package com.votetest.service;

import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.votetest.dao.PlaceDaoTest;
import com.votetest.model.MenuItem;
import com.votetest.model.Place;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:testApplicationContext.xml"})
@Transactional
public class PlaceServiceTest {
    
    @Resource(name = "placeService")
    private IPlaceService placeService;

    @Test
    public void createPlace() throws ObjectAlreadyExistsException {
        Place place = PlaceDaoTest.createRandomPlace();
        place = placeService.createPlace(place);
        
        Assert.assertNotNull("Place should not be null", place);
        Assert.assertNotNull("Place id should not be null", place.getId());
        
        try {
            placeService.createPlace(place);
            Assert.fail("Exception should be thrown");
        } catch (ObjectAlreadyExistsException e) {
        }
    }

    @Test
    public void updatePlace() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        String name = "newName" + UUID.randomUUID().toString();
        Place place = PlaceDaoTest.createRandomPlace();
        place = placeService.createPlace(place);
        place.setName(name);
        placeService.updatePlace(place);
        place = placeService.getPlaceById(place.getId());
        
        Assert.assertEquals(name, place.getName());
        
        
        try {
            Place newPlace = PlaceDaoTest.createRandomPlace();
            newPlace.setId(place.getId() + 1);
            placeService.updatePlace(newPlace);
            Assert.fail("Exception should be thrown");
        } catch (ObjectNotFoundException e) {
        }
    }

    @Test
    public void getPlaceById() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        Place place = PlaceDaoTest.createRandomPlace();
        place = placeService.createPlace(place);
        place = placeService.getPlaceById(place.getId());
        
        Assert.assertNotNull("Place should not be null", place);
        
        try {
            placeService.getPlaceById(place.getId() + 1);
            Assert.fail("Exception should be thrown");
        } catch (ObjectNotFoundException e) {
        }
    }

    @Test
    public void addMenuItem() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        Place place = PlaceDaoTest.createRandomPlace();
        int itemsSize = place.getMenuItems().size();
        place = placeService.createPlace(place);
        MenuItem itemToAdd = PlaceDaoTest.createRandomMenuItem();
        MenuItem menuItem = placeService.addMenuItem(place.getId(), itemToAdd);
        
        Assert.assertNotNull("Item should not be null", menuItem);
        
        place = placeService.getPlaceById(place.getId());
        
        Assert.assertEquals(itemsSize + 1, place.getMenuItems().size());
    }

    @Test
    public void removeMenuItem() throws ObjectAlreadyExistsException, ObjectNotFoundException {
        Place place = PlaceDaoTest.createRandomPlace();
        int size = place.getMenuItems().size();
        place = placeService.createPlace(place);
        MenuItem toRemove = null;
        
        for (MenuItem mi : place.getMenuItems()) {
            toRemove = mi;
            break; 
        }
        
        MenuItem removedItem = placeService.removeMenuItem(place.getId(), toRemove.getId());
        
        Assert.assertNotNull("Item should not be null", removedItem);
        Assert.assertEquals(toRemove, removedItem);
        
        Place loadedPlace = placeService.getPlaceById(place.getId());
        removedItem = loadedPlace.getMenuItemById(toRemove.getId());
        
        Assert.assertNull("Item should be null", removedItem);
        Assert.assertEquals(size - 1, loadedPlace.getMenuItems().size());
    }
}
