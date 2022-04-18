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
		// 1����ȡjsp��½������˻���������,����װ��account����
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(name);
		Account acc = new Account();
		acc.setName(name);
		acc.setPassword(password);
		//System.out.println(acc);
		// 2����ѯ
		AccountService service = new AccountService();
		Account checkAccount = service.selectByAccount(acc);
		//System.out.println(checkAccount);
		
		if (checkAccount!=null) {
			//��½�ɹ� --����½�ɹ����˻�������
			String remember = req.getParameter("remember");
			if("1".equals(remember)) {
				// ˵����ѡ�˰�ť
				Cookie setUsername = new Cookie("username",name);
				setUsername.setMaxAge(60*60*24*7);
				resp.addCookie(setUsername);
				Cookie setPassword = new Cookie("password",password);
				setPassword.setMaxAge(60*60*24*7);
				resp.addCookie(setPassword);
			}
			// ����¼�ɹ����Account����洢��Session��
			HttpSession session = req.getSession();
			session.setAttribute("account", acc);
			// �ض��򵽹��ܽ���
			 String contextPath = req.getContextPath();
			 resp.sendRedirect(contextPath+"/Menu.html");
		}
		else {
			//��½ʧ��-���ش�����Ϣ��������ԭ��½���� 
			//req.setAttribute(name, checkAccount);
			System.out.println("��½ʧ�ܣ�");
			//String contextPath = req.getContextPath();
			req.setAttribute("error", "�˺Ż������������飡");//��request�д洢������ֻ��ͨ��ת���ķ�ʽ���ܻ�ȡ��
			//resp.sendRedirect(contextPath+"/html"+"/LoginSystem.jsp");
			req.getRequestDispatcher("/LoginSystem.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
