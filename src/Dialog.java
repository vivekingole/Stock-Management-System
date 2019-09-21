

    
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class Dialog {
    public static JFXTextField tf;
    public static JFXTextField tf1;
    public static JFXTextField tf2;
    public static JFXDialog dialog;
    
    public static void loadDialogPane(StackPane sp,Node body,JFXButton close,JFXDialog.DialogTransition transition){                          
             close.setButtonType(JFXButton.ButtonType.RAISED);
             JFXDialogLayout layout=new JFXDialogLayout();
             layout.setBody((StackPane)body);                  
             JFXDialog dialog=new JFXDialog(sp, layout,transition);   
             Dialog.dialog=dialog;
             close.setOnAction(e->dialog.close());
               dialog.show();
    }
   
    public static void loadDialog(StackPane sp,Node heading,Node body){
             JFXButton button=new JFXButton("Ok");
             button.setPrefSize(100,40);             
             button.setButtonType(JFXButton.ButtonType.RAISED);
             JFXDialogLayout layout=new JFXDialogLayout();
             layout.setHeading(heading);
             layout.setBody(body);           
             JFXDialog dialog=new JFXDialog(sp, layout,JFXDialog.DialogTransition.CENTER);             
             button.setOnAction(e->dialog.close());
             layout.setActions(button);
               dialog.show();
    }
    
     public static void loadDialog(StackPane sp,Node heading,Node body,String buttonname,Double btnwidth,Double btnheight,JFXDialog.DialogTransition transition,EventHandler event){
             JFXButton button=new JFXButton(buttonname);
             button.setPrefSize(btnwidth,btnheight);             
             button.setButtonType(JFXButton.ButtonType.RAISED);
             JFXDialogLayout layout=new JFXDialogLayout();
             layout.setHeading(heading);
             layout.setBody(body);           
             JFXDialog dialog=new JFXDialog(sp, layout,transition);             
             button.setOnAction(event);
             layout.setActions(button);
               dialog.show();
    }
     public static void loadInputDialog(StackPane sp,Node heading,String buttonname,EventHandler handler){                     
             JFXButton button=new JFXButton(buttonname);
             tf=new JFXTextField();
             tf.setPromptText("Enter value");
             tf.setPrefSize(100, 50);
             button.setMinSize(100,40);  
             button.setButtonType(JFXButton.ButtonType.RAISED);
             JFXDialogLayout layout=new JFXDialogLayout();
             layout.setHeading(heading);
             layout.setBody(tf);
             layout.setMaxSize(300,200);
             dialog=new JFXDialog(sp, layout,JFXDialog.DialogTransition.CENTER);             
             layout.setActions(button);
             button.setOnAction(handler);    
             tf.setText("");
             tf1.setText("");
             dialog.show();
             
    }
     public static void InDialog(StackPane sp,Node heading,String buttonname,EventHandler handler){                     
             JFXButton button=new JFXButton(buttonname);
             tf=new JFXTextField();
             tf.setPromptText("Enter Quantity");
             tf.setPrefSize(100, 50);
             tf1=new JFXTextField();
             tf1.setPromptText("Amount");
             tf1.setPrefSize(100, 50);
             HBox box=new HBox(tf,tf1);
             box.setSpacing(20);
             button.setMinSize(100,40);  
             button.setButtonType(JFXButton.ButtonType.RAISED);
             JFXDialogLayout layout=new JFXDialogLayout();
             layout.setHeading(heading);
             layout.setBody(box);
             layout.setMaxSize(300,200);
             dialog=new JFXDialog(sp, layout,JFXDialog.DialogTransition.CENTER);             
             layout.setActions(button);
             button.setOnAction(handler);    
             tf.setText("");
             tf1.setText("");
             dialog.show();
             
    }
    public static void loadInputDialog(StackPane sp,Node heading,String buttonname,EventHandler handler,String prompt1,String prompt2,String prompt3){                     
             JFXButton button=new JFXButton(buttonname);
             tf=new JFXTextField();
             tf1=new JFXTextField();
             tf2=new JFXTextField();
             JFXComboBox<String> box=new JFXComboBox<>();  
             box.valueProperty().bindBidirectional(tf.textProperty());
             box.getItems().setAll(Util.getColumn("bill", "c_name"));
             box.setEditable(true);           
             tf.setPromptText(prompt1);
             tf1.setPromptText(prompt2);
             tf2.setPromptText(prompt3);
             box.setPromptText("Customers");
             tf.setPrefSize(150, 50);
             tf1.setPrefSize(150, 50);
             tf2.setPrefSize(150, 50);
             box.setPrefSize(250, 50);
             HBox hbox=new HBox(box,tf1,tf2);
             hbox.setSpacing(10);
             button.setMinSize(100,40);             
             button.setButtonType(JFXButton.ButtonType.RAISED);
             JFXDialogLayout layout=new JFXDialogLayout();
             layout.setHeading(heading);
             layout.setBody(hbox);
             layout.setPrefSize(600,250);
             dialog=new JFXDialog(sp, layout,JFXDialog.DialogTransition.CENTER);             
             layout.setActions(button);
             button.setOnAction(handler);             
             dialog.show();
             
    } 
}
