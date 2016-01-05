package com.votetest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ 
    @NamedQuery(name = "voteByUser", query = "from Vote t where voteUser.id = :userId"),
    @NamedQuery(name = "voteByUserAndDate", query = "from Vote t where voteUser.id = :userId and (voteDate >= :voteDateStart or :voteDateStart is null) and (voteDate <= :voteDateEnd or :voteDateEnd is null) "),
    @NamedQuery(name = "allVotes", query = "from Vote t"),
    @NamedQuery(name = "getVotes", query = "from Vote t where voteDate >= :startDate and voteDate <= :endDate"),
    })
@Entity
@Table(name = "VOTES")
public class Vote implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4161861565348510653L;

    /**
     * System identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id = null;

    @Column(name = "vote_date", nullable = false)
    private Date voteDate = null;
    
    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "vote_user", unique = false, nullable = false)
    private User voteUser = null;
    
    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "vote_place", unique = false, nullable = false)
    private Place votePlace = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public User getVoteUser() {
        return voteUser;
    }

    public void setVoteUser(User voteUser) {
        this.voteUser = voteUser;
    }

    public Place getVotePlace() {
        return votePlace;
    }

    public void setVotePlace(Place votePlace) {
        this.votePlace = votePlace;
    }
}
