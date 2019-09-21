



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author ECS-III
 */           
public class BillModel extends Application {
    
    static Stage stage;
    static AnchorPane root;
    @Override
    public void start(Stage stage) throws Exception {
        String data[][]={
            {"1","TL-3301","c4Wx40","100","100"},  
            {"2","TL-3301","c4Wx40","200","200"},  
            {"3","TL-3301","c4Wx40","300","300"}  
        };
        
        BillModelController.setValues("BILL001", "1-2-2019", "Vivek Ingole", "1500", "in", data);
        Parent root = FXMLLoader.load(getClass().getResource("BillModel.fxml"));        
        
        Scene scene = new Scene(root);        
        stage.setScene(scene);
       // stage.setIconified(true);
        stage.centerOnScreen();     
        stage.show();
        stage=stage;       
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
