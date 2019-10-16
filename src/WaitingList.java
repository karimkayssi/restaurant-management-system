import java.util.LinkedList;
import java.util.Queue;

public class WaitingList {

	Queue<Customer> customers = new LinkedList<>();
	
	public void enqueue(Customer customer) {
		customers.add(customer);
	}

	public Customer dequeue() {
		return customers.remove();	
	}

	public boolean isEmpty() {
		return customers.isEmpty();
	}
	
	public int size() {
		return customers.size();
	}
	
	
	
}
