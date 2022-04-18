package com.just.camkg.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.just.camkg.dao.AccountMapper;
import com.just.camkg.pojo.Account;
import com.just.camkg.util.SqlSessionFactoryUtil;

public class AccountService {
	private static SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
	//  根据account对象查询数据库
	public Account selectByAccount(Account account) {
		SqlSession sqlSession = sqlSessionFactory.openSession();//创建会话对象
		AccountMapper mapper = sqlSession.getMapper(AccountMapper.class);
		Account acc = mapper.selectByAccount(account);
		//执行事务
		sqlSession.commit();
		//关闭会话对象
		sqlSession.close();
		return acc;
	}
}
