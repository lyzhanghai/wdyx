// CharacterExtractor.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocrPlugins;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sourceforge.javaocr.scanner.DocumentScanner;
import net.sourceforge.javaocr.scanner.DocumentScannerListenerAdaptor;
import net.sourceforge.javaocr.scanner.PixelImage;

/**
 * Saves all the characters in an image to an output directory individually. 
 * @author William Whitney
 */
public class CharacterExtractor extends DocumentScannerListenerAdaptor
{
	protected transient final Logger logger = Logger.getLogger(this.getClass());

    private DocumentScanner documentScanner = new DocumentScanner();
    private BufferedImage targetImage = null;
    private int std_width;
    private int std_height;
    private List<BufferedImage> slices = null;
    
    public List<BufferedImage> slice(BufferedImage targetImage,int std_width,int std_height)
    {
    	slices = new ArrayList<BufferedImage>();
        try
        {
            this.targetImage = targetImage;
            this.std_width = std_width;
            this.std_height = std_height;
            PixelImage pixelImage = new PixelImage(targetImage);
            pixelImage.toGrayScale(true);
            pixelImage.filter();
            documentScanner.scan(pixelImage, this, 0, 0, pixelImage.width, pixelImage.height);
        }
        catch (Exception ex)
        {
        	logger.error("错误：",ex);
        }
        return slices;
    }

    @Override
    public void processChar(PixelImage pixelImage, int x1, int y1, int x2, int y2, int rowY1, int rowY2)
    {
        try
        {
            int areaW = x2 - x1;
            int areaH = y2 - y1;
            
            //Extract the character
            BufferedImage characterImage = targetImage.getSubimage(x1, y1, areaW, areaH);
            if(std_width<=0||std_height<=0){
            	slices.add(characterImage);
            }else{
            	//Scale image so that both the height and width are less than std size
                if (characterImage.getWidth() > std_width)
                {
                    //Make image always std_width wide
                    double scaleAmount = (double) std_width / (double) characterImage.getWidth();
                    AffineTransform tx = new AffineTransform();
                    tx.scale(scaleAmount, scaleAmount);
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                    characterImage = op.filter(characterImage, null);
                }

                if (characterImage.getHeight() > std_height)
                {
                    //Make image always std_height tall
                    double scaleAmount = (double) std_height / (double) characterImage.getHeight();
                    AffineTransform tx = new AffineTransform();
                    tx.scale(scaleAmount, scaleAmount);
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                    characterImage = op.filter(characterImage, null);
                }

                //Paint the scaled image on a white background
                BufferedImage normalizedImage = new BufferedImage(std_width, std_height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = normalizedImage.createGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, std_width, std_height);

                //Center scaled image on new canvas
                int x_offset = (std_width - characterImage.getWidth()) / 2;
                int y_offset = (std_height - characterImage.getHeight()) / 2;

                g.drawImage(characterImage, x_offset, y_offset, null);
                g.dispose();

                //Add slice to list
                slices.add(normalizedImage);
            }
        }
        catch (Exception ex)
        {
            logger.error("错误：",ex);
        }
    }
    
}
