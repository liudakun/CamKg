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
import com.just.camkg.util.GetCypherAllPath;
import com.just.camkg.util.Token;
@WebServlet("/getGraphByCypher")
public class GetGraphByCypher extends HttpServlet {
	private GetCypherAllPath searchUtil =  new GetCypherAllPath();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1、从post 请求中解析查询实体和关系的JSON字符串
		BufferedReader reader = req.getReader();
		String params = reader.readLine();
		//System.out.println(params);
		
		// 2、转为JSONObject对象
		JSONObject paramsObject = JSON.parseObject(params);
		System.out.println(paramsObject.toString());
		String entity1Type = paramsObject.getString("entity1Type");
		String entity1Name = paramsObject.getString("entity1Name");
		
		// 3、 用实体关系设置Cypher语句
		String cypher = "match p= (n:"+ entity1Type + ")-[r]->(m) where n.name ='"+entity1Name +"' return p";
		searchUtil.getNodesInfo(cypher);
		JSONObject relationJson = new JSONObject(searchUtil.getRelationMap());
		JSONObject nodeJson = new JSONObject(searchUtil.getNodeMap());
	
		// 4、获取Cypher查询结果，将得到的Node和RelationShip的查询结果合并未一个JSONObject对象
		JSONObject returnJson = new JSONObject();
		returnJson.putAll(nodeJson);
		returnJson.putAll(relationJson);
		
		// 5、将Cypher查询得到的JSON转为字符串发送到前端
		String returnJsonString = returnJson.toJSONString();
		//System.out.println("总的查询结果:"+ returnJsonString);
	
		// 6、发送到前端
		resp.setContentType("application / json;charset = utf-8");
		resp.getWriter().write(returnJsonString);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
