package com.just.camkg.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryUtil {
	private static SqlSessionFactory sqlSessionFactory = null;
	// 静态代码块
	static {
		String source = "mybatis-config.xml";
		try {
			InputStream is = Resources.getResourceAsStream(source);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			System.out.println("配置文件加载异常,无法正确创建SqlSessionFactory对象！");
			e.printStackTrace();
		}
		
	}
	//返回加载的sqlSessionFactory对象
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
