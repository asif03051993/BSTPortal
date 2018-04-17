/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.exceptions;

/**
 * Duplicate node exception.
 * 
 * @author asifaham
 */
public class DuplicateNodeException extends Exception {
    
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
    public DuplicateNodeException(int data) {
        super("Node with data " + data + " already exists.");
        this.message = "Node with data " + data + " already exists.";
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
