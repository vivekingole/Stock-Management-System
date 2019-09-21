
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.resource.animatefx.animation.FadeInRight;
import home.resource.animatefx.animation.FadeOutRight;
import home.resource.animatefx.animation.SlideInLeft;
import home.resource.animatefx.animation.SlideOutLeft;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class BillController implements Initializable {
    int flag=0;
    String selectedcname,selectedpname;
    JFXTreeTableView<Billing> treeview;
    JFXDialog dialog;
    ObservableList<Billing> list=FXCollections.observableArrayList();
    Database db=Database.getInstance();
    LinkedList<LocalDate> dates=new LinkedList<>();
    LocalDate currentdate=LocalDate.now();
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
    @FXML
    private Label pending;
    @FXML
    private Label cname;
    @FXML
    private Label issued_date;
    @FXML
    private Label days;
    @FXML
    private JFXTextField incdec;
    @FXML
    private JFXTextField payment;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    selectedcname=StockController.selectedfname+" "+StockController.selectedlname;
    searchtf.setOpacity(0);
    searchtf.focusedProperty().addListener(event->{
         if(!searchtf.isFocused())closeTF();
             });
    cname.setText(selectedcname);
    pending.setText(StockController.selectedpending);
    try{
       
      // int n= (int)ChronoUnit.DAYS.between(d1,d2);
        
    }catch(Exception e){
        Dialog.loadDialog(stack, new Text("Exception"),new Text(e.getMessage()));
    }
       
     initTable();        
     }  
    public void loadData(){       
        try{
            list.clear();
            ResultSet rs=db.executeQuery("select * from issued_stock where cname='"+cname.getText()+"' ");            
            while(rs.next()){
                list.add(new Billing(rs.getString("pname"),
                                       rs.getString("qty"),
                                       rs.getString("date")  
                ));
                 LocalDate d=LocalDate.parse(rs.getString("date"));
                 if(!dates.contains(d)){
                     dates.add(d);
                     issued_date.setText(issued_date.getText()+"  "+rs.getString("date"));
                     int n= (int)ChronoUnit.DAYS.between(d,currentdate);
                     days.setText(days.getText()+"           "+n+"       ");
                 }
            }
        } catch (Exception ex) {
            System.out.println("####Exception at load data");
        }   
    }
    public void initTable(){             
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ creating column ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
        
          JFXTreeTableColumn<Billing, String> pnamecol = new JFXTreeTableColumn<>("Product Name");
        pnamecol.setCellValueFactory((param) ->{
        if(pnamecol.validateValue(param)) return param.getValue().getValue().pname;
        else return pnamecol.getComputedValue(param);
        });
 
        JFXTreeTableColumn<Billing, String> qtycol = new JFXTreeTableColumn<>("Quantity");
        qtycol.setCellValueFactory((param) ->{
        if(qtycol.validateValue(param)) return param.getValue().getValue().qty;
        else return qtycol.getComputedValue(param);
        });
 
          JFXTreeTableColumn<Billing, String> datecol = new JFXTreeTableColumn<>("Date");
        datecol.setCellValueFactory((param) ->{
        if(datecol.validateValue(param)) return param.getValue().getValue().date;
        else return datecol.getComputedValue(param);
        });               

        JFXTreeTableColumn<Billing, JFXCheckBox> selectcol = new JFXTreeTableColumn<>("Select");
        selectcol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Billing, JFXCheckBox> x) ->
        selectcol.validateValue(x) ? x.getValue().getValue().check : selectcol.getComputedValue(x));

        
        loadData();
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ building table ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    
        final TreeItem<Billing> root = new RecursiveTreeItem<Billing>(list, RecursiveTreeObject::getChildren);         
        treeview = new JFXTreeTableView<Billing>(root);
        treeview.setShowRoot(false);
        treeview.setEditable(true);
        treeview.getColumns().setAll(datecol,pnamecol,qtycol,selectcol); 
        searchtf.textProperty().addListener((o,oldVal,newVal)->{
        treeview.setPredicate(user -> user.getValue().pname.get().contains(newVal)
                || user.getValue().date.get().contains(newVal)
               );              
        });
 
        size.textProperty().bind(Bindings.createStringBinding(()->"Total Products "+treeview.getCurrentItemsCount(),
        treeview.currentItemsCountProperty()));

                treeview.setPrefSize(anchor1.getPrefWidth(), anchor1.getPrefHeight());
                qtycol.setPrefWidth(anchor1.getPrefWidth()/4);
                pnamecol.setPrefWidth(anchor1.getPrefWidth()/4);
                selectcol.setPrefWidth(anchor1.getPrefWidth()/4);               
                datecol.setPrefWidth(anchor1.getPrefWidth()/4);               
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
    void pay(ActionEvent event) {
       
    }

    @FXML
    private void clearItems(ActionEvent event) {
        try{
        Iterator iterator=list.iterator();
        while(iterator.hasNext()){
            Billing b=(Billing)iterator.next();
            if(b.check.getValue().isSelected())
            {
                iterator.remove();
            }
        }
        }catch(Exception e){}
    }

    @FXML
    private void selectAll(ActionEvent event) {
        try{
        Iterator iterator=list.iterator();
        while(iterator.hasNext()){
            Billing b=(Billing)iterator.next();
            b.check.getValue().setSelected(true);
        }
        }catch(Exception e){}
    }

    @FXML
    private void incrementDecrement(ActionEvent event) {
        int bill=Integer.parseInt(pending.getText());
        int n=Integer.parseInt(incdec.getText());
        bill+=n;
        pending.setText(""+bill);
    }

    @FXML
    private void billReceipt(ActionEvent event) {
    }

}
class Billing extends RecursiveTreeObject<Billing>{
    StringProperty pname;
    StringProperty qty;
    StringProperty date;
    ObjectProperty<JFXCheckBox> check;
   
    public Billing(String pname, String qty,String date) {
        this.pname = new SimpleStringProperty(pname);
        this.qty = new SimpleStringProperty(qty) ;
        this.date = new SimpleStringProperty(date) ;
        this.check =new SimpleObjectProperty<>(new JFXCheckBox());
    }
     
}
