

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author ECS-III
 */           
public class Stock extends Application {
    
    static Stage stage;
    static StackPane root;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Stock.fxml"));        
         Stock.root=(StackPane)root;
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
