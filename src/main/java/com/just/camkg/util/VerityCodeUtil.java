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

	// 1、设置字体类型
	private String[] fontNames = {"Time News Roman","楷体"};
	// 2、设置验证码的字符集
	private String charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	// 3、生成验证码图片的背景颜色
	private Color bgColor = new Color(255,255,255);
	// 4、实际生成的验证码文本
	private String text;
	// 5、生成代码的随机颜色-返回一个Color对象，下次每生成一个字母就调用一次
	private Color randomColor(){
		int red = r.nextInt(150);
		int green = r.nextInt(150);
		int blue = r.nextInt(150);
		return new Color(red,green,blue);
		}
	// 6、生成随机的字体
	private Font randomFont() {
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[index];
		int stytle = r.nextInt(4);// 生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
		//int size = r.nextInt(5);//用来生成随机字号的，不喜欢
		return new Font(fontName,0,24);
	}
	
	// 7、画出干扰线
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
		return this.getText(); //这样的好处在于只要输入一个变量，调用一次函数即可
	}
	 
}
