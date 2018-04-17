/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.model;

import lombok.Getter;
import lombok.Setter;


/**
 * Binary Search Tree Node Model.
 * 
 * @author asifaham
 */
@Getter
@Setter
public class BSTNode {
    
    /**
     * Left Child.
     */
    private BSTNode left;
    
    /**
     * Right Child.
     */
    private BSTNode right;
    
    /**
     * Data or key represented by the node.
     */
    private int data;
    
    /**
     * Constructor.
     * 
     * @param data 
     *         data represented by node.
     */
    public BSTNode(int data){
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    /**
     * Check if node is leaf.
     * 
     * @return <code>true</code> if node is leaf, else <code>false</code>.
     */
    public boolean isLeaf(){
        return (left == null && right == null);
    }           
}
