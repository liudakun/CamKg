package com.just.camkg.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.just.camkg.dao.AccountMapper;
import com.just.camkg.pojo.Account;
import com.just.camkg.util.SqlSessionFactoryUtil;

public class AccountService {
	private static SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
	//  ����account�����ѯ���ݿ�
	public Account selectByAccount(Account account) {
		SqlSession sqlSession = sqlSessionFactory.openSession();//�����Ự����
		AccountMapper mapper = sqlSession.getMapper(AccountMapper.class);
		Account acc = mapper.selectByAccount(account);
		//ִ������
		sqlSession.commit();
		//�رջỰ����
		sqlSession.close();
		return acc;
	}
}
