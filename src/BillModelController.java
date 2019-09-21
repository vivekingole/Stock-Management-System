/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jfoenix.controls.JFXButton;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;


public class BillModelController implements Initializable {
    
    WritableImage imagelayout=new WritableImage(600,750);
    public static JFXButton close;
    BufferedImage bufferedimage;
    File outfile;
    public static String billno_,date_,name_,total_,type_,data[][];
    @FXML
    private AnchorPane idpane;
    @FXML
    private Text title;
   @FXML
    private AnchorPane anchor2;
   @FXML
   private JFXButton closebtn;
    @FXML
    private Text billno;
    @FXML
    private Text date;
    @FXML
    private Text name;
    @FXML
    private Text total;
    @FXML
    private Text subtitle;
    @FXML
    private ImageView logo2;
    @FXML
    private ImageView logo1;
    @FXML
    private VBox vbox;
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
       close=closebtn;
       logo1.setImage(new Image(new File(".\\img\\logo.png").toURI().toString()));
       logo2.setImage(new Image(new File(".\\img\\logo.png").toURI().toString()));
       title.setText((String)Util.getFirstRowData("title", "bill_title", 2));
       subtitle.setText((String)Util.getFirstRowData("title", "bill_subtitle", 2));
       billno.setText(billno_);
       date.setText(date_);
       name.setText(name_);
       total.setText(total_);
       int i=0;
       for(String s[]:data){
           ((Text)((HBox)((VBox)vbox.getChildren().get(i)).getChildren().get(0)).getChildren().get(0)).setText(s[0]);
           ((Text)((HBox)((VBox)vbox.getChildren().get(i)).getChildren().get(0)).getChildren().get(1)).setText(s[1]);
           String temp=(String)Util.getRowData("product","pname",2,"pid",s[1]);
           ((Text)((HBox)((VBox)vbox.getChildren().get(i)).getChildren().get(0)).getChildren().get(2)).setText(temp);
           ((Text)((HBox)((VBox)vbox.getChildren().get(i)).getChildren().get(0)).getChildren().get(3)).setText(s[3]);
           ((Text)((HBox)((VBox)vbox.getChildren().get(i)).getChildren().get(0)).getChildren().get(4)).setText(s[4]);
           i++;
        }
       anchor2.snapshot(null, imagelayout);
       if(type_=="in")
        //outfile = new File(".\\img\\in\\"+billno_+".jpg");     
        outfile = new File(".\\img\\in\\"+date_+".jpg");     
       else
        outfile = new File(".\\img\\out\\"+name_+"_"+date_+".jpg");     
       try {
            bufferedimage = SwingFXUtils.fromFXImage(imagelayout, null);
            ImageIO.write(bufferedimage, "png", outfile);
        } catch (Exception e) {System.err.println(e);}              
    }    
    public static void setValues(String billno,String date,String name,String total,String type,String data1[][]){
        billno_=billno;
        date_=date;
        name_=name;
        total_=total;
        type_=type;
        data=data1;       
    }    

    @FXML
    private void printImage(ActionEvent event) {
        printImage(bufferedimage);
    }
     private void printImage(BufferedImage image) {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(new Printable() {
      @Override
      public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // Get the upper left corner that it printable
         int width=0,height=0;
         if(type_.equals("in")){
             width=150;
             height=100;
         }else{
             width=200;
             height=250;
         }
        int x = (int) Math.ceil(pageFormat.getImageableX());
        int y = (int) Math.ceil(pageFormat.getImageableY());
        if (pageIndex != 0) {
          return NO_SUCH_PAGE;
        }
        graphics.drawImage(image, x, y, image.getWidth()-width, image.getHeight()-height, null);
        return PAGE_EXISTS;
      }
    });
    try {
      printJob.print();
    } catch (PrinterException e1) {
      e1.printStackTrace();
    }
  }
}
