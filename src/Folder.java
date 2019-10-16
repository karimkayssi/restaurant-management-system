import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Folder {
	
	public static void saveData(Restaurant resto) {
		File saveFile = new File("data.txt");
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				System.err.println("Could not create a new file");
			}
		}

		try (FileWriter writer = new FileWriter(saveFile)) {
			for (int i = 0; i < resto.customers.size(); i++) {
				Customer cust = resto.getCustomer(i);
				writer.append(cust.getID() + " " + cust.getName() + " " + cust.getPhonenumber() + " " + cust.getEmail() + " " + cust.getAddress()
						+ "\r\n");
			}

		} catch (IOException e) {
			System.err.println("Could not write to the file " + saveFile.getPath());
		}

		File saveWaitingFile = new File("waitinglist.txt");
		if (!saveWaitingFile.exists()) {
			try {
				saveWaitingFile.createNewFile();
			} catch (IOException e) {
				System.err.println("Could not create a new file");
			}
		}

		try (FileWriter writer = new FileWriter(saveWaitingFile)) {
			for (int i = 0; i < resto.waitingList.size(); i++) {
				Customer cust = resto.waitingList.dequeue();

				writer.append(cust.getID() + " " + cust.getName() + " " + cust.getPhonenumber() + " " + cust.getEmail() + " "
						+ cust.getAddress() + "\r\n");

				resto.waitingList.enqueue(cust);

			}

		} catch (IOException e) {
			System.err.println("Could not write to the file " + saveWaitingFile.getPath());
		}
	}

	public static void loadData(Restaurant rest) {
		File saveFile = new File("data.txt");
		if (saveFile.exists()) {
			try (BufferedReader read = new BufferedReader(new FileReader(saveFile))) {
				String s;
				while ((s = read.readLine()) != null && !s.equals("")) {
					String[] array = s.split(" ");

					rest.addClient(new Customer(array[0], array[1], array[2], array[3], array[4]));

				}

			} catch (IOException e) {
				System.out.println("Could not read file " + saveFile.getPath());
			}
		}
		
		File loadWaitingfile = new File("waitinglist.txt");
		if (loadWaitingfile.exists()) {
			try (BufferedReader read = new BufferedReader(new FileReader(loadWaitingfile))) {
				String s;
				while ((s = read.readLine()) != null && !s.equals("")) {
					String[] array = s.split(" ");

					rest.waitingList.enqueue(new Customer(array[0], array[1], array[2], array[3],array[4]));

				}

			} catch (IOException e) {
				System.out.println("Could not read file " + loadWaitingfile.getPath());
			}
		}
	}

}
