package com.simple.server.tasks;

import java.util.List;
import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.simple.server.config.AppConfig;
import com.simple.server.domain.contract.IContract;
import com.simple.server.domain.contract.RedirectRouting;
import com.simple.server.domain.contract.SessionFactory;
import com.simple.server.domain.contract.TimeoutPolicies;
import com.simple.server.mediators.CommandType;
import com.simple.server.service.IService;
import com.simple.server.util.MyLogger;


@SuppressWarnings("static-access")
@Service("CheckConnectionTask")
@Scope("prototype")
public class CheckConnectionTask  extends AbstractTask {

	
	@Autowired
	AppConfig appConfig;
	
	public AppConfig getAppConfig() throws Exception {
		return appConfig;
	}
	
	
	@Override
    public void update(Observable o, Object arg) {

        if(arg.getClass().equals(CommandType.class)) {
            switch ((CommandType) arg) {
                case WAKEUP_PROCESSING:
                case WAKEUP_ALL:
                    super.update(o, CommandType.WAKEUP_ALLOW);
                    break;
                case AWAIT_PROCESSING:
                case AWAIT_ALL:
                    super.update(o, CommandType.AWAIT_ALLOW);
                    break;
            }
        }      
    }
	
	@Override
	public void task() throws Exception {
		setDeactivateMySelfAfterTaskDone(true);
		Thread.currentThread().sleep(4000);	
		try {					
			MyLogger.warnStartBlock(getClass(), "SERVICE START CONFIG...");
						
						
			List<IContract> sessionFactories = getAppConfig().getMsgService().readAll(new SessionFactory());
			List<IContract> redirectRoutings = getAppConfig().getMsgService().readAll(new RedirectRouting());
			List<IContract> timeoutPolicies = getAppConfig().getMsgService().readAll(new TimeoutPolicies());
					
						
			MyLogger.warnSingleHeader(getClass(),"PROPERTY:;SIZE:;");	
			
			if(sessionFactories != null && sessionFactories.size() > 0) {
				MyLogger.warn(getClass(), "Session Factories;"+sessionFactories.size());								
			} else {
				MyLogger.error(getClass(), "SERVICE START CONFIG: session factories is null!");
			}
			
			
			
			if(redirectRoutings != null && redirectRoutings.size() > 0) {							
				MyLogger.warn(getClass(), "Redirect Routings;"+redirectRoutings.size());				
			} else {
				MyLogger.error(getClass(), "SERVICE START CONFIG: Redirect Routings is null!");
			}
			
			
			
			if(timeoutPolicies != null && timeoutPolicies.size() > 0) {
				MyLogger.warn(getClass(), "Timeout Policies;"+timeoutPolicies.size());								
			} else {
				MyLogger.error(getClass(), "SERVICE START CONFIG: timeout policies is null!");
			}
			
						
			MyLogger.warnEndBlock(getClass(), "SERVICE COMPLETE CONFIG...");											
		} catch (Exception e) {
			MyLogger.error(getClass(), e);
		}		
	}

}
