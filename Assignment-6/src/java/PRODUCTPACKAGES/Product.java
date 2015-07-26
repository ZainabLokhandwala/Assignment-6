/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PRODUCTPACKAGES;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Zainab
 */

public class Product {

    private int Pro_ID;
    private String Pro_name;
    private String Pro_Description;
    private int Pro_Quantity;

    public Product() {
    }

    public Product(int Pro_ID, String Pro_name, String Pro_Description, int Pro_Quantity) {
        this.Pro_ID = Pro_ID;
        this.Pro_name = Pro_name;
        this.Pro_Description = Pro_Description;
        this.Pro_Quantity = Pro_Quantity;
    }

    public Product(JsonObject json) {
        this.Pro_ID = json.getInt("Pro_ID");
        this.Pro_name = json.getString("name");
        this. Pro_Description= json.getString("Pro_Description");
        this.Pro_Quantity = json.getInt("Pro_Quantity");
    }
    
    public JsonObject toJSON(){
        return Json.createObjectBuilder().add("Pro_ID", Pro_ID)
                .add("Pro_name", Pro_name)
                .add("Pro_Description", Pro_Description)
                .add("Pro_Quantity", Pro_Quantity).build();
    }

    public int getPro_ID() {
        return Pro_ID ;
    }

    public void setPro_ID(int Pro_ID) {
        this.Pro_ID = Pro_ID;
    }

    public String getPro_name() {
        return Pro_name;
    }

    public void setPro_name(String Pro_name) {
        this.Pro_name = Pro_name;
    }

    public String getPro_Description() {
        return Pro_Description;
    }

    public void setPro_Description(String Pro_Description) {
        this.Pro_Description = Pro_Description;
    }

    public int getPro_Quantity() {
        return Pro_Quantity;
    }

    public void setPro_Quantity(int Pro_Quantity) {
        this.Pro_Quantity = Pro_Quantity;
    }

}

    

