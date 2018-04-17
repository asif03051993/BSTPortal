/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.defaults;

import com.google.gson.JsonObject;
import com.bst.service.constants.Constants;
import com.bst.service.exceptions.DuplicateNodeException;
import com.bst.service.exceptions.NoSuchNodeException;
import com.bst.service.model.BSTNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

/**
 * Binary Search Tree.
 * 
 * @author asifaham
 */
@Slf4j
public class BSTree {
    
    /** 
     *  BST root.
     */
    private BSTNode root;
    
    /**
     * Count of nodes in BST.
     */
    private int count;
 
     /**
      * Constructor.
      */
     public BSTree() {
         root = null;
         count = 0;
     }
     
     /**
      * Function to check if tree is empty.
      * 
      * @return <code>true</code> if tree is empty, else <code>false</code>.
      */
     public boolean isEmpty() {
         return root == null;
     }
     
     /**
      * Functions to insert data.
      * 
      * @param data
      *          - data to insert
      * @throws DuplicateNodeException 
      */
     public void insert(int data) throws DuplicateNodeException {
         if (search(data)) {
             log.error("Sorry "+ data +" is already present");
             throw new DuplicateNodeException(data);
         } else {
            root = insert(root, data);
            count++;
            log.info("Successfully inserted data");
         }
     }
     
     /**
      * Function to insert data recursively.
      * 
      * @param node 
      *         - root of subtree to start insert at.
      * @param data
      *         - data to insert.
      * @return {@link BSTNode}
      */
     private BSTNode insert(BSTNode node, int data) {
         if (node == null)
             node = new BSTNode(data);
         else
         {
             if (data < node.getData())
                 node.setLeft(insert(node.getLeft(), data));
             else
                 node.setRight(insert(node.getRight(), data));
         }
         return node;
     }
     
     /**
      * Function to delete data.
      * 
      * @param data
      *         - data to be deleted.
      * @throws NoSuchNodeException 
      */
     public void delete(int data) throws NoSuchNodeException {
         if (isEmpty()){
             log.error("Tree Empty");
             throw new NoSuchNodeException(data);
         } else if (search(data) == false) {
             log.error("Sorry "+ data +" is not present");
             throw new NoSuchNodeException(data);
         } else {
             root = delete(root, data);
             count--;
             log.info(data+ " deleted from the tree");
         }
     }
     
     /**
      * Function to delete data recursively.
      * 
      * @param root
      *           - root of subtree to start delete at.
      * @param data
      *           - data to be deleted.
      * @return {@link BSTNode}
      */
     private BSTNode delete(BSTNode root, int data)
     {
         BSTNode p, p2, n;
         if (root.getData() == data)
         {
             BSTNode lt, rt;
             lt = root.getLeft();
             rt = root.getRight();
             if (lt == null && rt == null)
                 return null;
             else if (lt == null)
             {
                 p = rt;
                 return p;
             }
             else if (rt == null)
             {
                 p = lt;
                 return p;
             }
             else
             {
                 p2 = rt;
                 p = rt;
                 while (p.getLeft() != null)
                     p = p.getLeft();
                 p.setLeft(lt);
                 return p2;
             }
         }
         if (data < root.getData())
         {
             n = delete(root.getLeft(), data);
             root.setLeft(n);
         }
         else
         {
             n = delete(root.getRight(), data);
             root.setRight(n);             
         }
         return root;
     }
     
     /**
      * Function to search for an element in BST.
      * 
      * @param val
      *          - data to search.
      * @return <code>true</code> if element is found, else <code>false</code>.
      */
     public boolean search(int val)
     {
         log.info("Searching for " + val + " in BST..");
         return search(root, val);
     }
     
     /**
      * Function to search for an element recursively.
      * 
      * @param root
      *          - root of subtree to start search from.
      * @param val
      *          - data to search
      * @return <code>true</code> if element is found, else <code>false</code>.
      */
     private boolean search(BSTNode root, int val)
     {
         boolean found = false;
         while ((root != null) && !found)
         {
             int rval = root.getData();
             if (val < rval)
                 root = root.getLeft();
             else if (val > rval)
                 root = root.getRight();
             else
             {
                 found = true;
                 log.info(val + " found in BST.");
                 break;
             }
             found = search(root, val);
         }
         return found;
     }
     
     /**
      * Function to count number of nodes.
      * 
      * @return count of nodes in BST.
      */
     public int countNodes()
     {
         log.info("count of nodes in BST is " + count);
         return count;
     }
     
     /**
      * Function to find min max height of BST.
      * 
      * @return min max levels.
      *         - [0] is min & [1] is max level.
      */
     public int[] findMinMaxLevels() {
        log.info("Finding min max levels of BST.");
        
        if(isEmpty()){
            log.info("min max levels are 0 & 0 respectively.");
            return new int[]{0,0};
        }
         
        Queue<BSTNode> currentLevel = new LinkedList<BSTNode>();
        Queue<BSTNode> nextLevel = new LinkedList<BSTNode>();

        currentLevel.offer(root);
        int currentHeight = 1;

        int[] result = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        while (!currentLevel.isEmpty() || !nextLevel.isEmpty()) {

            if (currentLevel.isEmpty()) {
                currentHeight += 1;
                Queue<BSTNode> tmp = nextLevel;
                nextLevel = currentLevel;
                currentLevel = tmp;
            }

            BSTNode node = currentLevel.poll();
            if (node.getLeft() != null) {
                nextLevel.offer(node.getLeft());
            }
            if (node.getRight() != null) {
                nextLevel.offer(node.getRight());
            }
            if (node.isLeaf()) {
                result[0] = Math.min(result[0], currentHeight);
            }
        }

        result[1] = currentHeight;
        log.info("min max levels are " + result[0] + "&" + result[1] + " respectively.");
        return result;
    }
     
     /**
      * Returns position of element if found.
      * 
      * @param data
      *          - data whose position is to be found.
      * @return {@link JsonObject}
      * @throws NoSuchNodeException 
      */
     public JsonObject getPosition(int data) throws NoSuchNodeException{
        log.info("Finding position ..");
        int pos = inorder().indexOf(data);
        if(pos == -1){
            log.error("No such node with " + data + " exists!");
            throw new NoSuchNodeException(data);
        } 
         
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("inorder", pos + 1);
        jsonObject.addProperty("preorder", preorder().indexOf(data) + 1);
        jsonObject.addProperty("postorder", postorder().indexOf(data) + 1);
        
        log.info("Position of data found.");
        return jsonObject;
     }
     
     /**
      * Function for inorder traversal.
      * 
      * @return {@link List} 
      *           - list of nodes in inorder traversal
      */
     public List inorder()
     {
         List<Integer> output = new ArrayList<>();
         inorder(root, output);
         return output;
     }
     
     /**
      * Recursive function for inorder traversal. 
      * @param r 
      *         - the root of subtree to start traversal.
      * @param output 
      *          - the output
      */
     private void inorder(BSTNode r, List output)
     {
         if (r != null)
         {
             inorder(r.getLeft(), output);
             output.add(r.getData());
             inorder(r.getRight(), output);
         }
     }
     
     /**
      * Function for preorder traversal.
      * 
      * @return {@link List} 
      *           - list of nodes in preorder traversal
      */
     public List preorder()
     {
         List<Integer> output = new ArrayList<>();
         preorder(root, output);
         return output;
     }
     
     /**
      * Recursive function for preorder traversal. 
      * @param r 
      *         - the root of subtree to start traversal.
      * @param output 
      *          - the output
      */
     private void preorder(BSTNode r, List output)
     {
         if (r != null)
         {
             output.add(r.getData());
             preorder(r.getLeft(), output);             
             preorder(r.getRight(), output);
         }
     }
     
     /**
      * Function for postorder traversal.
      * 
      * @return {@link List} 
      *           - list of nodes in postorder traversal
      */
     public List postorder()
     {
         List<Integer> output = new ArrayList<>();
         postorder(root, output);
         return output;
     }
     
     /**
      * Recursive function for postorder traversal. 
      * @param r 
      *         - the root of subtree to start traversal.
      * @param output 
      *          - the output
      */
     private void postorder(BSTNode r, List output)
     {
         if (r != null)
         {
             postorder(r.getLeft(), output);             
             postorder(r.getRight(), output);
             output.add(r.getData());
         }
     }

    /**
     * Prints the binary tree.
     * 
     * @return output
     */ 
    public String print() {
        log.info("Printing binart tree in tree format..");
        PaddedWriter writer = new PaddedWriter();
        printPretty( 1, 0, writer );
        return writer.toString();
    }

    
    /**
     * Pretty formatting of a binary tree to the output string.
     * 
     * @param level
     *          - level in bst. 
     * @param indentSpace
     *          - space to indent.
     * @param out 
     *          - padded writer.
     */
    private void printPretty(int level, int indentSpace, PaddedWriter out) {
        String endHtmlContent = "\n</pre></div>\n</body>\n</html>";
        out.write("<!DOCTYPE html>\n" +"<html>\n"+ "<body>\n"+"<div style=\"color:black;\"><pre>");
        if(isEmpty()){
            out.write("Tree is empty");
            out.write(endHtmlContent);
            return;
        }
        
        int h = findMinMaxLevels()[1]+1;
        int nodesInThisLevel = 1;
        int branchLen = 2*((int)Math.pow( 2.0, h )-1) - (3-level) *(int)Math.pow( 2.0, h-1 );
        int nodeSpaceLen = 2+(level+1)*(int)Math.pow(2.0,h);
        int startLen = branchLen + (3-level) + indentSpace;

        Deque<BSTNode> nodesQueue = new LinkedList<BSTNode>();
        nodesQueue.offerLast( root );
        for (int r = 1; r < h; r++) {
            printBranches( branchLen, nodeSpaceLen, startLen, nodesInThisLevel, nodesQueue, out );
            branchLen = branchLen/2 - 1;
            nodeSpaceLen = nodeSpaceLen/2 + 1;
            startLen = branchLen + (3-level) + indentSpace;
            printNodes(branchLen, nodeSpaceLen, startLen, nodesInThisLevel, nodesQueue, out);

            for (int i = 0; i < nodesInThisLevel; i++) {
                BSTNode currNode = nodesQueue.pollFirst();
                if (currNode!=null) {
                    nodesQueue.offerLast( currNode.getLeft() );
                    nodesQueue.offerLast( currNode.getRight() );
                } else {
                    nodesQueue.offerLast( null );
                    nodesQueue.offerLast( null );
                }
            }
            nodesInThisLevel *= 2;
        }
        printBranches(branchLen, nodeSpaceLen, startLen, nodesInThisLevel, nodesQueue, out);
        printLeaves(indentSpace, level, nodesInThisLevel, nodesQueue, out);
        out.write(endHtmlContent);
    }
    
    /**
     * Print branches.
     * 
     *        6
     * (eg, /   \ )
     * 
     * @param branchLen
     * @param nodeSpaceLen
     * @param startLen
     * @param nodesInThisLevel
     * @param nodesQueue
     * @param out 
     */
    private void printBranches(int branchLen, int nodeSpaceLen, int startLen, int nodesInThisLevel, Deque<BSTNode> nodesQueue, PaddedWriter out) {
        Iterator<BSTNode> iterator = nodesQueue.iterator();
        for (int i = 0; i < nodesInThisLevel/2; i++) {
            if (i == 0) {
                out.setw(startLen-1);
            } else {
                out.setw(nodeSpaceLen-2);
            }
            out.write();
            BSTNode next = iterator.next();
            if (next != null) {
                out.write( "<span style=\"color:blue;\">/</span>" );
            } else {
                out.write(Constants.space);
            }
            out.setw(2*branchLen+2);
            out.write();
            next = iterator.next();
            if (next != null) {
                out.write( "<span style=\"color:red;\">\\</span>" );
            } else {
                out.write( Constants.space );
            }
        }
        out.endl();
    }

    /**
     * Print nodes.
     * (eg, ---14--- )
     * 
     * @param branchLen
     * @param nodeSpaceLen
     * @param startLen
     * @param nodesInThisLevel
     * @param nodesQueue
     * @param out 
     */
    private void printNodes(int branchLen, int nodeSpaceLen, int startLen, int nodesInThisLevel, Deque<BSTNode> nodesQueue, PaddedWriter out) {
        Iterator<BSTNode> iterator = nodesQueue.iterator();
        BSTNode currentNode;
        for (int i = 0 ; i < nodesInThisLevel; i++) {
            currentNode = iterator.next();
            if (i == 0) {
                out.setw(startLen );
            } else {
                out.setw(nodeSpaceLen );
            }
            out.write();
            if (currentNode != null && currentNode.getLeft() != null) {
                out.setfill( Constants.underScore );
            } else {
                out.setfill( Constants.space );
            }
            out.setw( branchLen+2 );
            if (currentNode != null) {
                out.write(""+currentNode.getData());
            } else {
                out.write();
            }
            if (currentNode != null && currentNode.getRight() != null) {
                out.setfill( Constants.underScore );
            } else {
                out.setfill( Constants.space );
            }
            out.setw(branchLen);
            out.write();
            out.setfill(Constants.space);
        }
        out.endl();
    }

    /**
     * Print the leaves only (just for the bottom row).
     * 
     * @param indentSpace
     * @param level
     * @param nodesInThisLevel
     * @param nodesQueue
     * @param out 
     */
    private void printLeaves(int indentSpace, int level, int nodesInThisLevel, Deque<BSTNode> nodesQueue, PaddedWriter out) {
        Iterator<BSTNode> iterator = nodesQueue.iterator();
        BSTNode currentNode;
        for (int i = 0; i < nodesInThisLevel; i++) {
            currentNode = iterator.next();
            if (i == 0) {
                out.setw(indentSpace+2);
            } else {
                out.setw(2*level+2);
            }
            if (currentNode !=null) {
                out.write(""+currentNode.getData());
            } else {
                out.write();
            }
        }
        out.endl();
    }

 }
