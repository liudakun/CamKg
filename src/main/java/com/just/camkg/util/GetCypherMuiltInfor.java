package com.just.camkg.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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

public class GetCypherMuiltInfor {
	private static Driver driver;
	public static Map<String, Object> tripleMap = new  LinkedHashMap();
	
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
	public  void getMuiltGraphInfo(String cypher){
		try(Session session = driver.session()){
			StatementResult result = session.run(cypher); //获取查询结果值
			// 打印看看
			LinkedHashSet<String>tripleSet = new LinkedHashSet();
			while(result.hasNext()) {
				Record record = result.next();
				//System.out.println(record);
				List<Value> value = record.values();   //查询记录值
				for(Value i:value){
				Path path = i.asPath();
				/* 如果一个路径中存在多个三元组可以使用这种方式*/
				Iterator<Segment> ss = path.iterator();
				while(ss.hasNext()) {
					Segment seg = ss.next();
					Node stNode = seg.start();
					Node edNode = seg.end();
					String stinf = stNode.get("name").asString() + "_" +
							stNode.labels().iterator().next().toString();
					//String stNodeType = stNode.labels().iterator().next().toString();
					//System.out.println(stNodeType);
					String resinf = seg.relationship().type();
					String eninf = edNode.get("name").asString() + "_" +
							 edNode.labels().iterator().next().toString();
					String triple = stinf + "/" + resinf + "/" + eninf;
					tripleSet.add(triple);
					
					//System.out.println(triple);
//					System.out.println(stNode.get("name").asString());
//					System.out.println(seg.relationship().type());
//					System.out.println(edNode.get("name").asString());
//					
				}
				
				}
			
			}
			
			// 将获取到的结果存储的Map集合中
			Integer tempIndex = 0;
			for(String triple : tripleSet) {
				this.tripleMap.put(triple, (Object)tempIndex);
				tempIndex++;
			}
			
	  }
			// this.close(); //关闭驱动 我们希望用户手动关闭，否则只用一次就报错
	}
}
