/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bst.service.model;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Input for BST add/search rest services. 
 * 
 * @author asifaham
 */
@Getter
@Setter
@NoArgsConstructor
public class Input {
    
    /**
     * Data to add/search
     */
    private Integer data;
    
    /**
     * Convert from json to input.
     * 
     * @param json
     * @return {@link Input}
     */
    public Input fromJson(String json){
        return new Gson().fromJson(json, Input.class);
    }
    
    /**
     * Convert from input to json.
     * 
     * @param input
     * @return jsonString
     */
    public String toJson(Input input){
        return new Gson().toJson(input, Input.class);
    }
}
