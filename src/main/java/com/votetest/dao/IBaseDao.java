package com.votetest.dao;

import java.io.Serializable;

/**
 * Interface defining basic general operations related to DAO objects
 * @author Nikolay Dechev
 *
 */
public interface IBaseDao {
    public boolean objectExists(Serializable id) throws DaoException;
}
