/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.defaults;

/**
 * PaddedWriter used for printing {@link BSTree} in tree format.
 * 
 * @author asifaham
 */
public class PaddedWriter {
    
    /**
     * Width of chars to be padded. 
     */
    private int width = 0;
    
    /**
     * Chars used for padding.
     */
    private String fillChars = " ";
    
    /**
     * Stores output.
     */
    private final StringBuilder builder = new StringBuilder();
    
    /**
     * Set width.
     * 
     * @param width 
     */
    void setw(int width) {
        this.width = width;
    }
    
    /**
     * Set fill chars.
     * 
     * @param fillChars 
     */
    void setfill(String fillChars) {
        this.fillChars = fillChars;
    }
    
    /**
     * Write padded value.
     * 
     * @param str 
     *          - value to write 
     */
    void write(String str) {
        write(str.toCharArray());
    }
    
    /**
     * Write padded value.
     * 
     * @param buf 
     *          - value to write 
     */
    void write(char[] buf) {
        if (buf.length < width) {
            for(int i =width - buf.length ; i > 0; i--){
                builder.append(fillChars);
            }
        }
        builder.append(buf);
        setw(0);
    }
    
    /**
     * Write only padding.
     */
    void write() {
        for(int i =width ; i > 0; i--){
            builder.append(fillChars);
        }
        setw(0);
    }
    
    /**
     * End Line.
     */
    void endl() {
        builder.append("\n");
        setw(0);
    }
    
    @Override
    public String toString(){
        return builder.toString();
    }
}
