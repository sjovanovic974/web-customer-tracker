package sasa.jovanovic.springdemo.dao;

import java.util.List;
import sasa.jovanovic.springdemo.entity.Customer;

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int id);
}
