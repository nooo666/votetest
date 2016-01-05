package com.votetest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Place data class
 * @author Nikolay Dechev
 *
 */
@NamedQueries({ 
    @NamedQuery(name = "allActivePlaces", query = "from Place t"),
    })
@Entity
@Table(name = "PLACES")
public class Place implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2270388983943637857L;

    /**
     * System identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id = null;

    /**
     * Name of the restaurant 
     */
    @Column(name = "PLACE_NAME", nullable = false)
    private String name = null;

    /**
     * Reference to menu items
     */
    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval=true)
    private List<MenuItem> menuItems = null;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonManagedReference
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
    
    public void addMenuItem(MenuItem menuItem) {
        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }
        
        menuItem.setPlace(this);
        menuItems.add(menuItem);
    }
    
    public MenuItem getMenuItemById(Integer itemId) {
        MenuItem result = null;
        
        for (MenuItem mi : menuItems) {
            if (mi.getId().equals(itemId)) {
                result = mi;
                break;
            }
        }
        
        return result;
    }
    
    public void removeMenuItem(MenuItem menuItem) {
        this.menuItems.remove(menuItem);
    }

    public MenuItem removeMenuItemById(Integer itemId) {
        MenuItem item = getMenuItemById(itemId);
        removeMenuItem(item);
        
        return item;
    }
}
