package com.just.camkg.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.neo4j.driver.v1.Record;

public class GetCypherAllNodesInfo {
private static Driver driver;
private Map<String,Object>nodeInfoMap = new LinkedHashMap();


	public Map<String, Object> getNodeInfoMap() {
	return nodeInfoMap;
}

	static {
		//default port is 7687
		String uri="bolt://localhost:7687";
		String username = "neo4j";
		String password = "liu197917";
		driver = GraphDatabase.driver(uri,AuthTokens.basic(username,password));
	}
	public static void close() {
		 driver.close();
	}
	
	// 获取节点的Info信息
	public  void getNodesInfo(String cypher){
		try(Session session = driver.session()){
			StatementResult result = session.run(cypher);
			while(result.hasNext()){
				Record record = result.next();
				List<Value> value = record.values(); 
				for(Value i:value){
				Node node = i.asNode();
				this.nodeInfoMap = node.asMap();
				
	       }
		}
	  }
			//close();
	}

}
