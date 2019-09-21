/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class BillsController implements Initializable {

    Database db=Database.getInstance();
    @FXML
    private JFXComboBox<String> billno;
    @FXML
    private JFXComboBox<String> customer_name;
    @FXML
    private JFXComboBox<String> date;
    @FXML
    private StackPane stack;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try{
           ResultSet rs=db.executeQuery("select distinct billno from log where out='---'");
           while(rs.next()){
               billno.getItems().add(rs.getString(1));
           }
            rs=db.executeQuery("select distinct buyer_name from log where out not in('---')");
           while(rs.next()){
               customer_name.getItems().add(rs.getString(1));
           }
       }catch(Exception e){System.out.println(e);}
           customer_name.valueProperty().addListener(event->{
            try{       
                date.getItems().clear();
              ResultSet rs=db.executeQuery("select distinct date from log where buyer_name='"+customer_name.getValue()+"'");
              while(rs.next()){
              date.getItems().add(rs.getString(1));
              }   
              }catch(Exception e){System.out.println(e);}           
           });
       
    }    

    @FXML
    private void back(ActionEvent event) {
         try {
            StackPane pane=FXMLLoader.load(getClass().getResource("Main.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void in_bill(ActionEvent event) {
        try{
            int i=0;
            float total=0.0f;
            String date="",name="";
            ResultSet rs=null;
            rs=db.executeQuery("select count(*) from log where billno='"+billno.getValue()+"'");
            rs.next();
            int count=rs.getInt(1);
            System.out.println("Count is "+count);
            String data[][]=new String[count][6];
            rs=db.executeQuery("select * from log where billno='"+billno.getValue()+"'");// here buyer_name field is common for both i.e. in, out.
            //in case IN it holds retailer name and in case OUT it holds customer name
            while(rs.next()){
                 if(i==0){
                date=rs.getString("date");
                name=rs.getString("buyer_name");                
                }
                data[i][0]=rs.getString("in");
                data[i][1]=rs.getString("id");               
                data[i][2]="";
                data[i][3]="---";
                data[i][4]=rs.getString("bill");
                total+=Float.parseFloat(data[i][4]);
                i++;                 
                if(i==24){  //because 1 bill has 23 rows only
                    BillModelController.setValues(billno.getValue(), date, name, ""+total, "in", data);
                    try {
                        StackPane sp=FXMLLoader.load(getClass().getResource("BillModel.fxml"));
                        Dialog.loadDialogPane(stack,sp ,BillModelController.close, JFXDialog.DialogTransition.CENTER);
                        i=0;
                        data=new String[count-23][6];
                    } catch (IOException ex) {
                    Logger.getLogger(BillsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   }
            }
                 BillModelController.setValues(billno.getValue(), date, name, ""+total, "in", data);
                    try {
                        StackPane sp=FXMLLoader.load(getClass().getResource("BillModel.fxml"));
                        Dialog.loadDialogPane(stack,sp ,BillModelController.close, JFXDialog.DialogTransition.CENTER);                      
                    } catch (IOException ex) {
                    Logger.getLogger(BillsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
           
        }catch(Exception e){
           Logger.getLogger(BillsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void out_bill(ActionEvent event) {
        try{
            int i=0;
            float total=0.0f;
            String date1="",name="";
            ResultSet rs=null;
            rs=db.executeQuery("select count(*) from log where buyer_name='"+customer_name.getValue()+"' and date='"+date.getValue()+"'");
            rs.next();
            int count=rs.getInt(1);
            System.out.println("Count is "+count);
            String data[][]=new String[count][6];
            rs=db.executeQuery("select * from log where buyer_name='"+customer_name.getValue()+"' and date='"+date.getValue()+"'");// here buyer_name field is common for both i.e. in, out.
            //in case IN it holds retailer name and in case OUT it holds customer name
            while(rs.next()){
                 if(i==0){
                date1=rs.getString("date");
                name=rs.getString("buyer_name");                
                }
                data[i][0]=rs.getString("out");
                data[i][1]=rs.getString("id");               
                data[i][2]="";
                data[i][3]=rs.getString("rate");
                data[i][4]=rs.getString("bill");
                total+=Float.parseFloat(data[i][4]);
                i++;                 
                if(i==24){  //because 1 bill has 23 rows only
                    BillModelController.setValues("---", date1, name, ""+total, "out", data);
                    try {
                        StackPane sp=FXMLLoader.load(getClass().getResource("BillModel.fxml"));
                        Dialog.loadDialogPane(stack,sp ,BillModelController.close, JFXDialog.DialogTransition.CENTER);
                        i=0;
                        data=new String[count-23][6];
                    } catch (IOException ex) {
                    Logger.getLogger(BillsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   }
            }
                 BillModelController.setValues("---", date1, name, ""+total, "out", data);
                    try {
                        StackPane sp=FXMLLoader.load(getClass().getResource("BillModel.fxml"));
                        Dialog.loadDialogPane(stack,sp ,BillModelController.close, JFXDialog.DialogTransition.CENTER);                      
                    } catch (IOException ex) {
                    Logger.getLogger(BillsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
           
        }catch(Exception e){
           Logger.getLogger(BillsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
