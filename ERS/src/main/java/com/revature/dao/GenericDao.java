package com.revature.dao;

import java.util.List;

public interface GenericDao<T> {
	public T add(T t);

	public T getById(Integer id);

	public int update(T t);

	public int delete(T t);
	
	public List<T> getAll();
}
