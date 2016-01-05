package com.votetest.dao;

import org.hibernate.HibernateException;

/**
 * Exception thrown by DAO objects
 * @author Nikolay Dechev
 * 
 */
public class DaoException extends HibernateException {

    /**
     * 
     */
    private static final long serialVersionUID = 4191904276505339918L;

    /**
     * Constructs an exception object with given cause
     * @param cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
