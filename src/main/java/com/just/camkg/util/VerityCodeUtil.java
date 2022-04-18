package com.just.camkg.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
public class VerityCodeUtil {
	private  int width =131;
	private int height =40;
	private Random r = new Random();
	private int verfyCodeLength = 4;
	public void setVerfyCodeLength(int verfyCodeLength) {
		this.verfyCodeLength = verfyCodeLength;
	}

	// 1��������������
	private String[] fontNames = {"Time News Roman","����"};
	// 2��������֤����ַ���
	private String charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	// 3��������֤��ͼƬ�ı�����ɫ
	private Color bgColor = new Color(255,255,255);
	// 4��ʵ�����ɵ���֤���ı�
	private String text;
	// 5�����ɴ���������ɫ-����һ��Color�����´�ÿ����һ����ĸ�͵���һ��
	private Color randomColor(){
		int red = r.nextInt(150);
		int green = r.nextInt(150);
		int blue = r.nextInt(150);
		return new Color(red,green,blue);
		}
	// 6���������������
	private Font randomFont() {
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[index];
		int stytle = r.nextInt(4);// �����������ʽ, 0(����ʽ), 1(����), 2(б��), 3(����+б��)
		//int size = r.nextInt(5);//������������ֺŵģ���ϲ��
		return new Font(fontName,0,24);
	}
	
	// 7������������
	private void drawLine(BufferedImage image) {
		int num =3;
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		for (int i = 0; i < num; i++) {
			int x1 = r.nextInt(width);
			int y1 = r.nextInt(height);
			int x2 = r.nextInt(width);
			int y2 = r.nextInt(height);
			g2.setStroke(new BasicStroke(1.5F));
			// set disturb line color
			g2.setColor(Color.BLUE);
			g2.drawLine(x1,y1,x2,y2);// draw line	
		}
		
	}
	
	// 8.Get Radom char code
	private char randomChar() {
			int index = r.nextInt(charSet.length());
			return charSet.charAt(index);
	}
	//9. Create BufferedImage
	private BufferedImage createImage() {
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(this.bgColor);
		g2.fillRect(0,0,width,height);
		return image;
	}
	//10. Create VeritfyCodeImage
	 public BufferedImage getImage() {
		BufferedImage image = createImage();// create image buffered areas
		Graphics2D g2 = (Graphics2D)image.getGraphics();//get paint envoiroment
		StringBuilder verifyCode = new StringBuilder();
		for (int i = 0; i < verfyCodeLength; i++) {
			String s = randomChar()+"";
			verifyCode.append(s);
			float x = i*1.0F*width/4;
			g2.setFont(randomFont());
			g2.setColor(randomColor());
			g2.drawString(s,x,height-5);
		}
		this.text = verifyCode.toString();
		drawLine(image);// add distub line
		return image;
	}
	 // 11.return verifyCode text
	private String getText() {
		return text;
	}
	
	//	12.reserve image to the outPutStream
	public  String outputImage(OutputStream out) throws Exception {
		BufferedImage image = this.getImage();
		ImageIO.write(image, "JPEG", out);
		return this.getText(); //�����ĺô�����ֻҪ����һ������������һ�κ�������
	}
	 
}
