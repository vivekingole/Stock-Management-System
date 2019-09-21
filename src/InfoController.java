/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import home.resource.animatefx.animation.RollIn;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author SATISH
 */
public class InfoController implements Initializable {

    public static JFXButton close;
    public static JFXButton l1,l2,l3,l4,l5;
    @FXML
    private Circle img1;
    @FXML
    private Hyperlink sitelink;
    @FXML
    private JFXButton link1;
    @FXML
    private JFXButton link2;
    @FXML
    private JFXButton link3;
    @FXML
    private JFXButton link4;
    @FXML
    private JFXButton link5;
    @FXML
    private JFXButton closebtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         close=closebtn;
         l1=link1;
         l2=link2;
         l3=link3;
         l4=link4;
         l5=link5;
         img1.setFill(new ImagePattern(new Image("profile.jpg")));                
    }

    @FXML
    private void visiteSite(ActionEvent event) {       
         Runnable newtask=null;
         newtask = () -> {       
          Util.visiteURL("https://coolitknowledge.blogspot.com");
         };
          Thread newthread = new Thread(newtask, "BLOGSITE-VISIT");
          newthread.start();
    }
    @FXML
    private void visiteSocialSite(ActionEvent event) {
        Runnable newtask=null;
        JFXButton src=(JFXButton)event.getSource();
        if(src==link1)
            newtask = () -> {       
                Util.visiteURL("https://www.facebook.com/vivek.ingole.3958?ref=bookmarks");
            };
        else if(src==link2)
            newtask = () -> {       
                Util.visiteURL("https://www.linkedin.com/in/vivek-ingole-965378167/");
            };
        else if(src==link3)
            newtask = () -> {       
                Util.visiteURL("https://www.instagram.com/vicky_ingole_");
            };
        else if(src==link4)
            newtask = () -> {       
                Util.visiteURL("https://twitter.com/vivekingole10");
            };
        else if(src==link5)
            newtask = () -> {       
                Util.visiteURL("https://in.pinterest.com/vivekingole13");
            };  
             Thread newthread = new Thread(newtask, "SOCIAL-SITE-VISIT");
              newthread.start();
    }
    
    public static void runAnimation1(){
        l1.setOpacity(0);
        l2.setOpacity(0);
        l3.setOpacity(0);
        l4.setOpacity(0);
        l5.setOpacity(0);
       
        RollIn r1=new RollIn(l1);
        RollIn r2=new RollIn(l2);
        RollIn r3=new RollIn(l3);
        RollIn r4=new RollIn(l4);
        RollIn r5=new RollIn(l5);
        r1.setDelay(Duration.millis(1000));
        r2.setDelay(Duration.millis(1300));
        r3.setDelay(Duration.millis(1600));
        r4.setDelay(Duration.millis(1900));
        r5.setDelay(Duration.millis(2100));
        r1.play();
        r2.play();
        r3.play();
        r4.play();
        r5.play();
    }
  
}
