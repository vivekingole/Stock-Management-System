



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import home.resource.animatefx.animation.Pulse;
import home.resource.animatefx.animation.ZoomIn;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.JOptionPane;
public class AdminLoginController implements Initializable {

    public static int registror=0;
    public static Circle image;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField pass;     
    Database db=Database.getInstance();
    @FXML
    private JFXButton btn;
   @FXML
    private StackPane stack;
    @FXML
    private Circle img;
    @FXML
    private Circle c2;
    @FXML
    private Circle c1;
    @FXML
    private JFXButton info;
    @Override    
    public void initialize(URL url, ResourceBundle rb) {          
        image=img;     
        Pulse infoanimation=new Pulse(info);
            infoanimation.setCycleCount(-1);
             infoanimation.play();
            ZoomIn z1=new ZoomIn(c1);
            z1.setCycleCount(-1);
            z1.setCycleDuration(200);
            z1.play();
            ZoomIn z2=new ZoomIn(c2);
            z2.setCycleCount(-1);
            z2.setDelay(Duration.millis(200));
            z2.play();
            
       try {
            ResultSet rs=db.executeQuery("select * from admin");
            rs.next();
            rs.getString(1);            
            registror=1;
            img.setFill(new ImagePattern(new Image(new File("img\\user.jpg").toURI().toString())));  
        } catch (Exception ex) {            
            img.setFill(new ImagePattern(new Image(new File("img\\user.jpg").toURI().toString())));  
            registror=0;
        }
         
    }    

    @FXML
    private void login(ActionEvent event) {      
        try {                  
            String username=user.getText();                       
            String password=pass.getText();                        
            if(username.equals("")||password.equals("")){
                    Dialog.loadDialog(stack, new Text("Login Failed!"),new Text("Sorry!\n\nPlease enter correct login credential."));
            }else{                            
            ResultSet rs=db.executeQuery("select * from admin");
            rs.next();
               if(username.equals(rs.getString("name"))&&password.equals(rs.getString("password"))){
                  StackPane pane=FXMLLoader.load(getClass().getResource("Main.fxml"));
                    AdminLogin.root.getChildren().setAll(pane);  
                 }else{
                    Dialog.loadDialog(stack, new Text("Login Failed!"),new Text("Sorry!\n\nPlease enter correct login credential."));
               }               
            }                                          
            } catch (Exception e) {
                Util.notify("Exception", e.getMessage(), Util.Notification.error);            
            }      
    }

    @FXML
    private void info(ActionEvent event) {
        try {
            StackPane sp=FXMLLoader.load(getClass().getResource("Info.fxml"));
            Dialog.loadDialogPane(stack,sp ,InfoController.close, JFXDialog.DialogTransition.CENTER);
            InfoController.runAnimation1();
        } catch (IOException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loginDetails(ActionEvent event) {
        try {
                StackPane sp=FXMLLoader.load(getClass().getResource("AdminRegistration.fxml"));
                Dialog.loadDialogPane(stack,sp ,AdminRegistrationController.close, JFXDialog.DialogTransition.CENTER);                
            } catch (Exception e) {
                Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, e);
            }
    }
   
}
