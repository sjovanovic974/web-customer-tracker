package sasa.jovanovic.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sasa.jovanovic.springdemo.entity.Customer;
import sasa.jovanovic.springdemo.service.CustomerService;
import sasa.jovanovic.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject customer service
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String listCustomers(Model theModel, @RequestParam(required = false) String sort) {

		// get customers from the service
		List<Customer> customers = null;
		
		// check for the sort field
		if(sort != null) {
			int theSortField = Integer.parseInt(sort);
			customers = customerService.getCustomers(theSortField);
		} else {
			// no sort field provided... default to string by last name
			customers = customerService.getCustomers(SortUtils.LAST_NAME);
		}

		// add the customers to the model
		theModel.addAttribute("customers", customers);

		return "list-customers"; 
	}
	
	@GetMapping("/show-form-for-add")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		//save the customer using our service
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model theModel) {
		
		// get the customer from our service
		Customer customer = customerService.getCustomer(id);
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", customer);
		
		// send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		
		// delete the customer
		customerService.deleteCustomer(id);
		
		// send over to our form
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {
		
		// search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
}
