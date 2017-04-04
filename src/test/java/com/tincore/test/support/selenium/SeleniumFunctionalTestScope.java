package com.tincore.test.support.selenium;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class SeleniumFunctionalTestScope implements Scope {

	public static final String ID = "seleniumFunctionalTest";

	private Map<String, Object> cache = new HashMap<>();

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		if (!cache.containsKey(name)) {
			cache.put(name, objectFactory.getObject());
		}
		return cache.get(name);
	}

	@Override
	public String getConversationId() {
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
	}

	@Override
	public Object remove(String name) {
		return cache.remove(name);
	}

	public void reset() {
		cache.clear();
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}
}