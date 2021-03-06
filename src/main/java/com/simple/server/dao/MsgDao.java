package com.simple.server.dao;

import java.util.List;
import org.hibernate.Session;

import com.simple.server.domain.SysMessage;
import com.simple.server.domain.contract.IContract;

public interface MsgDao {
	void send(IContract message) throws Exception;
	void send(SysMessage message) throws Exception;
	void batchInsertBus(List<IContract> msgList) throws Exception;	
	void insertBus(List<IContract> msgList) throws Exception;
	void insert(IContract msg) throws Exception;
	Session currentSession() throws Exception;
	void batchInsertSys(List<SysMessage> msgList) throws Exception;
	List<IContract> readAll(IContract msg) throws Exception;
	List<IContract> readbySQLCriteria(IContract msg, String sql) throws Exception;
	void insertOrUpdate(IContract msg) throws Exception;
}
