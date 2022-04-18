package com.just.camkg.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.just.camkg.util.Token;

@WebServlet("/getLoginUserInfo")
public class GetUserInfo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 可以通过解析token的方式获取用户的信息
		String token = req.getHeader("Authorization");
		// 密钥解码之后获取用户名
		String username = null;
		try {
			username = Token.verifyToken(token);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(username!=null) {
			resp.setContentType("text/html; charset = utf-8");
			resp.getWriter().write(username);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
