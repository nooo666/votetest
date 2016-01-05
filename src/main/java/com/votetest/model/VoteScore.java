package com.votetest.model;

import java.io.Serializable;
import java.util.Date;

public class VoteScore implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 780974618988547853L;

    /**
     * Place that voting applies to 
     */
    private Place place = null;
    
    /**
     * total votes
     */
    private long votesCount = 0;
    
    /**
     * Date for the vote 
     */
    private Date voteDate = null;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public long getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(long votesCount) {
        this.votesCount = votesCount;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }
}
