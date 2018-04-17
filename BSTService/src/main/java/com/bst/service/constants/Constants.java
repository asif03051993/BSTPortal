/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.constants;

/**
 * Constants in BST Rest Service.
 * 
 * @author asifaham
 */
public interface Constants {
    
    /**
     * Session Name.
     */
    String sessionName = "bst-session";
    
    /**
     * Verticle worker pool size.
     */
    Integer workerPoolSize = 10;
    
    /**
     * Param name in Delete service.
     */
    String key = "key";
    
    /**
     * Http Content Type Header Name.
     */
    String contentTypeName = "content-type";
    
    /**
     * Http Json Content Type Value.
     */
    String jsonValue = "application/json";
    
    /**
     * Http Html Content Type Value.
     */
    String htmlValue = "application/html";
    
    /**
     * Http Text Content Type Value.
     */
    String textValue = "application/text";
    
    /**
     * Space used in constructing BST in tree format.
     */
    String space = " ";
    
    /**
     * Underscore used in constructing BST in tree format.
     */
    String underScore = "_";
}
