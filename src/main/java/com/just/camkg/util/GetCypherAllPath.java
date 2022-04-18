package com.just.camkg.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Path.Segment;
import org.neo4j.driver.v1.types.Relationship;

import com.alibaba.fastjson.JSONObject;

import org.neo4j.driver.v1.Record;

public class GetCypherAllPath {
	private static Driver driver;
	public static Map<String,Object>nodeMap = new LinkedHashMap(); // 存放所有节点的Map集合
	public static Map<String,Object>relationMap = new LinkedHashMap(); //存放所有关系的Map集合
	public static Map<String, Object> getNodeMap() {
	return nodeMap;
	}
	public static Map<String, Object> getRelationMap() {
	return relationMap;
	}

	//静态代码块，获取驱动
	static {
		//default port is 7687
		String uri="bolt://localhost:7687";
		String username = "neo4j";
		String password = "liu197917";
		driver = GraphDatabase.driver(uri,AuthTokens.basic(username,password));
	}
	public void close() {
		 driver.close();
	}
	
	// 查询功能
	public void getNodesInfo(String cypher){
		try(Session session = driver.session()){
			StatementResult result = session.run(cypher); //获取查询结果值
		    Map<String,Object> tempNodeMap =new LinkedHashMap();
			Map<String,Object> temprelationMap  = new LinkedHashMap();
			Integer index =0;
			int startHashCode = 0; //起始节点Map的hashCode值
			int endHashCode = 0;   //终止节点Map的hashCode值
			int endNodeCount = 0;  //查询终止节点的数量
			Node startNode = null;
			Node endNode = null;
			// 遍历Cypher查询语句得到的所有path
			while(result.hasNext()) {
				int tempStartHashCode = startHashCode; // 存放上次的节点的HashCode值
				int tempEndHashCode = endHashCode;
				Record record = result.next();
				List<Value> value = record.values();   //查询记录值
				for(Value i:value){
				Path path = i.asPath();
				
				startNode = path.start();  // 起始节点
				/* 2 遍历起始节点
				 * 获取起始节点的HashCode值，因为每个三元组都有起始节点，所以利用HashCode值来避免在tempNodeMap中重复添加*/ 
				startHashCode = startNode.hashCode(); 		// 通过节点Hash值去除重复节点
				if (startHashCode!=tempStartHashCode) {

					Map<String,Object> startNodeMap = new LinkedHashMap(); // 获取起始节点的所有属性，并打包成一个Map集合
					// 2.1 解决startNode.asMap().put()方法出错
					for (String key : startNode.asMap().keySet()) {
						startNodeMap.put(key, startNode.asMap().get(key));
					}
					// 2.2 添加节点的类型
					Iterator startNodeTypes = startNode.labels().iterator();
					String startNodeTypeValue = startNodeTypes.next().toString();
					startNodeMap.put("startNodeType", startNodeTypeValue);
					tempNodeMap.put("startNode", startNodeMap);
				}
				/* 3 遍历终止节点*/
				endNode = path.end();
				endHashCode = endNode.hashCode();
				// 这一步起始不用添加，通常一个路径中只有一个终止节点
				if (endHashCode != tempEndHashCode) {
					endNodeCount++; 
					Map<String,Object> endNodeMap = new LinkedHashMap();   // 获取终止节点的所有属性，并打包成一个Map集合
					for(String key : endNode.asMap().keySet()) {
						endNodeMap.put(key, endNode.asMap().get(key));
					}
					Iterator endNodeTypes = endNode.labels().iterator();
					String endNodeTypeValue = endNodeTypes.next().toString();
					endNodeMap.put("endNodeType", endNodeTypeValue);
					tempNodeMap.put("endNode" + endNodeCount, endNodeMap);
				}
				
				/* 4 获取路径的关系 */
				String pathType =null;
				Iterable<Relationship> re = path.relationships(); 
				for(Relationship res:re) {
					pathType = res.type(); //路径的关系-用路径关系的类型
					temprelationMap.put("relationShip" + endNodeCount, pathType);
				}
	       }
		}

			this.nodeMap = tempNodeMap;
			this.relationMap = temprelationMap;
	  }
			// this.close(); //关闭驱动 我们希望用户手动关闭，否则只用一次就报错
	}
}
