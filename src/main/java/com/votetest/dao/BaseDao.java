package com.votetest.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Basic operations needed by all DAO objects
 * @author Nikolay Dechev
 *
 */
public class BaseDao {
    
    /**
     * Reference to session factory 
     */
    private SessionFactory sessionFactory; 
    
    /**
     * Set session factory instance
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return - returns session factory instance
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * @return - retrieves current session
     */
    public Session getCurrentSession() {
        Session currentSession = getSessionFactory().getCurrentSession();
        
        return currentSession;
    }
    
    /**
     * Check whether object with given identifier exists into the database  
     * @param id - identifier value to check 
     * @param idName - identifier name
     * @param clazz - entity class name 
     * @return - true if exists, false otherwise
     * @throws DaoException - exception that wraps the causes of the error
     */
    public boolean objectExists(Serializable id, String idName, Class<?> clazz) throws DaoException {
        try {
            return getCurrentSession().createCriteria(clazz)
                    .add(Restrictions.eq(idName, id))
                    .setProjection(Projections.property(idName))
                    .uniqueResult() != null;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }     
    }

}
