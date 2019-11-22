# OS-Semaphore-Cafe-Seats
A program that utilizes a semaphore to ensure correct allocation of seats in an ice cream cafe. 

There are 4 seats in the ice cream cafe.

A customer may arrive at any time, and take any amount of time to finish their ice cream once seated. 

If there is an empty seat when a customer arrives, the customer can take that seat.

However if all 4 seats are occupied, then new arriving customers have to wait until all 4 seated customers finish their ice cream and leave, before getting a seat.

A semaphore is utilized to implement the above business rules and scenario.
