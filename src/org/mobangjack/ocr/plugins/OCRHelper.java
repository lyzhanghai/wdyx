package org.mobangjack.ocr.plugins;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sourceforge.javaocr.ocrPlugins.CharacterExtractor;

/**
 * OCRHelper class
 * @author 帮杰
 *
 */
public class OCRHelper {

	protected transient final Logger logger = Logger.getLogger(this.getClass());
	
	private BufferedImage image;

	private int iw, ih;

	private int[] pixels;
	
	public OCRHelper(BufferedImage image) {
		this.image = image;
		iw = image.getWidth();
		ih = image.getHeight();
		pixels = new int[iw * ih];
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			logger.error("error:", e);
		}
	}
	
	public BufferedImage getImage(){
		return this.image;
	}
	
	public OCRHelper monochrome(int th) {
		ColorModel cm = ColorModel.getRGBdefault();
		for (int i = 0; i < iw * ih; i++) {
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]);
			if (cm.getRed(pixels[i]) > th) {
				red = 255;
			} else {
				red = 0;
			}

			if (cm.getGreen(pixels[i]) > th) {
				green = 255;
			} else {
				green = 0;
			}

			if (cm.getBlue(pixels[i]) > th) {
				blue = 255;
			} else {
				blue = 0;
			}

			pixels[i] = alpha << 24 | red << 16 | green << 8 | blue;
		}
		this.image = ImageIOHelper.imageProducerToBufferedImage(new MemoryImageSource(iw, ih, pixels, 0, iw));
		return this;
	}

	public OCRHelper medianX() {
		ColorModel cm = ColorModel.getRGBdefault();
		for (int i = 1; i < ih - 1; i++) {
			for (int j = 1; j < iw - 1; j++) {
				int red, green, blue;
				int alpha = cm.getAlpha(pixels[i * iw + j]);
	
				int red4 = cm.getRed(pixels[i * iw + j - 1]);
				int red5 = cm.getRed(pixels[i * iw + j]);
				int red6 = cm.getRed(pixels[i * iw + j + 1]);
	
				if (red4 >= red5) {
					if (red5 >= red6) {
						red = red5;
					} else {
						if (red4 >= red6) {
							red = red6;
						} else {
							red = red4;
						}
					}
				} else {
					if (red4 > red6) {
						red = red4;
					} else {
						if (red5 > red6) {
							red = red6;
						} else {
							red = red5;
						}
					}
				}
	
				int green4 = cm.getGreen(pixels[i * iw + j - 1]);
				int green5 = cm.getGreen(pixels[i * iw + j]);
				int green6 = cm.getGreen(pixels[i * iw + j + 1]);
	
				if (green4 >= green5) {
					if (green5 >= green6) {
						green = green5;
					} else {
						if (green4 >= green6) {
							green = green6;
						} else {
							green = green4;
						}
					}
				} else {
					if (green4 > green6) {
						green = green4;
					} else {
						if (green5 > green6) {
							green = green6;
						} else {
							green = green5;
						}
					}
				}
	
				int blue4 = cm.getBlue(pixels[i * iw + j - 1]);
				int blue5 = cm.getBlue(pixels[i * iw + j]);
				int blue6 = cm.getBlue(pixels[i * iw + j + 1]);
	
				if (blue4 >= blue5) {
					if (blue5 >= blue6) {
						blue = blue5;
					} else {
						if (blue4 >= blue6) {
							blue = blue6;
						} else {
							blue = blue4;
						}
					}
				} else {
					if (blue4 > blue6) {
						blue = blue4;
					} else {
						if (blue5 > blue6) {
							blue = blue6;
						} else {
							blue = blue5;
						}
					}
				}
				pixels[i * iw + j] = alpha << 24 | red << 16 | green << 8 | blue;
			}
		}
		this.image = ImageIOHelper.imageProducerToBufferedImage(new MemoryImageSource(iw, ih, pixels, 0, iw));
		return this;
	}
	
	public OCRHelper medianY() {
		ColorModel cm = ColorModel.getRGBdefault();
		for (int j = 1; j < iw - 1; j++) {
			for (int i = 1; i < ih - 1; i++) {
				int red, green, blue;
				int alpha = cm.getAlpha(pixels[i * iw + j]);

				int red4 = cm.getRed(pixels[(i - 1) * iw + j]);
				int red5 = cm.getRed(pixels[i * iw + j]);
				int red6 = cm.getRed(pixels[(i + 1) * iw + j]);

				if (red4 >= red5) {
					if (red5 >= red6) {
						red = red5;
					} else {
						if (red4 >= red6) {
							red = red6;
						} else {
							red = red4;
						}
					}
				} else {
					if (red4 > red6) {
						red = red4;
					} else {
						if (red5 > red6) {
							red = red6;
						} else {
							red = red5;
						}
					}
				}

				int green4 = cm.getGreen(pixels[(i - 1) * iw + j]);
				int green5 = cm.getGreen(pixels[i * iw + j]);
				int green6 = cm.getGreen(pixels[(i + 1) * iw + j]);

				if (green4 >= green5) {
					if (green5 >= green6) {
						green = green5;
					} else {
						if (green4 >= green6) {
							green = green6;
						} else {
							green = green4;
						}
					}
				} else {
					if (green4 > green6) {
						green = green4;
					} else {
						if (green5 > green6) {
							green = green6;
						} else {
							green = green5;
						}
					}
				}

				int blue4 = cm.getBlue(pixels[(i - 1) * iw + j]);
				int blue5 = cm.getBlue(pixels[i * iw + j]);
				int blue6 = cm.getBlue(pixels[(i + 1) * iw + j]);

				if (blue4 >= blue5) {
					if (blue5 >= blue6) {
						blue = blue5;
					} else {
						if (blue4 >= blue6) {
							blue = blue6;
						} else {
							blue = blue4;
						}
					}
				} else {
					if (blue4 > blue6) {
						blue = blue4;
					} else {
						if (blue5 > blue6) {
							blue = blue6;
						} else {
							blue = blue5;
						}
					}
				}
				pixels[i * iw + j] = alpha << 24 | red << 16 | green << 8 | blue;
			}
		}
		this.image = ImageIOHelper.imageProducerToBufferedImage(new MemoryImageSource(iw, ih, pixels, 0, iw));
		return this;
	}
	
	public OCRHelper RGB(int r,int g,int b){
		ColorModel cm = ColorModel.getRGBdefault();
		for (int i = 0; i < iw * ih; i++) {
			int alpha = cm.getAlpha(pixels[i]);
			int red = cm.getRed(pixels[i]);
			int green = cm.getGreen(pixels[i]);
			int blue = cm.getBlue(pixels[i]);
			if(red!=r||green!=g||blue!=b){
				red = 255;
				green = 255;
				blue = 255;
			}
			pixels[i] = alpha << 24 | red << 16 | green << 8 | blue;
		}
		this.image = ImageIOHelper.imageProducerToBufferedImage(new MemoryImageSource(iw, ih, pixels, 0, iw));
		return this;
	}
	
	public OCRHelper grey() {
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		this.image = op.filter(image, null);
		return this;
	}
	
	/**coder for single filtered char image*/
	public String coder(){
		String code = "";
		ColorModel cm = ColorModel.getRGBdefault();
		for (int i = 0; i < iw*ih; i++) {
			int red = cm.getRed(pixels[i]);
			int green = cm.getGreen(pixels[i]);
			int blue = cm.getBlue(pixels[i]);
			if(red==green&&red==blue){
				code+=0;
			}else{
				code+=1;
			}
		}
		return code;
	}
	
	/**coder for a whole raw image*/
	public List<String> Coder(){
		BufferedImage bi = this.medianX().medianY().monochrome(100).getImage();
		List<BufferedImage> slices = new CharacterExtractor().slice(bi, Const.STD_WIDTH, Const.STD_HEIGHT);
		List<String> codes = new ArrayList<String>();
		for(BufferedImage slice : slices){
			String code = new OCRHelper(slice).coder();
			codes.add(code);
		}
		return codes;
	}
}
