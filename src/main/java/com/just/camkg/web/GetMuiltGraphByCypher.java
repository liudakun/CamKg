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
import com.just.camkg.util.GetCypherMuiltInfor;
import com.just.camkg.util.Token;
@WebServlet("/getMuiltGraphByCypher")
public class GetMuiltGraphByCypher extends HttpServlet {
	private GetCypherMuiltInfor searchUtil =  new GetCypherMuiltInfor();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1、从post 请求中解析查询实体和关系的JSON字符串
		BufferedReader reader = req.getReader();
		String params = reader.readLine();
		//System.out.println(params);
		
		JSONObject paramsObject = JSON.parseObject(params);
		System.out.println(paramsObject.toString());
		String entity1Type = paramsObject.getString("entity1Type");
		String entity1Name = paramsObject.getString("entity1Name");
		String relationshipLayer = paramsObject.getString("RelationshipLayler");
		System.out.println(relationshipLayer);
		
		// 3、 用实体关系设置Cypher语句并查询
		String cypher = "match p= (n:"+ entity1Type + ")-[*1.."+relationshipLayer + "]->(m) where n.name ='"+ entity1Name +"' return p";
		
		searchUtil.getMuiltGraphInfo(cypher);
		System.out.println(cypher);
		System.out.println(searchUtil.tripleMap.toString());
		//4、转为JSONObject对象
		JSONObject allPath = new JSONObject(searchUtil.tripleMap); //调用查询语句，获取所有三元组信息
		
		// 5、发送到前端
		resp.setContentType("application / json;charset = utf-8");
		resp.getWriter().write(allPath.toJSONString());
		System.out.println(allPath.toJSONString());
		searchUtil.tripleMap.clear();
	
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
