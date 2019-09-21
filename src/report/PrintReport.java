
package report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.swing.JFrame;
 
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
 
public class PrintReport extends JFrame {
 
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
 
    public void showReport() throws JRException, ClassNotFoundException, SQLException, FileNotFoundException {
        
        JasperReport jasperReport = JasperCompileManager.compileReport("E:\\vivek\\Stock5\\src\\report\\ProductList.jrxml");
        HashMap<String, Object> parameters = new HashMap<String, Object>();
 
        parameters.put("id", "125");
       /* parameters.put("receipt_no", "RE101".toString());
        parameters.put("name", "Khushboo");
        parameters.put("amount", "10000");
        parameters.put("receipt_for", "EMI Payment");
        parameters.put("date", "20-12-2016");
        parameters.put("contact", "98763178".toString());
 */
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
 
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.print("Done!");
 
    }
    public static void main(String ars[]){
        try {
            new PrintReport().showReport();
        } catch (JRException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
 
}