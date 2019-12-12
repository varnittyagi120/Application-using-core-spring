package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import dto.Person;

@Component
public class DbOperations<T extends Person> {
	private Transaction tx = null;

	public boolean addData(T object) {
		try {
			Session session = getSession(object);
			session.save(object);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();  
			return false;
		}
		return true;

	}

	public List<T> read(String id, T object) {
		List<T> listOfObject = new ArrayList<>();
		try {
			Session session = getSession(object);
			listOfObject.add((T) session.get(object.getClass(), id));
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return listOfObject;
	}

	public List<T> readAll(T object) {
		List<T> listOfObjects = new ArrayList<>();
		try {
			Session session = getSession(object);
			listOfObjects = session.createCriteria(object.getClass()).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return listOfObjects;
	}

	public List<T> checkUidExsist(String id, T object) {
		List<T> listOfClientObject = new ArrayList<>();
		try {
			Session session = getSession(object);
			listOfClientObject.add((T) session.get(object.getClass(), id));
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return listOfClientObject;
	}

	public void update(T object) {
		try {
			Session session = getSession(object);
			session.saveOrUpdate(object);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}

	public boolean delete(String id, T object) {
		try {
			Session session = getSession(object);
			session.delete(session.get(object.getClass(), id));
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
		return true;
	}

	public Session getSession(T object) {
		Configuration con = new Configuration().configure().addAnnotatedClass(object.getClass());
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(con.getProperties())
				.buildServiceRegistry();
		SessionFactory sf = con.buildSessionFactory(serviceRegistry);
		Session session = sf.openSession();
		tx = session.beginTransaction();
		return session;
	}
}
