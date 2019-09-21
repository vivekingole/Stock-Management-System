

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.paint.Paint;

public class Validator {
   
    static Pattern pattern;
    static Matcher matcher;
    public static void demo(){
        pattern=Pattern.compile("--");
        matcher=pattern.matcher("ashua--ds--kpj");
        while(matcher.find()){
            System.out.println("pattern fount at "+matcher.start()+" to "+(matcher.end()-1));
        }
    }
    public static boolean checkRequiredValidator(JFXTextField ...tf){        
        int f=0;
        for(JFXTextField t:tf)
            if(t.getText().equals("")){
                f=1;        
                t.setUnFocusColor(Paint.valueOf("red"));
                t.setStyle("-fx-prompt-text-fill:red");
                t.setStyle("-fx-text-fill:white");
            }
        if(f==1)return false;
        return true;
    }
    public static boolean checkRequiredValidator(JFXComboBox ...cb){        
        int f=0;
        for(JFXComboBox c:cb)
            if(c.getSelectionModel().isEmpty()){
                f=1;        
                c.setUnFocusColor(Paint.valueOf("red"));
                c.setStyle("-fx-prompt-text-fill:red");
            }
        if(f==1)return false;
        return true;
    }
    public static void main(String[] args) {
       
    }
    
}
