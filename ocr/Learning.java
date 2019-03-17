/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

/**
 *
 * @author Yee
 */
public class Learning {
    public static void learn(String File, Connection con, Database db, String letter) throws SQLException {

        try {

            File input = new File(File);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            float col1 = 0, col2 = 0, col3 = 0, black = 0;
            int j = (int) (result.getWidth()*0.10);
            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(j, i));
                if(c.getBlue() < 80){
                    black++;
                }
            }
            col1 = (black/result.getHeight())*100;
            black = 0;
            j = (int) (result.getWidth()*0.50);
            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(j, i));
                                System.out.println(c.getBlue());
                if(c.getBlue() < 80)
                {
                    black++;
                }
            }
            col2 = (black/result.getHeight())*100;
            System.out.println(col2);
            black = 0;
            j = (int) (result.getWidth()*0.90);
            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(j, i));
                if(c.getBlue() < 80)
                {
                    black++;
                }
            }
            System.out.println(col2);
            col3 = (black/result.getHeight())*100;     
            int col1i = (int)col1;
            int col2i = (int)col2;
            int col3i = (int)col3;
            System.out.println(col2i);
            db.execute(con, "INSERT INTO ocr (col1,col2,col3,letter) VALUES ("+col1i+","+col2i+","+col3i+",'"+letter+"') ");

        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static void cropInitial(String File) throws SQLException {

        try {

            File input = new File(File);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            result = result.getSubimage((int)100, (int)0, (int)440, (int)480);
            File output = new File("C:/Users/Yee/Documents/NetBeansProjects/OCR/test-10.png");
            ImageIO.write(result, "png", output);
            System.out.println("was ");
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static char OCR(String File, Connection con, Database db) throws SQLException{
        int[] confidence = new int[26];
        for(int c = 65; c < 91; c++){
            String query = "SELECT AVG(col1), AVG(col2), AVG(col3) from ocr WHERE Letter='"+(char)c+"'";
   //         String query = "SELECT AVG(col1, col2, col3) from ocr WHERE Letter='"+(char)c+"'";
            int arr[] = db.retrieve(con, query);
            confidence[c - 65] = guess(File, arr[0], arr[1], arr[2]);
        }
        int best = 10000, bestNdx = 10000;
        int bestNdx2 = 66;
        for(int i = 0; i < 26; i++){
            if(confidence[i] < best){
                bestNdx2 = bestNdx;
                best = confidence[i];
                bestNdx = i + 65;
            }
        }
        System.out.println((char)bestNdx);
        return (char)bestNdx;
    }

    public static int checkIfCapable(String File) throws SQLException {
        try {

            File input = new File(File);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            int capable = 1;

            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(0, i));
                if(c.getBlue() < 80){
                    capable = 0;
                    break;
                }
            }
            
            for (int i = 0; i < result.getWidth(); i++) {
                Color c = new Color(result.getRGB(i, 0));
                if(c.getBlue() < 80){
                    capable = 0;
                    break;
                }
            }
            
            for (int i = 0; i < result.getWidth(); i++) {
                Color c = new Color(result.getRGB(i, result.getHeight()-1));
                if(c.getBlue() < 80){
                    capable = 0;
                    break;
                }
            }
                        
            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(result.getWidth()-1, i));
                if(c.getBlue() < 80){
                    capable = 0;
                    break;
                }
            }
            System.out.println("woahwtf");
            return capable;

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return 1000;
    }


    public static int guess(String File, int c1, int c2, int c3) throws SQLException {
        try {

            File input = new File(File);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            float col1 = 0, col2 = 0, col3 = 0, black = 0;
            int j = (int) (result.getWidth()*0.10);
            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(j, i));
                if(c.getBlue() < 80){
                    black++;
                }
            }
            col1 = (black/result.getHeight())*100;
            black = 0;
            j = (int) (result.getWidth()*0.50);
            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(j, i));
                if(c.getBlue() < 80)
                {
                    black++;
                }
            }
            col2 = (black/result.getHeight())*100;
            black = 0;
            j = (int) (result.getWidth()*0.90);
            for (int i = 0; i < result.getHeight(); i++) {
                Color c = new Color(result.getRGB(j, i));
                if(c.getBlue() < 80)
                {
                    black++;
                }
            }
            col3 = (black/result.getHeight())*100;     
            int col1i = (int)col1;
            int col2i = (int)col2;
            int col3i = (int)col3;
            
            return Math.abs(col1i - c1) + Math.abs(col2i - c2) + Math.abs(col3i - c3);

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return 1000;
    }
}
