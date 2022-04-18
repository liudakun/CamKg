package com.just.camkg.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.Driver;
 
public class GraphDatabaseConnection implements AutoCloseable {
	private final Driver driver;
	
	public GraphDatabaseConnection() {
		//default port is 7687
		String uri="bolt://localhost:7687";
		String username = "neo4j";
		String password = "liu197917";
		driver = GraphDatabase.driver(uri,AuthTokens.basic(username,password));
	}
	public void close() {
		 driver.close();
	}
	
	public void printGreeting( final String message ) {
	    try ( Session session = driver.session() ) {
	    	String greeting = session.writeTransaction( new TransactionWork<String>()
	            {
	                @Override
	                public String execute( Transaction tx )
	                {
	                	String statementTemplate="MATCH (n:Person) RETURN n.name,n.age,n LIMIT 25";
	                    /*StatementResult result = tx.run( "CREATE (a:Greeting) " +
	                                                     "SET a.message = $message " +
	                                                     "RETURN a.message + ', from node ' + id(a)",
	                                                     Values.parameters( "message", message ) );
	                    return result.single().get( 0 ).asString();*/
	                	//String searchRealtion = "match p = (a:Person)-[r:couple]->(b:Person) where a.name = 'liudakun'  return b";
	                	String searchRealtion = "match p= (n:Person)-[r]->(m) where n.name ='liudakun' return p";
	                	
//	                	StatementResult result = tx.run(statementTemplate);
//	                	List<Record> resultList = result.list();
//	                	String resultString="";
//	                	for (Record record : resultList) {
//	                		System.out.println(record.get("n.name")+"\t"+record.get("n.age"));
//	                		//System.out.println(record.get("n").get("name")+"\t"+record.get("n").get("age"));
//	                		
//						}	                	
						//return resultString;  
	                	StatementResult result = tx.run(searchRealtion);
	                	List<String>name = new ArrayList();
	                	while(result.hasNext()) {
	                		Record re = result.next();
	                		List <Value>value = re.values();
	                		for (Value i:value){
								Node node = i.asNode();
								Iterator keys = node.keys().iterator();
								Iterator nodeTypes =  node.labels().iterator();
								String nodeType = nodeTypes.next().toString();
								System.out.println("节点类型:"+nodeType);
								System.out.println("节点属性如下:");
								while(keys.hasNext()) {
									String attrKey = (String) keys.next();
									String attrValue = node.get(attrKey).asString();
									System.out.println(attrKey + "-----" + attrValue);
								}
								
							}
	                		
	                		
	                	}
	                	List<Record> resultList = result.list();
	                	/*
	                	 * 2022.3.27 测试
	                	 * 1、测试查询Neo4j后返回的三元组个数
	                	 * 2、遍历每一个三元组*/
	                	System.err.println("查询得到的三元组个数："+resultList.size());
	                	String resultString="";
	                	for (Record record : resultList) {
	                		//System.out.println(record.get("b").get("age"));
	                		//System.out.println(record.get("n").get("name")+"\t"+record.get("n").get("age"));
	                		System.out.println(record.get("name"));
	                		
						}
	                	return resultString; 
	                	
	                }
	            } );
	            System.out.println( greeting );
	            
	        }
	    }
	 
	 public static void main( String... args ) throws Exception
	    {
	        try ( GraphDatabaseConnection greeter = new GraphDatabaseConnection() )
	        {
	            greeter.printGreeting( "hello, world" );
	        }
	    }
}