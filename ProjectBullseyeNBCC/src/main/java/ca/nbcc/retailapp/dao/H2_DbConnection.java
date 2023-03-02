
package ca.nbcc.retailapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class H2_DbConnection {

	private static final String PERSISTENCE_UNIT = "ProjectBullseyeNBCC";
	private static H2_DbConnection connection = null;
	private EntityManagerFactory emf = null;
	
	private H2_DbConnection() {
		init();
	}
	
	private void init() {
		if (emf == null || !emf.isOpen()) {
			//make new emf instance 
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		}
	}
	
	public static H2_DbConnection getInstance() {
		if (connection == null) {
			connection = new H2_DbConnection();
		}
		return connection;
	}
	
	public EntityManager getEntityManager() {
		init();
		return emf.createEntityManager();
	}
	
	public void close() {
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}
