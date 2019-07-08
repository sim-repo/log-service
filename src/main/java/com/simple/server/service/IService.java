package com.simple.server.service;

import java.util.List;

import com.simple.server.domain.SysMessage;
import com.simple.server.domain.contract.IContract;


public interface IService {
	void send(IContract message) throws Exception;
	void insertBus(List<IContract> msgList) throws Exception;
	void insertOrUpdate(IContract msg) throws Exception;
	void insert(List<IContract> msgList) throws Exception;
	void insert(IContract msg) throws Exception;
	void send(SysMessage message) throws Exception;
	void insertSys(List<SysMessage> msg) throws Exception;	
	List<IContract> readAll(IContract msg) throws Exception;
	List<IContract> readbySQLCriteria(IContract msg, String sql) throws Exception;
}
