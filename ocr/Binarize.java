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
public class Binarize {
    public static void binarizeMe(String File){
        try {

            File input = new File(File);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = (Graphics2D)result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            
            Color black = new Color(0,0,0);
            Color white = new Color(255,255,255);
            for (int i = result.getHeight() - 1; i > 0 ; i--) {
                for (int j = result.getWidth() - 1; j > 0 ; j--) {
                    Color c = new Color(result.getRGB(j, i));
                    if(c.getBlue() < 80 && c.getRed() < 80 && c.getGreen() < 80){
                        result.setRGB(j, i, black.getRGB());
                    }else{
                        result.setRGB(j, i, white.getRGB());
                    }
                }
            }
            
            File output = new File(File);
            ImageIO.write(result, "png", output);

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
