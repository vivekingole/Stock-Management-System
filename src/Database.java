
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane; 
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class Database {

    private static Connection con;
    private static Statement stmt;        
    private static PreparedStatement prepstmt;        
    private static boolean flag=false;
    private static Database db;
    public static String exception;
    private Database(){ 
        
     }
    public static Database getInstance(){
        if(flag==false){
            db=new Database();
            db.createConnection();
            flag=true;
        }
        return db;
    } 
    public void createConnection(){
        try{            
             Class.forName("org.sqlite.JDBC");
             con=DriverManager.getConnection("jdbc:sqlite:database\\db.sqlite");
             System.out.println("Connection established !!!");                  
        }catch(Exception e){
            System.out.println("Connection failed #####");
             Alert alert=new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK);
            alert.showAndWait();
            System.exit(1);
        }
    }
    public boolean execute(String query){       
        Boolean flag=true;
        try{
            stmt=con.createStatement();
            stmt.execute(query);                
            System.out.println("Query: "+query+" execution successful !!!");        
        }catch(Exception e){
               System.out.println("Exception in Query: "+query+" #####");
               System.out.println("Exception : "+e.getMessage());
               flag=false;                         
        }
        return flag;
    }
    public boolean executeReturnException(String query){
         Boolean flag=true;
        try{
            stmt=con.createStatement();
            stmt.execute(query);                
            System.out.println("Query: "+query+" execution successful !!!");        
        }catch(Exception e){
            exception=e.getMessage();
            flag=false;                         
        }
        return flag;
    }
    public ResultSet executeQuery(String query){
        ResultSet res;
        try{
            stmt=con.createStatement();            
            res=stmt.executeQuery(query);         
            System.out.println("Query: "+query+" execution successful !!!");            
            return res;            
        }catch(Exception e){
               System.out.println("Exception in Query: "+query+" #####");                                                      
               System.out.println("Exception : "+e.getMessage());
        }        
        return null;
    }
    public PreparedStatement getPreparedStatement(String query){       
        try{
            prepstmt=con.prepareStatement(query);
         }catch(Exception e){
               System.out.println("Exception in getPreparedstatement(): "+query+" #####");
               System.out.println("Exception : "+e.getMessage());
               return null;                         
        }
        return prepstmt;
    }
    public static void main(String[] args) throws SQLException {     
        Database db=Database.getInstance();
       /* ResultSet rs=db.executeQuery("select * from member");
        while(rs.next()){
            System.out.println(rs.getString(1));
        }
        */
    }
    
}
