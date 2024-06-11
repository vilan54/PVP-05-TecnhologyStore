package com.vilan.pablo.technology.store.rest.common;

import java.util.List;
import java.util.Map;

public interface GenericServiceAPI<I,O> {

	String save(I entity, String id) throws Exception;
	
	String save(I entity) throws Exception;
	
	void delete(String id) throws Exception;
	
	O get(String name) throws Exception;

    O getByEmail(String email) throws Exception;

	void updateName(String id, String newName) throws Exception;
	
	Map<String, Object> getAsMap(String id) throws Exception;
	
	List<O> getAll() throws Exception;
}