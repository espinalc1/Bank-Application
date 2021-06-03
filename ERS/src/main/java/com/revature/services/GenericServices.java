package com.revature.services;

import java.util.List;

public interface GenericServices<T> {
	public T add(T t);

	public T getById(Integer id);

	public Integer update(T t);

	public Integer delete(T t);

	public List<T> getAll();

}
