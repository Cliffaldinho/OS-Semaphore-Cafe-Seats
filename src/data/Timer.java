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

public class Timer implements Runnable {
	
	private static int finishedCustomers;
	
	private static int totalCustomers;
	private static int time;
	private static boolean allSeatsFinished;
	private static int seatsFinished;
	private static ArrayList<Customer> customersList;
	
	public static ArrayList<Customer> getCustomerList() {
		return customersList;
	}
	
	public static void setCustomersList(ArrayList<Customer> customers) {
		customersList=customers;
	}


	public static int getSeatsFinished() {
		return seatsFinished;
	}

	public static void setSeatsFinished(int taken) {
		seatsFinished = taken;
	}
	
	public static void incrementSeatsFinished() {
		seatsFinished++;
	}
	
	public static void decrementSeatsFinished() {
		seatsFinished--;
	}

	public static boolean isAllSeatsFinished() {
		return allSeatsFinished;
	}

	public static void setAllSeatsFinished(boolean allTaken) {
		allSeatsFinished=allTaken;
	}

	static {
		finishedCustomers=0;
		totalCustomers=0;
		time=0;
		allSeatsFinished = false;
		customersList = new ArrayList<>();
	}
	
	public void run() {
		
		//gets the total customers in the shop
		totalCustomers=customersList.size();
		
		//executor service to execute threads
		ExecutorService executor = Executors.newFixedThreadPool(totalCustomers);
		
		//while loop acting as a timer
		while(finishedCustomers<totalCustomers) {
		
			//prevents the inner for loop from running if 
			//timer's time is more than  the last customer's arrival time
			if(time<=customersList.get(customersList.size()-1).getArrivalTime()) {
				
				//execute the thread if the customer arrival time is same as timer's time
			for(int i=0;i<customersList.size();i++) {
				if(customersList.get(i).getArrivalTime()==time) {
					executor.execute(customersList.get(i));
				}
			}
			}
			
			
			//increment timer
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
			time++;
		}
		System.out.println("\nAll done.");
		
		//shuts down the executor
		executor.shutdown();
		
		//makes sure all threads are terminated then shut down executor
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//prints out output
		System.out.println("\nCustomer"+"   "+"Arrives"+"   "+"Seats"+"   "+"Leaves");
		for(int i=0;i<customersList.size();i++) {
			Customer customer=customersList.get(i);
			System.out.println("   "+customer.getCustomerID()+"        "+customer.getArrivalTime()+"        "+
			customer.getSeatTime()+"       "+customer.getLeaveTime());
		}
		
	}
	
	public static int getTime() {
		return time;
	}

	public static void setTime(int t) {
		time=t;
	}
	
	public static void incrementTime() {
		time++;
	}


	public static int getFinishedCustomers() {
		return finishedCustomers;
	}

	public static void setFinishedCustomers(int finishedCustomers) {
		Timer.finishedCustomers = finishedCustomers;
	}

	public static int getTotalCustomers() {
		return totalCustomers;
	}

	public static void setTotalCustomers(int totalCustomers) {
		Timer.totalCustomers = totalCustomers;
	}
	
	public static void incrementFinishedCustomers() {
		finishedCustomers++;
	}

}
