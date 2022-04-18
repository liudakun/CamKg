package com.just.camkg.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.just.camkg.util.GetCypherAllNodesInfo;
@WebServlet("/getNodeInfoByCypher")
public class GetNodeInfoByCypher extends HttpServlet {
	private GetCypherAllNodesInfo searchUtil =  new GetCypherAllNodesInfo();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取节点的名称和类型
		BufferedReader reader = req.getReader();
		String params = reader.readLine();
		//System.out.println(params);
		
		// 2、转为JSONObject对象
		JSONObject paramsObject = JSON.parseObject(params);
		System.out.println(paramsObject.toString());
		String nodeType = paramsObject.getString("nodeType");
		String nodeName = paramsObject.getString("nodeName");
		System.out.println("nodeType" + nodeType);
		System.out.println("nodeName" + nodeName);
		String cypher = "match (n:" + nodeType + ") where n.name ='" + nodeName + "' return n";
		searchUtil.getNodesInfo(cypher);
		System.out.println("节点的信息");
		System.out.println(searchUtil.getNodeInfoMap() );
		// 发送到前端
		JSONObject returnJson = new JSONObject(searchUtil.getNodeInfoMap());
		String returnJsonString = returnJson.toJSONString();
		resp.setContentType("application / json;charset = utf-8");
		resp.getWriter().write(returnJsonString);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
