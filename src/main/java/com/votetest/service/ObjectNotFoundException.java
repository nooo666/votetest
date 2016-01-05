package com.votetest.service;

/**
 * Thrown when object not found in the system
 * @author Nikolay Dechev
 *
 */
public class ObjectNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 6306515970597228494L;

    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }
}
