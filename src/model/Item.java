/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item{
    public StringProperty id;
    public StringProperty display;
    public StringProperty quantity;
    public StringProperty ptotal;
    public String stock;
 
    public Item(String id,String display,String quantity,String ptotal,String stock) {
        this.id = new SimpleStringProperty(id);
        this.ptotal = new SimpleStringProperty(ptotal);
        this.quantity = new SimpleStringProperty(quantity) ;               
        this.display = new SimpleStringProperty(display) ;               
        this.stock = new String(stock) ;               
    }
}