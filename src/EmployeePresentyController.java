/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Vishal
 */
public class EmployeePresentyController implements Initializable {

    public static JFXButton close;
    Database db=Database.getInstance();
    JFXCheckBox check[];
    int count=0;
    @FXML
    private GridPane scroll;
    @FXML
    private JFXButton closebtn;
    @FXML
    private Label date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        close=closebtn;
        date.setText(""+LocalDate.now());
        int idx=0;
        try{
        ResultSet rs=db.executeQuery("select count(*) from employee where status=1");
        rs.next();
        count=rs.getInt(1);
        check=new JFXCheckBox[count];
        rs=db.executeQuery("select * from employee where status=1");
         while(rs.next()){
            check[idx]=new JFXCheckBox(rs.getString("name")); 
            scroll.addRow(idx,check[idx]);
            idx++;
         }
        }catch(Exception e){
            Util.notify("Exception", e.getMessage(), Util.Notification.error);    
        }
        
    }    

    @FXML
    private void done(ActionEvent event) {
        for(int i=0;i<count;i++){
            db.execute("insert into presenty values("
                    + "'"+ check[i].getText() +"',"
                    + "'"+ date.getText() +"',"
                    + check[i].isSelected()
                    + ")");
        }
          Dialog.dialog.close();
          Util.notify("Presenty updated", "Presenty Master Updated ", Util.Notification.success);    
    }
    
}
