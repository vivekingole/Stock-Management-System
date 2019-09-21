/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer extends RecursiveTreeObject<Customer>{
    public StringProperty fname;
    public StringProperty lname;
    public StringProperty phone;
    public StringProperty addr;
    public StringProperty pending;
 
    public Customer(String fname, String lname,String phone,String addr,String pending) {
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.phone = new SimpleStringProperty(phone) ;
        this.addr = new SimpleStringProperty(addr) ;               
        this.pending = new SimpleStringProperty(pending) ;               
    }
}