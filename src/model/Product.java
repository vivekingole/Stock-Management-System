/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Product extends RecursiveTreeObject<Product>{
    public StringProperty id;
    public StringProperty name;
    public StringProperty section;
    public StringProperty quantity;
    public StringProperty rate;
    public StringProperty display;
    
    
    public Product(String id, String name,String section,String quantity,String rate,String display) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.section = new SimpleStringProperty(section) ;
        this.quantity = new SimpleStringProperty(quantity) ;               
        this.rate = new SimpleStringProperty(rate) ;               
        this.display = new SimpleStringProperty(display) ;   
    }
}