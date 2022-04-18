package com.just.camkg.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.just.camkg.pojo.Account;
//@WebFilter("/*")
public class LoginFliter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO 自动生成的方法存根
		/**
		 * 要注意的是，@WebFilter("/*") 会过滤一些资源，有些和登录是相关的资源应放行*/
		HttpServletRequest req = (HttpServletRequest)request;
		String []passSource = {"/LoginSystemServlet","/LoginSystem.jsp","/img/","/js/","/VerifyCodeServlet"};
		// 获取当前的访问资源
		String currentSource = req.getRequestURL().toString();
		for (String u:passSource) {
			if (currentSource.contains(u)) {
				//确实在访问该资源，放行
				chain.doFilter(req, response);
				return;//没有找到该资源
			}
		}
		HttpSession session = req.getSession();
		Object account = session.getAttribute("account");
		if(account!=null) {
			//已经登录了系统，放行
			chain.doFilter(req, response);
		}else {
			// 说明没有登录，需要转发到登录界面并给出提示登录的信息
			String promptMsg = "请先登录！";
			req.setAttribute("promptMsg", promptMsg);
			req.getRequestDispatcher("/LoginSystem.jsp").forward(req, response);
		}
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}
	
}
