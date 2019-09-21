/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;
/**
 * FXML Controller class
 *
 * @author sai
 */
public class AddEmployeeController implements Initializable {

    Database db=Database.getInstance();   
    @FXML
    private JFXTextField name;
    @FXML
    private StackPane stack;
    @FXML
    private JFXTextField branch;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextArea addr;
    @FXML
    private JFXTextField salary;
    @FXML
    private JFXToggleButton status;
    @FXML
    private JFXTextField id;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void back(ActionEvent event) {
        try {
            StackPane pane=FXMLLoader.load(getClass().getResource("Main.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void reset() {
        try {
            StackPane pane=FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @FXML
    private void add(ActionEvent event) {
          if(
            db.executeReturnException("insert into employee(name,branch,addr,phone,salary,status) values("
                    +"'"+name.getText()+"',"
                    +"'"+branch.getText()+"',"
                    +"'"+addr.getText()+"',"
                    +"'"+phone.getText()+"',"
                    + salary.getText()+","
                    +""+status.isSelected()+""
                    + ")")
            ){
            Util.notify("employee Added", name.getText(), Util.Notification.success);
            reset();
          }else
            Dialog.loadDialog(stack, new Text("Exception"),new Text(Database.exception));              
    }

    @FXML
    private void delete(ActionEvent event) {
        try{
         if(id.getText().equals("")){
             Util.notify("employee Delete failure", "Please enter employee ID", Util.Notification.error);              
         }else{
             db.execute("delete from employee where id="+id.getText());
             Util.notify("Deletion success", "employee Deleted", Util.Notification.success);              
         }
        }catch(Exception e){
             Dialog.loadDialog(stack, new Text("Exception"),new Text(""+e.getMessage()));
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if(
            db.executeReturnException("update employee set"                    
                    +" name='"+name.getText()+"',"
                    +" addr='"+addr.getText()+"',"
                    +" phone='"+phone.getText()+"',"                    
                    +" branch='"+branch.getText()+"',"
                    +" salary="+salary.getText()+","
                    +" status="+status.isSelected()+""
                    + " where id="+id.getText())
            ){
            Util.notify("Updation success", id.getText()+" "+name.getText(), Util.Notification.success);
            reset();
          }else
           Dialog.loadDialog(stack, new Text("Exception"),new Text(Database.exception));                     
    }

    @FXML
    private void view(ActionEvent event) {
         try{
            ResultSet rs=db.executeQuery("select * from employee where id="+id.getText());
            rs.next();
            name.setText(rs.getString("name"));
            branch.setText(rs.getString("branch"));
            addr.setText(rs.getString("addr"));
            phone.setText(rs.getString("phone"));
            salary.setText(rs.getString("salary"));
            status.setSelected(rs.getBoolean("status"));
         }catch(Exception e){
             Dialog.loadDialog(stack, new Text("Exception"),new Text(""+e.getMessage()));
        }
    }
}
