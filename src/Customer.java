
public class Customer {
	
	String ID;
	String name;
	String phonenumber;
	String email;
	String address;
	
	public Customer(String ID, String name, String phonenumber, String email, String address) {
		this.ID = ID;
		this.name = name;
		this.phonenumber = phonenumber;
		this.email = email;
		this.address = address;
}

	
	
	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhonenumber() {
		return phonenumber;
	}


	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoneNumer() {
		return phonenumber;
	}
	
	@Override
	public String toString() {
		return "ID: " + getID() + " name: " + getName() + " phone number:"+ getPhonenumber()  + " email: " +getEmail() +" address: " + getAddress() ;
	}
	
}
