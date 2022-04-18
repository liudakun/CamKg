package com.just.camkg.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.just.camkg.util.Token;
import com.just.camkg.util.VerityCodeUtil;
@WebServlet("/loginVerifyCodeServlet")
public class VertifyCodeServlet extends HttpServlet {
	private VerityCodeUtil verityCodeUtil = new VerityCodeUtil();
	private String verifyCode =null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// set verityCodeImage path
		ServletOutputStream os = resp.getOutputStream();
		//File file = new File("src/main/webapp/img/verifyCodeImage.jpeg");
		//OutputStream os = new FileOutputStream(file);
		//VerityCodeUtil util = new VerityCodeUtil();
		try {
			verifyCode =verityCodeUtil.outputImage(os) ;
			
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
