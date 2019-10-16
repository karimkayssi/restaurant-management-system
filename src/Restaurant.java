import java.util.ArrayList;
import java.util.List;

public class Restaurant {

	List<Customer> customers = new ArrayList<Customer>();

	WaitingList waitingList = new WaitingList();

	int capacity = 5;

	public boolean addCustomer(Customer customer) {
		if (customers.size() < capacity) {
			this.customers.add(customer);
			return true;
		} else {
			waitingList.enqueue(customer);
			return false;
		}
	}

	public Customer[] findByPhoneNumber(String phoneNumber) {
		ArrayList<Customer> retValue = new ArrayList<>();
		for (Customer customer : customers) {
			if (customer.getPhoneNumer().equals(phoneNumber)) {
				retValue.add(customer);
			}
		}
		Customer[] array = new Customer[retValue.size()];
		return retValue.toArray(array);

	}
	

	public Customer[] findFromWaitingList(String phoneNumber) {
		ArrayList<Customer> retValue = new ArrayList<>();
		for (int i =0; i < waitingList.size(); i++) {
			Customer customer = waitingList.dequeue();
			if (customer.getPhoneNumer().equals(phoneNumber)) {
				retValue.add(customer);
			}
			waitingList.enqueue(customer);
		}
		Customer[] array = new Customer[retValue.size()];
		return retValue.toArray(array);

	}
	

	public Customer getCustomer(int i) {

		return this.customers.get(i);

	}

	public void addClient(Customer customer) {
		customers.add(customer);

	}

	public void exit() {
		Folder.saveData(this);
	}

	public void removeCustomer(String ID, Undostack undostack) {
		for (int i = 0; i< customers.size(); i++) {
			Customer customer = customers.get(i);
			if (ID.equals(customer.getID())) {
				Action action = new Action();
				action.deleted = customer;
				customers.remove(customer);
				if (!waitingList.isEmpty()) {
					action.customer = waitingList.dequeue();
					customers.add(action.customer);
					action.actionType = ActionType.DELETE_FROM_RESERVATION_LIST;

				} else {
					action.actionType = ActionType.DELETE_FROM_RESERVATION_LIST_EMPTY_WAITING_LIST;

				}
				undostack.push(action);

			}
		}
	}

	public void removeCustomer(Customer c) {		
		for (int i = 0; i< customers.size(); i++) {
			Customer customer = customers.get(i);
			if (c == customer) {
				customers.remove(customer);
				if (!waitingList.isEmpty()) {
					customers.add(waitingList.dequeue());
				}
			}
		}
	}

}
