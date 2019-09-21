/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author sai
 */
public class MainController implements Initializable {

    @FXML
    private StackPane stack;
    @FXML
    private Label title;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String str=(String)Util.getFirstRowData("admin", "title", 2);
        title.setText(str);
    }    

    @FXML
    private void History(ActionEvent event) {
        try {
            StackPane pane=FXMLLoader.load(getClass().getResource("/Log.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Log_Out(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void Add_Product(ActionEvent event) {
          try {
            StackPane pane=FXMLLoader.load(getClass().getResource("/AddProduct.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Stock(ActionEvent event) {
         try {
            StackPane pane=FXMLLoader.load(getClass().getResource("/Stock.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Bill(ActionEvent event) {
         try {
            StackPane pane=FXMLLoader.load(getClass().getResource("/Bill.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void billHistory(ActionEvent event) {
          try {
            StackPane pane=FXMLLoader.load(getClass().getResource("/BillHistory.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void bills(ActionEvent event) {
        try {
            StackPane pane=FXMLLoader.load(getClass().getResource("/Bills.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Add_employee(ActionEvent event) {try {
            StackPane pane=FXMLLoader.load(getClass().getResource("/AddEmployee.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    private void emplpoyeePresenty(ActionEvent event) {
        try {
            StackPane sp=FXMLLoader.load(getClass().getResource("EmployeePresenty.fxml"));
            Dialog.loadDialogPane(stack,sp ,EmployeePresentyController.close, JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
