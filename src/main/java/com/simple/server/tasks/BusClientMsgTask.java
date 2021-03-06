package com.simple.server.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.simple.server.config.AppConfig;
import com.simple.server.config.RoleType;
import com.simple.server.domain.AContract;
import com.simple.server.domain.contract.BusReplyMsg;
import com.simple.server.domain.contract.BusWriteMsg;
import com.simple.server.domain.contract.IContract;
import com.simple.server.lifecycle.HqlStepsType;
import com.simple.server.mediators.CommandType;
import com.simple.server.statistics.time.Timing;
import com.simple.server.util.DateTimeConverter;
import com.simple.server.util.MyLogger;

@SuppressWarnings("static-access")
@Service("BusClientMsgTask")
@Scope("prototype")
public class BusClientMsgTask extends AbstractTask {
	@Autowired
	private AppConfig appConfig;
	Boolean isBatchOperation = false;
	private final static Integer MAX_NUM_ELEMENTS = 100000;
	private List<AContract> list = new ArrayList<AContract>();
	private List<IContract> newList = new ArrayList<IContract>();

	@Override
	public void update(Observable o, Object arg) {
		if (arg != null && arg.getClass() == CommandType.class) {
			switch ((CommandType) arg) {
			case WAKEUP_CONSUMER:
			case WAKEUP_ALL:
				arg = CommandType.WAKEUP_ALLOW;
				super.update(o, arg);
				break;
			case AWAIT_CONSUMER:
			case AWAIT_ALL:
				arg = CommandType.AWAIT_ALLOW;
				super.update(o, arg);
				break;
			}
		}
	}

	@Override
	public void task() throws Exception {
		try {
			if (appConfig.getBusClientMsgQueue().drainTo(list, MAX_NUM_ELEMENTS) == 0)
				list.add(appConfig.getBusClientMsgQueue().take());
	
			Thread.currentThread().sleep(Timing.getSleep4Wait());
			list.add(appConfig.getBusClientMsgQueue().take());
			isBatchOperation = false;
			if (list.size() > 50) {
				isBatchOperation = true;
				MyLogger.debug(getClass(), new Date()+": batch insert mode enabled");
				while (basePhaser.getCurrNumPhase() != HqlStepsType.START.ordinal()) {
					if (appConfig.getBusClientMsgQueue().size() > 0)
						appConfig.getBusClientMsgQueue().drainTo(list, MAX_NUM_ELEMENTS);
				}
			}

			
			String datetime = DateTimeConverter.getCurDate();		
			
			for(AContract msg: list){
				
				msg.setLogDatetime(datetime);
				msg.setLoggerId(this.toString());
	
				if (msg instanceof BusWriteMsg) {
					BusWriteMsg bmsg = (BusWriteMsg) msg;
					if ((bmsg.getServiceRoleFrom() != null) && bmsg.getServiceIdTo() == null
							&& bmsg.getServiceRoleFrom().equals(RoleType.FRONT_SERVICE.toString())) {
		
						BusReplyMsg busReplyMsg = new BusReplyMsg();
						busReplyMsg.setJuuid(msg.getJuuid());
						busReplyMsg.setResponseContentType(msg.getResponseContentType());
						busReplyMsg.setResponseContractClass(msg.getResponseContractClass());
						busReplyMsg.setResponseURI(msg.getResponseURI());
						if (isBatchOperation) {
							newList.add(busReplyMsg);
						} else {
							appConfig.getMsgService().insert(busReplyMsg);
						}
					}
				}
				if (isBatchOperation) {
					newList.add(msg);
				} else {
					appConfig.getMsgService().insert(msg);
				}
			}
			
			if (isBatchOperation) {
				appConfig.getMsgService().insertBus(newList);				
				newList.clear();
			}
			list.clear();
			
		} catch(Exception e ) {
			if (list.size() > 10) {
				newList.clear();				
			}
			list.clear();
			MyLogger.error(getClass(), e);	
		}
	}

}
