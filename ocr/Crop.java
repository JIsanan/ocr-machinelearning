package ocr;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joshua
 */
public class Crop {
    public static int crop(String File){
        int retval = 1;
        try {

            File input = new File(File);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = (Graphics2D)result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            
            int left = image.getWidth() - 1;
            int right = 1;
            int up = image.getHeight() - 1;
            int down = 1;
            int flag = 0;
            int neoHeight = result.getHeight();
            
            
            int i = result.getHeight() - 1;
            int j = 0;
            int linestartX;
            int linestartY;
            int lineendX = 0;
            int lineendY = 0;
            int tiltRight = 0;
            int flagger = 0;

            
            Color c = new Color(result.getRGB(j, i));
            
            for (i = 0; i < result.getHeight() && flagger != -1; i++) {
                for (j = 0; j < result.getWidth() && flagger != -1; j++) {
                    c = new Color(result.getRGB(j, i));
                    if(c.getBlue() < 100){
                        flagger = 1;
                    }
                    if(flagger > 0){
                        if(c.getBlue() > 100){
                            flagger++;
                        }
                    }
                    if(flagger >= result.getWidth()){
                        flagger = -1;
                    }
                }
            }    
            lineendY = i;
            System.out.println(lineendY);
            
            System.out.println("it reached me");
            for (i = 0; i < lineendY; i++) {
                for (j = 0; j < result.getWidth(); j++) {
                    c = new Color(result.getRGB(j, i));
                    if(c.getBlue() < 100){
                        if(left > j){
                            left = j;
                        }
                        if(right < j){
                            right = j;
                        }
                        if(up > i){
                            up = i;
                        }
                        if(down < i){
                            down = i;
                        }
                    }
                }
            }
            
            System.out.println(up);
            System.out.println(down);
            System.out.println(right);
            System.out.println(left);
            BufferedImage croppedImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            if(up == image.getHeight() - 1 || left == image.getWidth() - 1 || right == 0 || down == 1 || up < 1 || left < 0 || right < 1 || right-left <= 0 || down-up <= 0){
                retval = 0;
            }else{
                croppedImage = result.getSubimage(left, up, (right - left) , (down - up));
            }
            File output = new File(File);
            ImageIO.write(croppedImage, "png", output);
            File output2 = new File("C:/Users/Yee/Documents/NetBeansProjects/OCR/partial.png");
            ImageIO.write(croppedImage, "png", output2);
            
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return retval;
    }
}
