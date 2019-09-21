

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
public class Util {
    public static FileInputStream fis;
    public static FileOutputStream fos;
   public static Database db=Database.getInstance();
    public static void copyFile(String from,String to){
        int ch;
        try {
            
            fis=new FileInputStream(from);                   
            fos=new FileOutputStream(to);
            while((ch=fis.read())!=-1){
                fos.write(ch);
            }
            fos.write(-1);           
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }catch(Exception e){
            System.out.println("error while copying files :\n\n"+e.getMessage());
        }
        finally{
           try{
               fos.close();           
            fis.close();
           }catch(Exception e){}
        }
    }
     public static String generateCode(String s1,int n){
        String s=""+n;        
        for(int i=1;i<=6-s.length();i++) s1+="0";
        s1+=n;
        return s1;
    }
     public static int decodeInt(String s){
        return(Integer.parseInt(s.substring(1)));
    }
     public static String[] getColumn(String table,String column){
         String arr[]={"null"};  
         try {
            ResultSet rs=db.executeQuery("select count("+column+") from "+table);
            rs.next();
            arr=new String[rs.getInt(1)];
            int i=0;
            rs=db.executeQuery("select "+column+" from "+table);
            while(rs.next())
            {
                arr[i++]=rs.getString(1);
            }
        } catch (SQLException ex) {
            System.err.println("exception in getcolumn:"+ex.getMessage());
        }
           return arr;
     }  
     public static String[] getColumn(String table,String column,String primarykey,String primaryvalue){
         String arr[]={"null"};  
         String s="";
         int i=0;
         try {
            ResultSet rs=db.executeQuery("select "+column+" from "+table
                    +" where "+primarykey+"='"+primaryvalue+"'");
            while(rs.next()){
                s+=rs.getString(1)+" ";
                i++;
            }
            s.trim();
            arr=new String[i];
            arr=s.split(" ");
        } catch (SQLException ex) {
            System.err.println("exception in getcolumn:"+ex.getMessage());
        }
        if(i==0)return null; 
        return arr;
     }  
     public static int searchCount(String table,String column,String value){         
         ResultSet rs;   
         int count=0;
         rs=db.executeQuery("select count("+column+") from (select "+column+" from "
               +table+" where "+column+"='"+value+"')");
        try {         
            rs.next();
            count=rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
         return count;
     }
     public static String[] getUserPermission(String id){
        String s="";
        try {
            ResultSet rs;
            rs=db.executeQuery("select * from user_permission where id='"+id+"'");
            rs.next();
            for(int i=2;i<=17;i++){
                //s+=rs.getMetaData().getColumnName(i)+":"+rs.getBoolean(i)+"-\n";
                if(rs.getBoolean(i)){
                    System.out.println("value of i :"+i);
                    switch(i){
                        case 2: s+="Add Book"+"-"; break;
                        case 3: s+="Edit Book"+"-"; break;
                        case 4: s+="Issue Book"+"-"; break;
                        case 5: s+="Submit Book"+"-"; break;
                        case 6: s+="View Book"+"-"; break;
                        case 8: s+="Add Member"+"-"; break;
                        case 9: s+="Edit Member"+"-"; break;
                        case 10: s+="View Member"+"-"; break;
                        case 12: s+="Add User"+"-"; break;
                        case 13: s+="Edit User"+"-"; break;
                        case 14: s+="View User"+"-"; break;
                        case 15: s+="Edit Setting"+"-"; break;
                        case 16: s+="View All"+"-"; break;
                        case 17: s+="Administrator"; break;
                    }
                }
            }
            //String permission[];
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
         //System.out.println(s);
        return s.split("-");
     }
     public static Object getFirstRowData(String table,String column,int datatype){         
         ResultSet rs;   
         Object obj="";  
           rs=db.executeQuery("select * from "+table);          
        try {         
            rs.next();
            if(datatype==1)obj=rs.getInt(column);
            else if(datatype==2) obj=rs.getString(column);
            else if(datatype==3)obj=rs.getDate(column);
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
         return obj;
     }
      public static Object getRowData(String table,String column,int datatype,String primrykeycolumn,String primarykeyvalue){         
         ResultSet rs;   
         Object obj="";  
           rs=db.executeQuery("select * from "+table+" where "+primrykeycolumn+"='"+primarykeyvalue+"'");            
        try {         
            rs.next();
            if(datatype==1)obj=rs.getInt(column);
            else if(datatype==2) obj=rs.getString(column);
            else if(datatype==3)obj=rs.getDate(column);
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
         return obj;
     }
       public static boolean checkRow(String table,String primarykey,String primarykeyvalue){         
         ResultSet rs;            
         try{
         rs=db.executeQuery("select * from "+table+" where "+primarykey+"='"+primarykeyvalue+"'");            
         rs.next();
         rs.getString(1);
         return true;
         }catch(Exception e){          
             return false;
         }         
     }
     public static LocalDate toLocalDate(String date,String format){         
        Date dt=new Date(0,0,0);  //for only initialize purpose
         try{
         ResultSet rs=db.executeQuery("select TO_DATE('"+date+"','"+format+"') from dual");
         rs.next();
         dt=rs.getDate(1);   
         return dt.toLocalDate();
         }catch(Exception e){
             System.err.println("error In toLocalDate()");             
         }
         return  dt.toLocalDate();
     }
     public static boolean update(String table,String column,String newval,String keycolumn,String keyval,int datatype){
         boolean flag=false;
         if(datatype==1)
            flag=db.execute("update "+table+" set "+column+"="+newval+" where "+keycolumn+"='"+keyval+"'");
         else
            flag=db.execute("update "+table+" set "+column+"='"+newval+"' where "+keycolumn+"='"+keyval+"'");
         return flag;
     }
     
     public static void notify(String title,String text,Notification type){
         String name="";         
         if(type==Notification.success)name="true";
         else if(type==Notification.error)name="false";
         else if(type==Notification.warning)name="warning";
         else if(type==Notification.info)name="info";
         ImageView img=new ImageView(new Image("/"+name+".png"));
         img.setFitWidth(50);
         img.setFitHeight(50);
       
          Notifications notification=Notifications.create()
                .title(title)
                .text(text)
                .graphic(img)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)                
                .hideCloseButton()
                .darkStyle();        
          notification.show();
         /* if(type==0)notification.showInformation();
         else if(type==1)notification.showError();
         else if(type==2)notification.showWarning();
         else if(type==3)notification.showConfirm();
         */
     }
     public static void visiteURL(String url){
         try {
             Desktop.getDesktop().browse(new URL(url).toURI());
          } catch (Exception e) { System.out.println(e.getMessage()); }        
     }
    public static void main(String ar[]){
        System.out.println(checkRow("admin", "name", "v1"));
    }
    public static enum Notification{
        success, error ,warning , info
    }
}
