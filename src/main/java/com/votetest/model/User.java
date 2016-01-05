package com.votetest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User data class
 * @author Nikolay Dechev
 *
 */
@NamedQueries({ 
    @NamedQuery(name = "userByUsername", query = "from User t where username = :username"),
    })
@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = { "USERNAME" }) )
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5094119373519084261L;

    /**
     * System identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id = null;
    
    /**
     * Login user identifier
     */
    @Column(name = "USERNAME", nullable = false)
    private String username = null;
    
    /**
     * Login password
     */
    @Column(name = "PASSWORD", nullable = false)
    private String password = null;
    
    /**
     * User full name 
     */
    @Column(name = "FULLNAME", nullable = false)
    private String fullname = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
 