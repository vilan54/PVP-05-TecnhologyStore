package com.vilan.pablo.technology.store.model.exceptions;

public class DuplicateInstanceException extends InstanceException {
    public DuplicateInstanceException(String name, Object key) {
    	super(name, key); 	
    }
}
