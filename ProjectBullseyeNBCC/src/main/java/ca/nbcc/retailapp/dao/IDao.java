package ca.nbcc.retailapp.dao;

import java.util.List;

public interface IDao<T> {

	T getElementById(int id);
	
	List<T> getAllElements();
	
	T addElement(T t);
	
	T updateElement(T t);
	
	boolean removeElement(T t);
	
	List<T> searchByTerm();
	
}
