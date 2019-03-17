package ocr;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
public class Skew {
    public static void skew(String File){
        try {

            File input = new File(File);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphic = (Graphics2D)result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
  //          graphic.setBackground(Color.WHITE);
  //          graphic.fillRect(0,0,400,200);
            
            int tiltRight = 0;
            int linestartX = result.getWidth();
            int linestartY = result.getHeight();
            int lineendX = result.getWidth();
            int lineendY = result.getHeight();
            int flagger = 0;
            double angle = 0;
            for (int i = result.getHeight() - 1; i > 0 && flagger == 0; i--) {
                for (int j = result.getWidth() - 1; j > 0 && flagger == 0; j--) {
                    Color c = new Color(result.getRGB(j, i));
                    if(c.getBlue() < 50){
                        System.out.println("woah");
                        if(j > result.getWidth()/2){
                            tiltRight = 0;
                        }else{
                            tiltRight = 1;
                        }
                        linestartX = j;
                        linestartY = i;
                        int x = j;
                        int newY = i;
                        for(int y = i; flagger == 0;){
                            x = (tiltRight == 0) ? x-1 : x+1 ;
                            int blackfound = 0;
                            for(y = newY;y > (newY - 20) && blackfound == 0;){
                                Color b = new Color(result.getRGB(x, y));
                                if(b.getBlue() < 50){
                                   System.out.println(y);
                                   blackfound = 1;
                                }else{
                                    y--;
                                }
                            }
                            if(blackfound == 0){
                                lineendX = x;
                                lineendY = newY;
                                flagger = 1;
                                if(tiltRight == 0){
                                   angle = Math.sqrt(Math.pow((linestartX - x), 2) + Math.pow((linestartY - newY), 2));
                                }else{
                                   angle = Math.sqrt(Math.pow((x - linestartX), 2) + Math.pow((linestartY - newY), 2));
                                }
                                angle = (linestartY - newY)/angle;
                                angle = Math.asin(angle);  
                                if(tiltRight == 0){
                                    angle = -angle;
                                }
                            }else{
                                newY = y;
                            }
                        }
                    }
                }
            }
            System.out.println(lineendX);
            System.out.println(lineendY);
            System.out.println(linestartX);
            System.out.println(linestartY);
            System.out.println(angle);
            AffineTransform transform = new AffineTransform();
            transform.rotate(angle, result.getWidth()/2, result.getHeight()/2);

            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            result = op.filter(result, null);

            File output = new File(File);
            ImageIO.write(result, "png", output);

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
