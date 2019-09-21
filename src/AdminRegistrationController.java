


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;

public class AdminRegistrationController implements Initializable {

    public static JFXButton close;
    String path1,path2;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXTextField pass1;
    @FXML
    private StackPane sp;
     Database db=Database.getInstance();
     @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton closebtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        close=closebtn;
         user.setText((String)Util.getFirstRowData("admin", "name", 2));
         pass1.setText((String)Util.getFirstRowData("admin", "password", 2));
    }    

    @FXML
    private void register(ActionEvent event) {              
            db.execute("update admin set"
                   + " name='"+user.getText()+"',"                        
                   + " password='"+pass1.getText()+"'"                   
                   + "");              
            AdminLoginController.registror=1;
            Util.notify("Successful", "Updation Successful !", Util.Notification.success);
    }    
}
