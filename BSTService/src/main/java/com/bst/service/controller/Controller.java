/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.controller;

import com.google.gson.JsonObject;
import com.bst.service.constants.Constants;
import com.bst.service.defaults.BSTree;
import com.bst.service.model.Input;
import com.bst.service.exceptions.DuplicateNodeException;
import com.bst.service.exceptions.NoSuchNodeException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller (or handler) for BST web service.
 * 
 * @author asifaham
 */
@Slf4j
public class Controller extends AbstractVerticle {
    
    /**
     * Stores sessionId and its associated BST.
     */
    private static Map<String, BSTree> dataStore;
    
    /**
     * Constructor.
     * 
     * @param vertx 
     */
    public Controller(Vertx vertx) {
        this.vertx = vertx;
        this.dataStore = new HashMap<>();
    }
    
    /**
     * Returns BSTree associated with a session.
     * 
     * @param sessionId
     *           - current session id.
     * @return {@link BSTree} associated with the session
     */
    private BSTree getBSTree(String sessionId){
        if(dataStore.containsKey(sessionId)){
            return dataStore.get(sessionId);
        } else {
            BSTree tree = new BSTree();
            dataStore.put(sessionId, tree);
            return tree;
        }
    }
    
    /**
     * Handles insert node request.
     * 
     * @param routingContext 
     */
    public void addNode(RoutingContext routingContext){
        log.info("Handling insert node request..");
        
        String requestJson = routingContext.getBodyAsString();
        HttpServerResponse response = routingContext.response();
        response.putHeader(Constants.contentTypeName, Constants.textValue);

        Input input = new Input().fromJson(requestJson);
        if(input.getData() == null){
            response.setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
            response.end("Invalid request");
        }
        
        String sessionId = routingContext.session().id();
        BSTree bstree = getBSTree(sessionId);
        
        try {
            bstree.insert(input.getData());
            response.setStatusCode(HttpResponseStatus.OK.code());
            response.end("Successfully inserted data.");
        } catch (DuplicateNodeException ex) {
            log.error(ex.getMessage());
            response.setStatusCode(HttpResponseStatus.CONFLICT.code());
            response.end(ex.getMessage());
        }   
    }
    
    /**
     * Handles remove node request.
     * 
     * @param routingContext 
     */
    public void removeNode(RoutingContext routingContext){
        log.info("Handling remove node request..");
        
        String input = routingContext.request().getParam(Constants.key);
        HttpServerResponse response = routingContext.response();
        response.putHeader(Constants.contentTypeName, Constants.textValue);
        
        //validate input
        if(input == null){
            response.setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
            response.end("Invalid request");
        }
        
        int data = Integer.parseInt(input);
        String sessionId = routingContext.session().id();
        BSTree bstree = getBSTree(sessionId);
        
        try {
            bstree.delete(data);
            response.setStatusCode(HttpResponseStatus.OK.code());
            response.end("Successfully deleted data.");
        } catch (NoSuchNodeException ex) {
            log.error(ex.getMessage());
            response.setStatusCode(HttpResponseStatus.CONFLICT.code());
            response.end(ex.getMessage());
        }
    }
    
    /**
     * Handles find position of node (search) request.
     * 
     * @param routingContext 
     */
    public void getPosition(RoutingContext routingContext){
        log.info("Handling find position of node (search) request..");
        
        String requestJson = routingContext.getBodyAsString();
        HttpServerResponse response = routingContext.response();
        
        Input input = new Input().fromJson(requestJson);
        if(input.getData() == null){
            response.setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
            response.end("Invalid request");
        }
        
        String sessionId = routingContext.session().id();
        BSTree bstree = getBSTree(sessionId);
        
        try {
            JsonObject jsonObject = bstree.getPosition(input.getData());
            response.putHeader(Constants.contentTypeName, Constants.jsonValue);
            response.setStatusCode(HttpResponseStatus.OK.code());
            response.end(jsonObject.toString());
        } catch (NoSuchNodeException ex) {
            log.error(ex.getMessage());
            response.putHeader(Constants.contentTypeName, Constants.textValue);
            response.setStatusCode(HttpResponseStatus.CONFLICT.code());
            response.end(ex.getMessage());
        }
    }
    
    /**
     * Handles print binary search tree request.
     * 
     * @param routingContext 
     */
    public void printTree(RoutingContext routingContext){
        log.info("Handling print request..");
        
        String sessionId = routingContext.session().id();
        BSTree bstree = getBSTree(sessionId);
        
        HttpServerResponse response = routingContext.response();
        response.putHeader(Constants.contentTypeName, Constants.htmlValue);
        response.end(bstree.print());
    }
    
    /**
     * Handles get statistics of binary search tree request.
     * 
     * @param routingContext 
     */
    public void getStats(RoutingContext routingContext){
        log.info("Handling get statistics of binary search tree request..");
        
        String sessionId = routingContext.session().id();
        BSTree bstree = getBSTree(sessionId);
        
        int count = bstree.countNodes();
        int[] minMaxLevels = bstree.findMinMaxLevels();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("count", count);
        jsonObject.addProperty("minHeight", minMaxLevels[0]);
        jsonObject.addProperty("maxHeight", minMaxLevels[1]);
        
        HttpServerResponse response = routingContext.response();
        response.putHeader(Constants.contentTypeName, Constants.jsonValue);
        response.end(jsonObject.toString());
    }
}
