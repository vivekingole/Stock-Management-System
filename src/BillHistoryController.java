
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.resource.animatefx.animation.FadeInRight;
import home.resource.animatefx.animation.FadeOutRight;
import home.resource.animatefx.animation.SlideInLeft;
import home.resource.animatefx.animation.SlideOutLeft;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class BillHistoryController implements Initializable,EventHandler<ActionEvent> {
    int flag=0;
    String selectedid,selectedpaid;
    JFXTreeTableView<BillingHistory> treeview;
    JFXDialog dialog;
    ObservableList<BillingHistory> list=FXCollections.observableArrayList();
    Database db=Database.getInstance();
    @FXML
    private StackPane stack;
    @FXML
    private AnchorPane anchor;
   @FXML
    private AnchorPane anchor1;
    @FXML
    private JFXTextField searchtf;    
    @FXML
    private Label searchlbl;
    @FXML
    private Label size;
    private int time;
    private int paid;
    private int date;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    searchtf.setOpacity(0);
   searchtf.focusedProperty().addListener(event->{
         if(!searchtf.isFocused())closeTF();
             });
       
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
     initTable();        
     }  
    public void loadData(){       
        try{
            list.clear();
            ResultSet rs=db.executeQuery("select * from bill_history");            
            while(rs.next()){
                list.add(new BillingHistory(rs.getString("b_name"),
                                       rs.getString("b_date"),
                                       rs.getString("b_time"),
                                       rs.getString("b_payment")                                       
                ));
            }
        } catch (Exception ex) {
            System.out.println("####Exception at load data");
          //  Logger.getLogger(BillingHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    public void initTable(){             
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ creating column ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
        
          JFXTreeTableColumn<BillingHistory, String> idcol = new JFXTreeTableColumn<>("Customer Name");
        idcol.setPrefWidth(150);
        idcol.setCellValueFactory((param) ->{
        if(idcol.validateValue(param)) return param.getValue().getValue().id;
        else return idcol.getComputedValue(param);
        });
        
                
        JFXTreeTableColumn<BillingHistory, String> paidcol = new JFXTreeTableColumn<>("Date");
        paidcol.setPrefWidth(150);
        paidcol.setCellValueFactory((param) ->{
        if(paidcol.validateValue(param)) return param.getValue().getValue().date;
        else return paidcol.getComputedValue(param);
        });

        JFXTreeTableColumn<BillingHistory, String> datecol = new JFXTreeTableColumn<>("Time");
        datecol.setPrefWidth(150);
        datecol.setCellValueFactory((param) ->{
        if(datecol.validateValue(param)) return param.getValue().getValue().time;
        else return datecol.getComputedValue(param);
        });
 
          JFXTreeTableColumn<BillingHistory, String> timecol = new JFXTreeTableColumn<>("Payment");
        timecol.setPrefWidth(150);
        timecol.setCellValueFactory((param) ->{
        if(timecol.validateValue(param)) return param.getValue().getValue().paid;
        else return timecol.getComputedValue(param);
        });               

   
        loadData();
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ building table ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    
        final TreeItem<BillingHistory> root = new RecursiveTreeItem<BillingHistory>(list, RecursiveTreeObject::getChildren);         
        treeview = new JFXTreeTableView<BillingHistory>(root);
        treeview.setShowRoot(false);
        treeview.setEditable(true);
        treeview.getColumns().setAll(idcol,timecol,paidcol,datecol); 
        searchtf.textProperty().addListener((o,oldVal,newVal)->{
        treeview.setPredicate(user -> user.getValue().id.get().contains(newVal)
                || user.getValue().date.get().contains(newVal)
                || user.getValue().paid.get().contains(newVal)               
               );               
        });
 
        size.textProperty().bind(Bindings.createStringBinding(()->"Total Records "+treeview.getCurrentItemsCount(),
        treeview.currentItemsCountProperty()));

                treeview.setPrefSize(anchor1.getPrefWidth(), anchor1.getPrefHeight());
                datecol.setPrefWidth(anchor1.getPrefWidth()/4);
                idcol.setPrefWidth(anchor1.getPrefWidth()/4);
                paidcol.setPrefWidth(anchor1.getPrefWidth()/4);               
                timecol.setPrefWidth(anchor1.getPrefWidth()/4);               
                anchor1.getChildren().setAll(treeview);                                   
    }
       
    //~~~~~~~~~~~~~~~~~~~~~~~~~~ search animation ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    @FXML
    private void searchExit(MouseEvent event) {
        closeTF();
    }
    public void closeTF() {
        new SlideInLeft(searchlbl).play();
        new FadeOutRight(searchtf).play();
        flag=0;        
    }
    @FXML
    private void searchEnter(MouseEvent event) {
        if(flag==0){
        searchtf.requestFocus();
        searchtf.clear();
        new SlideOutLeft(searchlbl).play();
        new FadeInRight(searchtf).play();
        flag=1;        
        }        
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Operation on table~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
   
      @FXML
    void delete(ActionEvent event) {
        try{
        if(!treeview.getSelectionModel().isEmpty()){
        BillingHistory p=treeview.getSelectionModel().getSelectedItem().getValue();
        selectedid=p.id.getValue();
        Dialog.loadInputDialog(stack, new Text("Enter Confirmation Password :"), "delete", this);
        }else{
             Util.notify("Invalid Operation","First Select the row",Util.Notification.error);                
        }
        }catch(Exception e){ 
             Util.notify("Invalid Operation","First Select the row",Util.Notification.error);                
        }
    }
   

    @FXML
    void back(ActionEvent event) {
           try {
            StackPane pane=FXMLLoader.load(getClass().getResource("Main.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
               System.out.println(ex.getMessage());
        }
    }
    
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Event Handler~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    
    @Override   ////// Eventhandler 
    public void handle(ActionEvent event) {  
        JFXButton src=(JFXButton)event.getSource();
        
        if(src.getText().equals("delete")){
         String pass1=Dialog.tf.getText();
         String pass2=(String)Util.getFirstRowData("admin", "password2", 2);
         if(pass1.equals(pass2)){
           db.execute("delete from bill_history where b_name='"+selectedid+"'");
           loadData();
           Util.notify("Removation Successful","Customer "+selectedid+" Removed",Util.Notification.success);    
         }else{
           Util.notify("Removation Failure","Sorry Password incorrect",Util.Notification.error);                 
         }
        Dialog.dialog.close();
        }      
    }

}
class BillingHistory extends RecursiveTreeObject<BillingHistory>{
    StringProperty id;
    StringProperty paid;
    StringProperty date;
    StringProperty time;
   
    
    public BillingHistory(String id, String date, String time,String paid) {
        this.id = new SimpleStringProperty(id);
        this.paid = new SimpleStringProperty(paid);
        this.date = new SimpleStringProperty(date) ;
        this.time = new SimpleStringProperty(time) ;               
    }
     
}
