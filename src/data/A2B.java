package data;

import java.util.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.util.stream.IntStream;


public class A2B {
	
	public static void main(String[] args) {
		
		//adds all customers to a list
		Customer c1,c2,c3,c4,c5,c6,c7,c8;
		
		c1 = new Customer("C1",0,4);
		c2 = new Customer("C2",0,6);
		c3 = new Customer("C3",1,4);
		c4 = new Customer("C4",2,5);
		c5 = new Customer("C5",3,5);
		c6 = new Customer("C6",7,7);
		c7 = new Customer("C7",8,5);
		c8 = new Customer("C8",10,5);
		
		ArrayList<Customer> shopCustomers = new ArrayList<>();
		shopCustomers.add(c1);
		shopCustomers.add(c2);
		shopCustomers.add(c3);
		shopCustomers.add(c4);
		shopCustomers.add(c5);
		shopCustomers.add(c6);
		shopCustomers.add(c7);
		shopCustomers.add(c8);
		
		//puts the list into the Timer class
		Timer.setCustomersList(shopCustomers);
		
		
		Timer customers;
		customers = new Timer();
		
		//initializes a thread pool of 1
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
		//execute the Timer class thread, of which it's run method 
		//will then execute each customer thread according to arrival time
		executor.execute(customers);
		
		//shuts down executor
		executor.shutdown();
		
		//makes sure all threads terminated then shut down
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	

	}

}
