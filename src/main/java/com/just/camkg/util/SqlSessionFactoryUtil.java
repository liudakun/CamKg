package com.just.camkg.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryUtil {
	private static SqlSessionFactory sqlSessionFactory = null;
	// ��̬�����
	static {
		String source = "mybatis-config.xml";
		try {
			InputStream is = Resources.getResourceAsStream(source);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			System.out.println("�����ļ������쳣,�޷���ȷ����SqlSessionFactory����");
			e.printStackTrace();
		}
		
	}
	//���ؼ��ص�sqlSessionFactory����
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
