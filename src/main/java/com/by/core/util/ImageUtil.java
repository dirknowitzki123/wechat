package com.by.core.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Encoder;

/**
 * @author hemf
 * 图片工具类
 */
@SuppressWarnings({"unused","static-access","resource"})
public class ImageUtil {

	private Log log = LogFactory.getLog(ImageUtil.class);

	/**
	 * 图片转base64字节字符串
	 * @param image 图片
	 * @return
	 */
	public static String image2Base64(File image) {
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			StringBuilder sb = new StringBuilder();
			InputStream input = new FileInputStream(image);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];
			for (int len = input.read(temp); len != -1; len = input.read(temp)) {
				out.write(temp, 0, len);
				sb.append(out.toByteArray());
				out.reset();
			}
			out.flush();
			out.close();
			
			return encoder.encodeBuffer(sb.toString().getBytes("UTF-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 缩放文件
	 * @param srcImage
	 * @param destImage
	 * @param width
	 * @param height
	 * @return
	 */
	public static File zoom( File srcImage, File destImage, Integer width, Integer height ) {
		
		if ( !srcImage.exists() || !srcImage.isFile()
				|| !destImage.exists() || !destImage.isFile() ) {
			return destImage;
		}
		
		Double wr = 0d ,hr = 0d;  
		try {	
			BufferedImage bufImg = ImageIO.read(srcImage);  
			wr = width * 1.0 / bufImg.getWidth();  
			hr = height * 1.0 / bufImg.getHeight();
			
			if ( wr > 1 ) {
				width = bufImg.getWidth();
			}
			if ( hr > 1 ) {
				height = bufImg.getHeight();
			}
			
			if ( wr >= 1 && hr >= 1 ) {
				FileUtils.copyFile(srcImage, destImage);
			} else {
				Image Itemp = bufImg.getScaledInstance(width, height, bufImg.SCALE_SMOOTH);
				AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);  
				Itemp = ato.filter(bufImg, null);  
				ImageIO.write((BufferedImage)Itemp, "jpg", destImage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destImage;

	}

	public static InputStream zoom( InputStream srcIS, Integer width, Integer height) {
		return zoom(srcIS, width, height, "jpg");
	}
	
	public static InputStream zoom( InputStream srcIS, Integer width, Integer height, String extension ) {
		InputStream destIS = null;
		if ( srcIS == null ) {
			return destIS;
		}
		
		Double wr = 0d ,hr = 0d;  
		try {	
			BufferedImage bufImg = ImageIO.read(srcIS);  
			wr = width * 1.0 / bufImg.getWidth();  
			hr = height * 1.0 / bufImg.getHeight();
			
			if ( wr > 1 ) {
				width = bufImg.getWidth();
			}
			if ( hr > 1 ) {
				height = bufImg.getHeight();
			}
			
			if ( wr >= 1 && hr >= 1 ) {
				destIS = srcIS;
			} else {
				
				Image os = bufImg.getScaledInstance(width, height, bufImg.SCALE_SMOOTH);
				
				File file = File.createTempFile("tmp", "zoom." + extension);
				
				Image Itemp = bufImg.getScaledInstance(width, height, bufImg.SCALE_SMOOTH);
				AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);  
				Itemp = ato.filter(bufImg, null);  
				ImageIO.write((BufferedImage)Itemp, extension, file);
				destIS = new FileInputStream(file);
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destIS;

	}
	
	public static void main(String[] args) {
		File srcImage = new File("e:/image/3.jpg");
		File destImage = new File("e:/image/"+UUID.randomUUID()+".jpg");
		try {
			destImage.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer width = 120;
		Integer height = 120;
		zoom(srcImage, destImage, width, height);
		
		String base64 = image2Base64(srcImage);
		
		System.out.println("data:image/png;base64," + base64);
	}
	
}
