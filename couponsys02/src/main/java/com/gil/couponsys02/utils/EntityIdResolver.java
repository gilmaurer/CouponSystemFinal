package com.gil.couponsys02.utils;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

public class EntityIdResolver implements ObjectIdResolver {

	@Autowired
	 private EntityManager entityManager;
	
	@Override
	public void bindItem(IdKey id, Object pojo) {
		
	}

	@Override
	public Object resolveId(IdKey id) {
		return this.entityManager.find(id.scope, id.key);
	}

	@Override
	public ObjectIdResolver newForDeserialization(Object context) {
		return this;
	}

	@Override
	public boolean canUseFor(ObjectIdResolver resolverType) {
		return false;
	}

}
