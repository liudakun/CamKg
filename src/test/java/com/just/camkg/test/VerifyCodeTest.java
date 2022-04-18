package com.just.camkg.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.just.camkg.util.VerityCodeUtil;

public class VerifyCodeTest {
	public static void main(String[] args) throws Exception {
		File file = new File("src/main/webapp/img/verifyCodeImage.jpeg");
		OutputStream os = new FileOutputStream(file);
		VerityCodeUtil util = new VerityCodeUtil();
		String verifyCode = util.outputImage(os);
		System.out.println(verifyCode);
		
		
		
	}
}
