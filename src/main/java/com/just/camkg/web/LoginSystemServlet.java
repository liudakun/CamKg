package com.just.camkg.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.just.camkg.dao.AccountMapper;
import com.just.camkg.pojo.Account;
import com.just.camkg.service.AccountService;

@WebServlet("/LoginSystemServlet")
public class LoginSystemServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1、获取jsp登陆界面的账户名和密码,并分装成account对象
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(name);
		Account acc = new Account();
		acc.setName(name);
		acc.setPassword(password);
		//System.out.println(acc);
		// 2、查询
		AccountService service = new AccountService();
		Account checkAccount = service.selectByAccount(acc);
		//System.out.println(checkAccount);
		
		if (checkAccount!=null) {
			//登陆成功 --将登陆成功的账户和密码
			String remember = req.getParameter("remember");
			if("1".equals(remember)) {
				// 说明勾选了按钮
				Cookie setUsername = new Cookie("username",name);
				setUsername.setMaxAge(60*60*24*7);
				resp.addCookie(setUsername);
				Cookie setPassword = new Cookie("password",password);
				setPassword.setMaxAge(60*60*24*7);
				resp.addCookie(setPassword);
			}
			// 将登录成功后的Account对象存储到Session中
			HttpSession session = req.getSession();
			session.setAttribute("account", acc);
			// 重定向到功能界面
			 String contextPath = req.getContextPath();
			 resp.sendRedirect(contextPath+"/Menu.html");
		}
		else {
			//登陆失败-返回错误信息，跳回至原登陆界面 
			//req.setAttribute(name, checkAccount);
			System.out.println("登陆失败！");
			//String contextPath = req.getContextPath();
			req.setAttribute("error", "账号或密码有误，请检查！");//在request中存储的数据只能通过转发的方式才能获取到
			//resp.sendRedirect(contextPath+"/html"+"/LoginSystem.jsp");
			req.getRequestDispatcher("/LoginSystem.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
