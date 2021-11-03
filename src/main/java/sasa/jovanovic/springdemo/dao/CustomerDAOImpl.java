package sasa.jovanovic.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sasa.jovanovic.springdemo.entity.Customer;
import sasa.jovanovic.springdemo.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create a query
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// save or update the customer
		currentSession.saveOrUpdate(theCustomer);

	}

	@Override
	public Customer getCustomer(int id) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// get the customer from the database
		return currentSession.get(Customer.class, id);
	}

	@Override
	public void deleteCustomer(int id) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete the customer
		Query query = currentSession.createQuery("delete from Customer where id=:customerId");
		query.setParameter("customerId", id);

		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		Query theQuery = null;

		// only search by theSearchName if it is not empty

		if (theSearchName != null && theSearchName.trim().length() > 0) {
			// search for first name or last name... case insensitive
			theQuery = currentSession.createQuery(
					"from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			// theSearchName is empty... so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		return customers;
	}

	@Override
	public List<Customer> getCustomers(int theSortField) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// determine sort field
		String theFieldName = null;
		
		switch(theSortField) {
			case SortUtils.FIRST_NAME:
				theFieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				theFieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				theFieldName = "email";
				break;
			default:
				// if nothing matches the default to sort by lastName
				theFieldName = "lastName";
		}
		
		// create a query
		String queryString = "from Customer order by " + theFieldName;
		Query<Customer> theQuery = currentSession.createQuery(queryString, Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// return the results
		return customers;
	}

}
