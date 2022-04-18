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
import javax.servlet.http.HttpServletResponse;

import com.just.camkg.util.Token;
@WebFilter("/*")
public class VueLoginFliter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO 自动生成的方法存根
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;

		// 获取客户端请求的资源路径
		String reqURL = req.getRequestURL().toString();
		       //System.out.println(reqURL);
		// 判断当前路径中是否包含Login路径
		if(reqURL.contains("login")) {
			// 放行
			chain.doFilter(req, resp);
		}else {
			
			// 说明访问路径中不包含login，需要验证Token
			String authorization = req.getHeader("Authorization");
			
			//System.out.println(authorization);
			if(authorization!=null) {
				// 有token值，但是不一定是正确的
				String secretKey = "abcdefghijklmnopqrstuvwxyz";
				boolean verifyResult = Token.verifyTokenWithHMAC256(authorization,secretKey);
				String username =null;
				try {
					username = Token.verifyToken(authorization);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				//System.out.println("用户姓名"+username);
				if(username!=null||verifyResult == true) {
					// 说明验证结果准确，放行
					chain.doFilter(req, resp);
				}else {
					// 说明token错误，状态码为401代表未授权
					resp.setStatus(401);
                    //System.out.println("token无效，401");
				}
			}else {
				resp.setStatus(401);
				//System.out.println("无token!!");
			}
		}
		
		
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

}
