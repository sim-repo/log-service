package com.simple.server.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.server.domain.SysMessage;
import com.simple.server.domain.contract.IContract;


@Service("msgDao")
@Scope("singleton")
public class MsgDaoImpl implements MsgDao{

	@Autowired
	SessionFactory mysqlSessionFactory;
		
	@Override
	public Session currentSession() throws Exception{				
		return mysqlSessionFactory.getCurrentSession();
		
	}

	@Override
	public void send(IContract message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override	
	public void batchInsertBus(List<IContract> msgList) throws Exception {
		int count=0;
		for(IContract msg: msgList){
			try{							
				currentSession().save(msg);	
			}catch(SQLException e){
				e.printStackTrace();
			}			
			if (++count % 50 == 0 ) {
				currentSession().flush();
				currentSession().clear();
			}
		}
	}
	
	@Override
	public void insert(IContract msg) throws Exception {
		currentSession().save(msg);	
	}

	@Override
	public void insertBus(List<IContract> msgList) throws Exception {
		for(IContract msg: msgList){
			try{					
				currentSession().save(msg);	
				currentSession().flush();
				currentSession().clear();
			}catch(SQLException e){
				e.printStackTrace();
			}					
		}		
	}

	@Override
	public void send(SysMessage message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchInsertSys(List<SysMessage> msgList) throws Exception {
		int count=0;
		for(SysMessage msg: msgList){
			currentSession().save(msg);	
			if (++count % 50 == 0 ) {
				currentSession().flush();
				currentSession().clear();
			}
		}		
	}

	@Override
	@Transactional
	public List<IContract> readAll(IContract msg) throws Exception {
		Criteria criteria = currentSession().createCriteria(msg.getClass());
		List<IContract> res = criteria.list();	
		return res;
	}

	@Override
	@Transactional
	public List<IContract> readbySQLCriteria(IContract msg, String sql) throws Exception {
		Criteria criteria = currentSession().createCriteria(msg.getClass()).add(Restrictions.sqlRestriction(sql));
		List<IContract> res = criteria.list();	
		return res;
	}
	
}
