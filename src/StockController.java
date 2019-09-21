

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.resource.animatefx.animation.FadeInDown;
import home.resource.animatefx.animation.FadeInRight;
import home.resource.animatefx.animation.FadeOutRight;
import home.resource.animatefx.animation.FadeOutUp;
import home.resource.animatefx.animation.SlideInLeft;
import home.resource.animatefx.animation.SlideOutLeft;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Customer;
import model.Product;
import model.Item;

public class StockController implements Initializable {
    int flag=0,flag1=0;
    String selectedid,selectedname;
    JFXTreeTableView<Product> treeview;
    JFXTreeTableView<Customer> treeview1;
    JFXDialog dialog;
    ObservableList<Product> list=FXCollections.observableArrayList();
    ObservableList<Customer> list1=FXCollections.observableArrayList();
    LinkedList<Item> cart=new LinkedList<>();
    Database db=Database.getInstance();
    public static String selectedfname,selectedlname,selectedpending;
    String selectedpname,selectedpid,selectedpstock;
    int selectedrate,productcounter,totalbill;
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
    private AnchorPane anchor11;
    @FXML
    private Label size1;
    @FXML
    private JFXTextField searchtf1;
    @FXML
    private Label searchlbl1;
    @FXML
    private Label cname;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private JFXTextField advance;
    @FXML
    private Label total;
    @FXML
    private Label date1;
    @FXML
    private JFXListView<String> plist;
    @FXML
    private Label pcount;
    @FXML
    private Label pname;
    @FXML
    private Pane add_pane;
    @FXML
    private JFXTextField add_fname;
    @FXML
    private JFXTextField add_lname;
    @FXML
    private JFXTextField add_phone;
    @FXML
    private JFXTextField add_addr;
    @FXML
    private Label ptotal;
    @FXML
    private JFXTextField pqty;
    @FXML
    private JFXTextField pdiscount;
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    productcounter=0;
    total.setText("0");
    totalbill=0;
    date.setText(LocalDate.now().toString());
    time.setText(LocalTime.now().getHour()+" : "+LocalTime.now().getMinute());
    searchtf.setOpacity(0);
    searchtf.focusedProperty().addListener(event->{
         if(!searchtf.isFocused())closeTF(1);
             });
    searchtf1.setOpacity(0);
    searchtf1.focusedProperty().addListener(event->{
         if(!searchtf1.isFocused())closeTF(2);
             });
              
     initTable();  
     treeview1.getSelectionModel().selectedItemProperty().addListener(e->{
     selectedfname=treeview1.getSelectionModel().getSelectedItem().getValue().fname.getValue();    
     selectedlname=treeview1.getSelectionModel().getSelectedItem().getValue().lname.getValue();    
     selectedpending=treeview1.getSelectionModel().getSelectedItem().getValue().pending.getValue();    
     cname.setText(selectedfname+" "+selectedlname);
     });
     treeview.getSelectionModel().selectedItemProperty().addListener(e->{
     ptotal.setText("0");
     pqty.setText("");
     selectedpname=treeview.getSelectionModel().getSelectedItem().getValue().display.getValue();    
     selectedpid=treeview.getSelectionModel().getSelectedItem().getValue().id.getValue();    
     selectedrate=Integer.parseInt(treeview.getSelectionModel().getSelectedItem().getValue().rate.getValue());    
     selectedpstock=treeview.getSelectionModel().getSelectedItem().getValue().quantity.getValue();    
     pname.setText(selectedpname);
     });
     
     pqty.textProperty().addListener(l->{
         if(!pqty.getText().equals("")){
        int n=Integer.parseInt(pqty.getText());
        ptotal.setText(""+(n*selectedrate));
         }else{
             ptotal.setText("0");
         }
     });
     }  
    public void loadData(int n){       
        if(n==1){
        try{
            list.clear();
            ResultSet rs=db.executeQuery("select * from product");            
            while(rs.next()){
                list.add(new Product(rs.getString("id"),
                                       rs.getString("name"),
                                       rs.getString("section"),
                                       rs.getString("quantity"),                                       
                                       rs.getString("rate"),                                       
                                       rs.getString("display_name")                                       
                ));
            }
        } catch (Exception ex) {
            System.out.println("####Exception at load Product data");         
        }  
        }else{
          try{
            list1.clear();
            ResultSet rs=db.executeQuery("select * from customer");            
            while(rs.next()){
                list1.add(new Customer(rs.getString("fname"),
                                       rs.getString("lname"),
                                       rs.getString("phone"),
                                       rs.getString("addr"),                                       
                                       rs.getString("pending")                                                                                                            
                ));
            }
        } catch (Exception ex) {
            System.out.println("####Exception at load customer data");         
        }  
        }
    }
    public void initTable(){             
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ creating column ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
                           //~~~~~~~~~~~~ TABLE 1~~~~~~~~~~~~~~//
          JFXTreeTableColumn<Product, String> idcol = new JFXTreeTableColumn<>("Product id");
        idcol.setCellValueFactory((param) ->{
        if(idcol.validateValue(param)) return param.getValue().getValue().id;
        else return idcol.getComputedValue(param);
        });
                
        JFXTreeTableColumn<Product, String> namecol = new JFXTreeTableColumn<>("Product Name");
        namecol.setCellValueFactory((param) ->{
        if(namecol.validateValue(param)) return param.getValue().getValue().display;
        else return namecol.getComputedValue(param);
        });
        
        JFXTreeTableColumn<Product, String> sectioncol = new JFXTreeTableColumn<>("Section");
        sectioncol.setCellValueFactory((param) ->{
        if(sectioncol.validateValue(param)) return param.getValue().getValue().section;
        else return sectioncol.getComputedValue(param);
        });
    
        JFXTreeTableColumn<Product, String> ratecol = new JFXTreeTableColumn<>("Rate");
        ratecol.setCellValueFactory((param) ->{
        if(ratecol.validateValue(param)) return param.getValue().getValue().rate;
        else return ratecol.getComputedValue(param);
        });
        
        JFXTreeTableColumn<Product, String> quantitycol = new JFXTreeTableColumn<>("Available");
        quantitycol.setCellValueFactory((param) ->{
        if(quantitycol.validateValue(param)) return param.getValue().getValue().quantity;
        else return quantitycol.getComputedValue(param);
        });              
        
                           //~~~~~~~~~~~~ TABLE 2~~~~~~~~~~~~~~//
          
         JFXTreeTableColumn<Customer, String> fnamecol = new JFXTreeTableColumn<>("First Name");
        fnamecol.setCellValueFactory((param) ->{
        if(fnamecol.validateValue(param)) return param.getValue().getValue().fname;
        else return fnamecol.getComputedValue(param);
        });
                
        JFXTreeTableColumn<Customer, String> lnamecol = new JFXTreeTableColumn<>("Last Name");
        lnamecol.setCellValueFactory((param) ->{
        if(lnamecol.validateValue(param)) return param.getValue().getValue().lname;
        else return lnamecol.getComputedValue(param);
        });

        JFXTreeTableColumn<Customer, String> phonecol = new JFXTreeTableColumn<>("Phone");
        phonecol.setCellValueFactory((param) ->{
        if(phonecol.validateValue(param)) return param.getValue().getValue().phone;
        else return phonecol.getComputedValue(param);
        });
         
        JFXTreeTableColumn<Customer, String> addrcol = new JFXTreeTableColumn<>("Address");
        addrcol.setCellValueFactory((param) ->{
        if(addrcol.validateValue(param)) return param.getValue().getValue().addr;
        else return addrcol.getComputedValue(param);
        });
        
        JFXTreeTableColumn<Customer, String> pendingcol = new JFXTreeTableColumn<>("Pending");
        pendingcol.setCellValueFactory((param) ->{
        if(pendingcol.validateValue(param)) return param.getValue().getValue().pending;
        else return pendingcol.getComputedValue(param);
        });               
   
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Loading data ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

        loadData(1);
        loadData(2);
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ building table ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

                                //~~~~~~~~~~~~ TABLE 1~~~~~~~~~~~~~~//

        final TreeItem<Product> root = new RecursiveTreeItem<Product>(list, RecursiveTreeObject::getChildren);         
        treeview = new JFXTreeTableView<Product>(root);
        treeview.setShowRoot(false);
        treeview.setEditable(true);
        treeview.getColumns().setAll(idcol, namecol,sectioncol,ratecol,quantitycol); 
        searchtf.textProperty().addListener((o,oldVal,newVal)->{
        treeview.setPredicate(user -> user.getValue().id.get().contains(newVal)
                || user.getValue().name.get().contains(newVal)
                || user.getValue().section.get().contains(newVal)               
               );               
        });
        size.textProperty().bind(Bindings.createStringBinding(()->"Total Records "+treeview.getCurrentItemsCount(),
        treeview.currentItemsCountProperty()));
                treeview.setPrefSize(anchor1.getPrefWidth()+5, anchor1.getPrefHeight()+5);
                sectioncol.setPrefWidth(anchor1.getPrefWidth()/5);
                idcol.setPrefWidth(anchor1.getPrefWidth()/5);
                namecol.setPrefWidth(anchor1.getPrefWidth()/5);               
                quantitycol.setPrefWidth(anchor1.getPrefWidth()/5);               
                ratecol.setPrefWidth(anchor1.getPrefWidth()/5);               
                anchor1.getChildren().setAll(treeview);        

                                    //~~~~~~~~~~~~ TABLE 2~~~~~~~~~~~~~~//
         final TreeItem<Customer> root1 = new RecursiveTreeItem<Customer>(list1, RecursiveTreeObject::getChildren);         
        treeview1 = new JFXTreeTableView<Customer>(root1);
        treeview1.setShowRoot(false);
        treeview1.setEditable(true);
        treeview1.getColumns().setAll(fnamecol, lnamecol,phonecol,addrcol,pendingcol); 
        searchtf1.textProperty().addListener((o,oldVal,newVal)->{
        treeview1.setPredicate(user -> user.getValue().fname.get().contains(newVal)
                || user.getValue().lname.get().contains(newVal)
                || user.getValue().phone.get().contains(newVal)               
                || user.getValue().addr.get().contains(newVal)               
               );               
        });
        size1.textProperty().bind(Bindings.createStringBinding(()->"Total Records "+treeview1.getCurrentItemsCount(),
        treeview1.currentItemsCountProperty()));
                treeview1.setPrefSize(anchor11.getPrefWidth()+5, anchor11.getPrefHeight()+5);
                fnamecol.setPrefWidth(anchor11.getPrefWidth()/5);
                lnamecol.setPrefWidth(anchor11.getPrefWidth()/5);
                phonecol.setPrefWidth(anchor11.getPrefWidth()/5);               
                addrcol.setPrefWidth(anchor11.getPrefWidth()/5);               
                pendingcol.setPrefWidth(anchor11.getPrefWidth()/5);               
                anchor11.getChildren().setAll(treeview1);           
    }
       
    //~~~~~~~~~~~~~~~~~~~~~~~~~~ search animation ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    @FXML
    private void searchExit(MouseEvent event) {
        if(event.getSource()==searchlbl)
           closeTF(1);
        else
           closeTF(2); 
    }
    public void closeTF(int n) {
        if(n==1){
            new SlideInLeft(searchlbl).play();
            new FadeOutRight(searchtf).play();
            flag=0;  
        }else{
            new SlideInLeft(searchlbl1).play();
            new FadeOutRight(searchtf1).play();
            flag1=0;  
        }
    }
    @FXML
    private void searchEnter(MouseEvent event) {
        if(event.getSource()==searchlbl){
            if(flag==0){
            searchtf.requestFocus();
            searchtf.clear();
            new SlideOutLeft(searchlbl).play();
            new FadeInRight(searchtf).play();
            flag=1;        
            }
        }else{
            if(flag1==0){
            searchtf1.requestFocus();
            searchtf1.clear();
            new SlideOutLeft(searchlbl1).play();
            new FadeInRight(searchtf1).play();
            flag1=1;        
            }
        }
                
    }
   
    @FXML
    void back(ActionEvent event) {
           try {
            StackPane pane=FXMLLoader.load(getClass().getResource("Main.fxml"));  
            AdminLogin.root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void addProduct(ActionEvent event) {
        Item i=new Item(selectedpid,selectedpname,pqty.getText(),ptotal.getText(),selectedpstock);
        cart.addLast(i);
        plist.getItems().add(i.display.getValue()+"   -   "+i.quantity.getValue()+"   -    "+i.ptotal.getValue()+" /-");
        pcount.setText(""+(++productcounter));
        totalbill+=Integer.parseInt(i.ptotal.getValue());
        total.setText(""+totalbill);
    }

    @FXML
    private void addNewCustomer(ActionEvent event) {
        add_fname.setText("");
        add_lname.setText("");
        add_phone.setText("");
        add_addr.setText("");
        add_pane.setDisable(false);
        FadeInDown animation=new FadeInDown(add_pane);
        animation.setSpeed(2);
        animation.play();
    }

    @FXML
    private void reset(ActionEvent event) {
         try {
            StackPane pane=FXMLLoader.load(getClass().getResource("Stock.fxml"));  
            stack.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void done(ActionEvent event) {
        int bill=0;
       //try{
       //    ResultSet rs=db.executeQuery("select pending from customer where fname='"+selectedfname+"' and lname='"+selectedlname+"'");
       //    rs.next();
       //    bill=rs.getInt(1);
           bill=Integer.parseInt(selectedpending);
           bill+=totalbill; 
           if(!advance.getText().equals(""))
           bill-=Integer.parseInt(advance.getText());
      // }catch(Exception e){
       //     Dialog.loadDialog(stack, new Text("Exception"),new Text(e.getMessage()));
      // }
       if(!db.executeReturnException("update customer set pending="+bill+" where fname='"+selectedfname+"' and lname='"+selectedlname+"'"))
           Dialog.loadDialog(stack, new Text("Exception"),new Text(Database.exception));
       
       for(Item i : cart.toArray(new Item[cart.size()])){
          // System.out.println(i.stock);
           int oldstock=Integer.parseInt(i.stock);
           int newstock= oldstock - Integer.parseInt(i.quantity.getValue());
           db.execute("update product set quantity="+newstock+" where id='"+i.id.getValue()+"'");
           db.execute("insert into issued_stock values("
                   + "'"+ selectedfname+" "+selectedlname +"',"
                   + "'"+ i.display.getValue() +"',"
                   + i.quantity.getValue()+","
                   + "'"+date.getText()+"'"
                   + ")");       
       }
       
       
       if(db.executeReturnException("insert into transitions(cname,date,time,amount,tag,totalbill) values("
               + "'"+ selectedfname+" "+selectedlname +"',"
               + "'"+ date.getText() +"',"
               + "'"+ time.getText() +"',"
               + advance.getText() +","
               + "'I',"
               + totalbill
               + ")"))
       {
           Util.notify("Successful", "Products issued to customer", Util.Notification.success);
       }
       else
            Dialog.loadDialog(stack, new Text("Exception"),new Text(Database.exception));
       
       reset(event);
    }


    @FXML
    private void cancleProduct(ActionEvent event) {
        int i=plist.getSelectionModel().getSelectedIndex();
        if(i>=0){
        totalbill-=Integer.parseInt(cart.get(i).ptotal.getValue());
        plist.getItems().remove(i);
        cart.remove(i);
        pcount.setText(""+(--productcounter));
        total.setText(""+totalbill);
        }
    }

    @FXML
    private void add_cancle(ActionEvent event) {
        FadeOutUp animation=new FadeOutUp(add_pane);
        animation.setSpeed(2);
        animation.play();
        add_pane.setDisable(true);
    }

    @FXML
    private void add_addCustomer(ActionEvent event) {
      
        if(db.executeReturnException("insert into customer(fname,lname,phone,addr) values("
                    + "'"+ add_fname.getText() +"',"
                    + "'"+ add_lname.getText() +"',"
                    + "'"+ add_phone.getText() +"',"
                    + "'"+ add_addr.getText() +"'"
                    + ")"))
            Util.notify("Successful","Customer "+add_fname.getText()+add_lname.getText()+" Added",Util.Notification.success);                 
        else
             Dialog.loadDialog(stack, new Text("Exception"),new Text(Database.exception));
        
            FadeOutUp animation=new FadeOutUp(add_pane);
            animation.setSpeed(2);
            animation.play();
            add_pane.setDisable(true);
        loadData(2);
    }

    @FXML
    private void deductDiscount(ActionEvent event) {
        totalbill-=Integer.parseInt(pdiscount.getText());
        total.setText(""+totalbill);
    }

    @FXML
    private void payment(ActionEvent event) {
       
        if(!treeview1.getSelectionModel().isEmpty()){
        
            try{
                Stage stage=new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("Bill.fxml"));        
                Scene scene = new Scene(root);        
                stage.setScene(scene);
                stage.centerOnScreen();     
                stage.show();
            }catch(Exception e){
                Dialog.loadDialog(stack, new Text("Exception"),new Text(e.getMessage()));
            }
           
        }else{
             Util.notify("Invalid Operation","First Select the row",Util.Notification.error);                
        }
  
    }

}

