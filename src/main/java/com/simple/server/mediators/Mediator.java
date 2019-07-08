package com.simple.server.mediators;

import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.simple.server.tasks.states.State;
import com.simple.server.util.MyLogger;


public class Mediator extends Observable implements Observer {
	
	
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && !arg.getClass().equals(State.class)) {
            setChanged();
            notifyObservers(arg);
        }
        else
        if (arg instanceof State) {
        	MyLogger.error(getClass(), "Mediator:"+o.getClass()+":"+((State) arg).getMessage());	
        }
    }
    
    public void wakeupAll(){
    	setChanged();
    	notifyObservers(CommandType.WAKEUP_ALL);
    }
}
