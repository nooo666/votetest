package com.votetest.service;

/**
 * Thrown when object already exists in the system
 * @author Nikolay Dechev
 *
 */
public class ObjectAlreadyExistsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3830418597056373023L;

    public ObjectAlreadyExistsException() {
        super();
    }

    public ObjectAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ObjectAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }

    public ObjectAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
