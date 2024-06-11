package com.vilan.pablo.technology.store.model.exceptions;

public class InstanceNotFoundException extends InstanceException {
    
    public InstanceNotFoundException(String name, Object key) {
    	super(name, key); 	
    }

}
