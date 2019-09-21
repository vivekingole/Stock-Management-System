
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

public class LogController implements Initializable,EventHandler<ActionEvent>{
    int flag=0;
    String selectedid,selectedname;
    JFXTreeTableView<LogData> treeview;
    JFXDialog dialog;
    ObservableList<LogData> list=FXCollections.observableArrayList();
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
            ResultSet rs=db.executeQuery("select * from log");            
            while(rs.next()){
                list.add(new LogData(rs.getString("id"),
                                       rs.getString("date"),
                                       rs.getString("billno"),
                                       rs.getString("buyer_name"),
                                       rs.getString("in"),
                                       rs.getString("out"),
                                       rs.getString("total"),                                       
                                       rs.getString("bill")                                       
                ));
            }
        } catch (Exception ex) {
            System.out.println("####Exception at load data");
          //  LogDatager.getLogDatager(LogDataController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    public void initTable(){             
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ creating column ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
        
          JFXTreeTableColumn<LogData, String> idcol = new JFXTreeTableColumn<>("Id");
        idcol.setPrefWidth(150);
        idcol.setCellValueFactory((param) ->{
        if(idcol.validateValue(param)) return param.getValue().getValue().id;
        else return idcol.getComputedValue(param);
        });
        
                
        JFXTreeTableColumn<LogData, String> datecol = new JFXTreeTableColumn<>("Date");
        datecol.setPrefWidth(150);
        datecol.setCellValueFactory((param) ->{
        if(datecol.validateValue(param)) return param.getValue().getValue().date;
        else return datecol.getComputedValue(param);
        });

        JFXTreeTableColumn<LogData, String> billnocol = new JFXTreeTableColumn<>("Bill no.");
        billnocol.setPrefWidth(150);
        billnocol.setCellValueFactory((param) ->{
        if(billnocol.validateValue(param)) return param.getValue().getValue().billno;
        else return billnocol.getComputedValue(param);
        });
        
        JFXTreeTableColumn<LogData, String> namecol = new JFXTreeTableColumn<>("Customer Name");
        namecol.setPrefWidth(150);
        namecol.setCellValueFactory((param) ->{
        if(namecol.validateValue(param)) return param.getValue().getValue().name;
        else return namecol.getComputedValue(param);
        });
        
        JFXTreeTableColumn<LogData, String> incol = new JFXTreeTableColumn<>("In");
        incol.setPrefWidth(150);
        incol.setCellValueFactory((param) ->{
        if(incol.validateValue(param)) return param.getValue().getValue().in;
        else return incol.getComputedValue(param);
        });
        
        JFXTreeTableColumn<LogData, String> outcol = new JFXTreeTableColumn<>("Out");
        outcol.setPrefWidth(150);
        outcol.setCellValueFactory((param) ->{
        if(outcol.validateValue(param)) return param.getValue().getValue().out;
        else return outcol.getComputedValue(param);
        });
 
          JFXTreeTableColumn<LogData, String> totalcol = new JFXTreeTableColumn<>("Available");
        totalcol.setPrefWidth(150);
        totalcol.setCellValueFactory((param) ->{
        if(totalcol.validateValue(param)) return param.getValue().getValue().total;
        else return totalcol.getComputedValue(param);
        });    
        
          JFXTreeTableColumn<LogData, String> billcol = new JFXTreeTableColumn<>("Bill");
        billcol.setPrefWidth(150);
        billcol.setCellValueFactory((param) ->{
        if(billcol.validateValue(param)) return param.getValue().getValue().bill;
        else return billcol.getComputedValue(param);
        });               

   
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Loading data ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

        loadData();
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ building table ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    
        final TreeItem<LogData> root = new RecursiveTreeItem<LogData>(list, RecursiveTreeObject::getChildren);         
        treeview = new JFXTreeTableView<LogData>(root);
        treeview.setShowRoot(false);
        treeview.setEditable(true);
        treeview.getColumns().setAll(datecol,billnocol,idcol,namecol,outcol,incol,totalcol,billcol); 
        searchtf.textProperty().addListener((o,oldVal,newVal)->{
        treeview.setPredicate(user -> user.getValue().id.get().contains(newVal)
                || user.getValue().date.get().contains(newVal)
                || user.getValue().date.get().contains(newVal)
                || user.getValue().bill.get().contains(newVal)
                || user.getValue().name.get().contains(newVal)               
               );               
        });
 
        size.textProperty().bind(Bindings.createStringBinding(()->"Total Records "+treeview.getCurrentItemsCount(),
        treeview.currentItemsCountProperty()));

                treeview.setPrefSize(anchor1.getPrefWidth(), anchor1.getPrefHeight());
                datecol.setPrefWidth((anchor1.getPrefWidth()/7)-30);
                idcol.setPrefWidth((anchor1.getPrefWidth()/7)-25);
                namecol.setPrefWidth((anchor1.getPrefWidth()/7)+20);               
                totalcol.setPrefWidth((anchor1.getPrefWidth()/7)-35);               
                billnocol.setPrefWidth((anchor1.getPrefWidth()/7));               
                incol.setPrefWidth((anchor1.getPrefWidth()/7)-35);               
                outcol.setPrefWidth((anchor1.getPrefWidth()/7)-35);               
                billcol.setPrefWidth(anchor1.getPrefWidth()/7);               
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
  
    @FXML
    void back(ActionEvent event) {
           try {
            StackPane pane=FXMLLoader.load(getClass().getResource("Main.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
               System.err.println(ex.getMessage());
        }
    }

     @FXML
    void clear(ActionEvent event) {
         Dialog.loadInputDialog(stack, new Text("Enter Confirmation Password :"), "delete", this);        
    }
    @Override   ////// Eventhandler 
    public void handle(ActionEvent event) {  
        JFXButton src=(JFXButton)event.getSource();
        
        if(src.getText().equals("delete")){
         String pass1=Dialog.tf.getText();
         String pass2=(String)Util.getFirstRowData("admin", "password2", 2);
         if(pass1.equals(pass2)){
           db.execute("delete from log");
           loadData();
           Util.notify("Removation Successful","All Records Removed",Util.Notification.success);    
         }else{
           Util.notify("Removation Failure","Sorry Password incorrect",Util.Notification.error);                 
         }
        Dialog.dialog.close();
        }      
    }
}
class LogData extends RecursiveTreeObject<LogData>{
    StringProperty id;
    StringProperty date;
    StringProperty billno;
    StringProperty name;
    StringProperty in;
    StringProperty out;
    StringProperty total;
    StringProperty bill;
  
    public LogData(String id, String date,String billno, String name,String in,String out,String total,String bill) {
        this.id = new SimpleStringProperty(id);
        this.date = new SimpleStringProperty(date);
        this.billno = new SimpleStringProperty(billno);
        this.name = new SimpleStringProperty(name);
        this.in = new SimpleStringProperty(in) ;
        this.out = new SimpleStringProperty(out) ;
        this.total = new SimpleStringProperty(total) ;            
        this.bill = new SimpleStringProperty(bill) ;            
    }
     
}
