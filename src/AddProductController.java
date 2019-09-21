/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;
/**
 * FXML Controller class
 *
 * @author sai
 */
public class AddProductController implements Initializable {

    Database db=Database.getInstance();   
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField count;
    @FXML
    private StackPane stack;
    @FXML
    private JFXTextField section;
    @FXML
    private JFXTextField rate;
    @FXML
    private JFXTextField name1;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(id,Util.getColumn("product", "id"));
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
   
    public void reset() {
        try {
            StackPane pane=FXMLLoader.load(getClass().getResource("AddProduct.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void add_product(ActionEvent event) {
        try{
        if(Validator.checkRequiredValidator(id,name,count,section,rate)){
          if(
            db.execute("insert into product values("
                    +id.getText()+","
                    +"'"+name.getText()+"',"
                    +"'"+section.getText()+"',"
                    +count.getText()+","                    
                    + rate.getText()+","
                    +"'"+name1.getText()+"'"
                    + ")")
            ){
            Util.notify("Product Added", id.getText()+" "+name.getText()+" "+count.getText(), Util.Notification.success);
            reset();
          }else
            Util.notify("product adding failure", "ID cannot be repeated", Util.Notification.error);              
        }else{
            Util.notify("product adding failure", "Please fill all information", Util.Notification.error);              
        }
        }catch(Exception e){
             Dialog.loadDialog(stack, new Text("Exception"),new Text(""+e.getMessage()));
        }
    }

    @FXML
    private void delete_product(ActionEvent event) {
        try{
         if(id.getText().equals("")){
             Util.notify("product Delete failure", "Please enter Product ID", Util.Notification.error);              
         }else{
             db.execute("delete from product where id='"+id.getText()+"'");
             Util.notify("Deletion success", "Product Deleted", Util.Notification.success);              
         }
        }catch(Exception e){
             Dialog.loadDialog(stack, new Text("Exception"),new Text(""+e.getMessage()));
        }
         
    }

    @FXML
    private void update_product(ActionEvent event) {
        try{
        if(
            db.execute("update product set"                    
                    +" name='"+name.getText()+"',"
                    +" section='"+section.getText()+"',"
                    +" quantity="+count.getText()+","                    
                    +" rate="+rate.getText()+","
                    +" display_name='"+name1.getText()+"'"
                    + "")
            ){
            Util.notify("Updation success", id.getText()+" "+name.getText()+" "+count.getText()+" "+rate.getText(), Util.Notification.success);
            reset();
          }else
            Util.notify("Updation failure", "failure while updatating product", Util.Notification.error);                      
        }catch(Exception e){
             Dialog.loadDialog(stack, new Text("Exception"),new Text(""+e.getMessage()));
        }
    }

    @FXML
    private void view_product(ActionEvent event) {
        try{
            ResultSet rs=db.executeQuery("select * from product where id='"+id.getText()+"'");
            rs.next();
            name.setText(rs.getString("name"));
            name1.setText(rs.getString("display_name"));
            section.setText(rs.getString("section"));
            rate.setText(""+rs.getInt("rate"));
            count.setText(""+rs.getInt("quantity"));            
        }catch(Exception e){
             Dialog.loadDialog(stack, new Text("Exception"),new Text(""+e.getMessage()));
        }
                
    }   
}
