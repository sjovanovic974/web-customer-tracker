package sasa.jovanovic.springdemo.service;

import java.util.List;

import sasa.jovanovic.springdemo.entity.Customer;

public interface CustomerService {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int id);
}
