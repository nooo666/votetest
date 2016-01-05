package com.votetest.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.votetest.model.Place;
import com.votetest.model.Vote;
import com.votetest.model.VoteScore;

public class VoteDaoImpl extends BaseDao implements IVoteDao {

    private static final String OBJECT_ID = "id";

    @Override
    public boolean objectExists(Serializable id) throws DaoException {
        return super.objectExists(id, OBJECT_ID, Vote.class);
    }

    @Override
    public Vote getVoteById(Integer voteId) throws DaoException {
        return (Vote) getCurrentSession().get(Vote.class, voteId);
    }

    @Override
    public Integer createVote(Vote vote) throws DaoException {
        return (Integer) getCurrentSession().save(vote);
    }
 
    @Override
    public List<Vote> getVotesByUserId(Integer userId) throws DaoException {
        return getCurrentSession().getNamedQuery("voteByUser").setInteger("userId", userId).list();
    }

    @Override
    public List<Vote> getVotes(Date startDate, Date endDate) throws DaoException {
        return getCurrentSession().getNamedQuery("getVotes").setDate("startDate", startDate).setDate("endDate", endDate).list();
    }

    @Override
    public List<VoteScore> getVotesScore(Date startDate, Date endDate) throws DaoException {
//        Query query = getCurrentSession().createQuery("select vote_place as votePlaceId, p.place_name as placeName, TO_DATE(TO_CHAR(vote_date, 'DD/MM/YYYY'), 'DD/MM/YYYY') as voteDate, count(*) as votesCount from VOTES V, PLACES P where p.id = v.vote_place and vote_date >= :startDate and vote_date <= :endDate group by vote_place, TO_CHAR(vote_date, 'DD/MM/YYYY'), p.place_name");
        Query query = getCurrentSession().createQuery("select p, count(*) from Vote t, Place p where p.id = t.votePlace and t.voteDate >= :startDate and t.voteDate <= :endDate group by p");
        query.setDate("startDate", startDate).setDate("endDate", endDate);
        List<Object[]> listResult = query.list();
        List<VoteScore> result = new ArrayList<>(listResult.size()); 
        
        for (Object[] r : listResult) {
            VoteScore score = new VoteScore();
            score.setPlace((Place) r[0]);
            score.setVotesCount((Long) r[1]);
            result.add(score);
        }
        
        return result;
    }

    @Override
    public void updateVote(Vote vote) throws DaoException {
        
        if (!getCurrentSession().contains(vote)) {
            vote = (Vote) getCurrentSession().merge(vote);
        }
        
        getCurrentSession().update(vote);
    }

    @Override
    public List<Vote> getVotesByUserIdAndDate(Integer userId, Date startDate, Date endDate) throws DaoException {
        return getCurrentSession().getNamedQuery("voteByUserAndDate").setInteger("userId", userId).setDate("voteDateStart", startDate).setDate("voteDateEnd", endDate).list();
    }
}
