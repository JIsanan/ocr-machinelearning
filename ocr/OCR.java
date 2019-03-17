/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Yee
 */
public class OCR {

    /**
     * @param args the command line arguments
     */
    public static void ocr(String[] args) throws SQLException, IOException, InterruptedException {
        Dimension resolution = new Dimension(1280, 720); // HD 720p
        Webcam webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(new Dimension[] { resolution }); // register custom resolution
        webcam.setViewSize(resolution); // set it
        webcam.open();
        int g = 1;
        while(g == 1){
            File file = new File(String.format("test-%d.png", 10));
            ImageIO.write(webcam.getImage(), "PNG", file);
            System.out.format("Image for %s saved in %s \n", webcam.getName(), file);
            Database db = new Database();
            Connection con = db.openConnection();
            Learning m = new Learning();
            Thread.sleep(500);
            m.cropInitial("C:/Users/Yee/Pictures/Sample Pictures/test-10.png");
            m.OCR("C:/Users/Yee/Documents/NetBeansProjects/OCR/test-10.png", con, db);
            
        }
        // TODO code application logic here
       Database db = new Database();
       Connection con = db.openConnection();
       Learning m = new Learning();
       m.OCR("C:/Users/Yee/Pictures/Sample Pictures/mset/Dboi.jpg", con, db);
    }
    
    
}
