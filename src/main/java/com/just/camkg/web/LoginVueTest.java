package com.just.camkg.web;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.just.camkg.pojo.Account;
import com.just.camkg.pojo.Person;
import com.just.camkg.service.AccountService;
import com.just.camkg.util.Token;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/loginVueTest")
public class LoginVueTest extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		Account acc = new Account();
		acc.setName(name);
		acc.setPassword(password);
		//System.out.println(acc);
		// 2、查询
		AccountService service = new AccountService();
		Account checkAccount = service.selectByAccount(acc);
		//System.out.println(checkAccount);
		if (checkAccount!=null) {
			// 说明登录成功
			resp.setStatus(202);
			String token = Token.getToken(name);
			resp.setHeader("token", token);
			// 登录成功之后，将信息写入服务器的Session中
		}
		//System.out.println(req.getHeader("Authorization"));
		//req.getRequestDispatcher("/LoginSystem.jsp").forward(req, resp);
		
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
