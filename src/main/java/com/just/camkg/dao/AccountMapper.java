package com.just.camkg.dao;

import com.just.camkg.pojo.Account;

public interface AccountMapper {
	//1、根据用户名字和密码查询数据库Account表
	Account selectByAccount(Account account);
}
