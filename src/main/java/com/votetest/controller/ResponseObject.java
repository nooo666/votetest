package com.votetest.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Basic Response object that is returned by CRUD methods of controller. It is
 * made in order to be able to return errors and data without interfere original
 * object data
 * @author Nikolay Dechev
 * @param <T> - Generic type to be used i.e. User, Place, Vote etc
 */
public class ResponseObject<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3113056254721170318L;

    /**
     * Data to be returned
     */
    private T data = null;

    /**
     * Response code
     */
    private int code;
    
    /**
     * System messages if any  
     */
    private Collection<String> messages;

    /**
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return
     */
    public Collection<String> getMessages() {
        return messages;
    }

    /**
     * @param messages
     */
    public void setMessages(Collection<String> messages) {
        this.messages = messages;
    }

    /**
     * @param message
     */
    public void addMessage(String message) {
        if (messages == null) {
            messages = new ArrayList<String>();
        }

        messages.add(message);
    }
}
