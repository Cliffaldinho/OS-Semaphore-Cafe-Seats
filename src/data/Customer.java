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

public class Customer implements Runnable {
	
	private String customerID;
	private int arrivalTime;
	private int eatingDuration;
	private static Semaphore semaphore;
	private int sit;
	private int leave;
	
	static {
		semaphore = new Semaphore(4,true);
	}
	
	public Customer(String id, int time, int duration) {
		
		customerID=id;
		arrivalTime=time;
		eatingDuration=duration;
		sit=0;//waiting time in the queue
		leave=0;
		
	}
	
	public String getCustomerID() {
		return customerID;
	}
	
	public int getSeatTime() {
		return sit;
	}
	
	public int getLeaveTime() {
		return leave;
	}
	
	public void run() {
		
				System.out.println("Customer "+customerID+ " arrives at "+Timer.getTime()+" seconds.");
				
				//customer tries to acquire a semaphore
			try {
					semaphore.acquire();
					
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			//records the time customer sits
			sit=Timer.getTime();
			System.out.println("Customer "+customerID+" gets a seat at "+sit+" seconds.");
			
			//critical section
			
			leave=sit+eatingDuration;//calculates time customer leaves
			
			//while it's not yet time for him to leave
			while(Timer.getTime()<leave) {
				try {
					
					//sleep thread and let timer run
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
			}
			
			//finish eating
			System.out.println("Customer "+customerID+" finishes eating ice cream at "+Timer.getTime()+" seconds.");
		
			
			//for the ice cream shop rule
			
			//increment counter for amount of seats finished
			Timer.incrementSeatsFinished();
			
			//all seats finished is initialized to false
			while(!Timer.isAllSeatsFinished()) {
				
				//if seats finished is 4
				if(Timer.getSeatsFinished()==4) {
					
					//break out of the while loop
					Timer.setAllSeatsFinished(true);
				}
				
				//if seats finished is 0
				if(Timer.getSeatsFinished()==0) {
					
					//continue in the while loop
					Timer.setAllSeatsFinished(false);
				}
				
				//if seats finished is 1,2,3 it will wait to release their semaphore
				//thus ensuring all 4 seated customers have left before allowing next customer to sit
				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//decrement counter after customer left
			Timer.decrementSeatsFinished();
			
			
			//lets timer know that this thread has finished it's task
			Timer.incrementFinishedCustomers();
			
			//release semaphore
			semaphore.release();
			
			
			System.out.println("Available seats after customer "+customerID+" leaves is "+semaphore.availablePermits()+".");
			
		
	}
	

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int time) {
		arrivalTime = time;
	}
	
	public void decrementArrivalTime() {
		arrivalTime--;
	}
	
	public void incrementArrivalTime() {
		arrivalTime++;
	}
}
