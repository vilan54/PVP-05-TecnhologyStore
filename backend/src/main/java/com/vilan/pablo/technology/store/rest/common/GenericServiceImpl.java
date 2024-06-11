package com.vilan.pablo.technology.store.rest.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query;

public abstract class GenericServiceImpl<I, O> implements GenericServiceAPI<I, O> {

	public Class<O> clazz;

	
	public GenericServiceImpl(Class<O> entityClass) {
		this.clazz = entityClass;
	}

	@Override
	public String save(I entity) throws Exception {
		return this.save(entity, null);
	}

	@Override
	public String save(I entity, String id) throws Exception {
		if (id == null || id.length() == 0) {
			return getCollection().add(entity).get().getId();
		}

		DocumentReference reference = getCollection().document(id);
		reference.set(entity);
		return reference.getId();
	}

	@Override
	public void delete(String id) throws Exception {
		getCollection().document(id).delete().get();
	}

	@Override
	public O get(String id) throws Exception {
		DocumentReference ref = getCollection().document(id);
		ApiFuture<DocumentSnapshot> futureDoc = ref.get();
		DocumentSnapshot document = futureDoc.get();
		if (document.exists()) {
			O object = document.toObject(clazz);
			PropertyUtils.setProperty(object, "id", document.getId());
			return object;
		}
		return null;
	}

    @Override
    public O getByEmail(String email) throws Exception {
    	CollectionReference collection = getCollection();
    	Query query = collection.whereEqualTo("email", email);
    	ApiFuture<QuerySnapshot> querySnapshot = query.get();
		
    	try {
    	    List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		
    	    if (!documents.isEmpty()) {
    	        DocumentSnapshot document = documents.get(0);
    	        O object = document.toObject(clazz);
    	        PropertyUtils.setProperty(object, "id", document.getId());
    	        return object;
    	    }
    	} catch (InterruptedException e) {
    	    Thread.currentThread().interrupt(); // Restores the interrupted status
    	    throw new Exception("Thread was interrupted while waiting for Firestore query.", e);
    	} catch (ExecutionException e) {
    	    throw new Exception("Error executing Firestore query.", e);
    	} catch (IllegalAccessException e) {
    	    throw new Exception("Error accessing property during reflection.", e);
    	} catch (InvocationTargetException e) {
    	    throw new Exception("Error invoking method during reflection.", e);
    	} catch (NoSuchMethodException e) {
    	    throw new Exception("No such method found during reflection.", e);
    	}
	
    	return null;
}

	@Override
 	public void updateName(String id, String newName) throws Exception {
 	   // Verifica si el id no es nulo y no está vacío
 	   if (id == null || id.length() == 0) {
 	       throw new IllegalArgumentException("The id can not be empty or null");
 	   }
	
 	   // Verifica si el nuevo nombre no es nulo y no está vacío
 	   if (newName == null || newName.length() == 0) {
 	       throw new IllegalArgumentException("The id can not be empty or null");
 	   }

 	   // Obtén la referencia del documento
 	   DocumentReference reference = getCollection().document(id);

 	   // Actualiza solo el campo "nombre"
 	   Map<String, Object> updates = new HashMap<>();
 	   updates.put("name", newName);
 	   reference.update(updates).get();
	}	

	@Override
	public List<O> getAll() throws Exception {
		List<O> result = new ArrayList<O>();
		ApiFuture<QuerySnapshot> query = getCollection().get();
		List<QueryDocumentSnapshot> documents = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documents) {
			O object = doc.toObject(clazz);
			PropertyUtils.setProperty(object, "id", doc.getId());
			result.add(object);
		}
		return result;
	}

	@Override
	public Map<String, Object> getAsMap(String id) throws Exception {
		DocumentReference reference = getCollection().document(id);
		ApiFuture<DocumentSnapshot> futureDoc = reference.get();
		DocumentSnapshot document = futureDoc.get();
		if (document.exists()) {
			return document.getData();
		}
		return null;
	}

	public abstract CollectionReference getCollection();
}

