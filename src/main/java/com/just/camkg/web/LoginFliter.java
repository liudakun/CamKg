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
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO �Զ����ɵķ������
		/**
		 * Ҫע����ǣ�@WebFilter("/*") �����һЩ��Դ����Щ�͵�¼����ص���ԴӦ����*/
		HttpServletRequest req = (HttpServletRequest)request;
		String []passSource = {"/LoginSystemServlet","/LoginSystem.jsp","/img/","/js/","/VerifyCodeServlet"};
		// ��ȡ��ǰ�ķ�����Դ
		String currentSource = req.getRequestURL().toString();
		for (String u:passSource) {
			if (currentSource.contains(u)) {
				//ȷʵ�ڷ��ʸ���Դ������
				chain.doFilter(req, response);
				return;//û���ҵ�����Դ
			}
		}
		HttpSession session = req.getSession();
		Object account = session.getAttribute("account");
		if(account!=null) {
			//�Ѿ���¼��ϵͳ������
			chain.doFilter(req, response);
		}else {
			// ˵��û�е�¼����Ҫת������¼���沢������ʾ��¼����Ϣ
			String promptMsg = "���ȵ�¼��";
			req.setAttribute("promptMsg", promptMsg);
			req.getRequestDispatcher("/LoginSystem.jsp").forward(req, response);
		}
	}

	@Override
	public void destroy() {
		// TODO �Զ����ɵķ������
		
	}
	
}
