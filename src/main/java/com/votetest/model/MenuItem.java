package com.votetest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "MENUITEMS")
public class MenuItem implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7425751431279087416L;

    /**
     * System identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id = null;

    /**
     * Name of the menu item 
     */
    @Column(name = "ITEM_NAME", nullable = false)
    private String name = null;
    
    /**
     * Price of the menu item 
     */
    @Column(name = "ITEM_PRICE", nullable = false)
    private float price = 0;

    @ManyToOne
    @JoinColumn(name = "PLACE_ID") 
    private Place place = null;
    
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @JsonBackReference
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
