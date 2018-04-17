/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.exceptions;

/**
 * No such node exception.
 * 
 * @author asifaham
 */
public class NoSuchNodeException  extends Exception {
    
    /**
     * Message for the exception.
     */
    private final String message;
    
    /**
     * Constructor.
     * 
     * @param data 
     *          - node value
     */
    public NoSuchNodeException(int data) {
        super("No such node exists with data " + data + ".");
        this.message = "No such node exists with data " + data + ".";
    }
    
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
