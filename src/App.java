import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class App {

	private JFrame frame;
	Restaurant myResto = new Restaurant();
	// private JTextField txtPhoneNumber;
	// private JTextField txtPhoneNumber_1;
	JPanel panel_1;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JList<String> DisplayList;
	private Undostack undostack = new Undostack();
	private JTextField txtAddress;
	private JList<String> list_1;
	private JTextField txtSearchPhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		Folder.loadData(myResto);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Frame
		frame = new JFrame();
		frame.setBounds(0, 0, 1368, 689);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
//wundwows when exit save
			@Override
			public void windowClosing(WindowEvent e) {
				Folder.saveData(myResto);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBorder(new LineBorder(Color.GRAY, 12));
		panel.setBounds(6, 6, 1396, 102);
		frame.getContentPane().add(panel);

		JLabel lblNewLabel_1 = new JLabel("");
		panel.add(lblNewLabel_1);

		// Label Restaurant Management Title
		JLabel lblNewLabel = new JLabel("Restaurant Management");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel.setBorder(new LineBorder(UIManager.getColor("CheckBoxMenuItem.disabledBackground"), 12));

		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
		lblNewLabel.setForeground(UIManager.getColor("Button.background"));
		panel.add(lblNewLabel);

		// Label Customer Info
		JLabel lblCustomerInfo = new JLabel("Customer Info");
		lblCustomerInfo.setBounds(973, 245, 109, 16);
		frame.getContentPane().add(lblCustomerInfo);

		// Button Exit
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				myResto.exit();
			}
		});
		btnExit.setBounds(899, 120, 117, 29);
		frame.getContentPane().add(btnExit);

		// TextField ID
		txtId = new JTextField();
		txtId.setBounds(19, 273, 130, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);

		// TextField Name
		txtName = new JTextField();
		txtName.setBounds(193, 273, 130, 26);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);

		// TextField Phone
		txtPhone = new JTextField();
		txtPhone.setBounds(381, 273, 130, 26);
		frame.getContentPane().add(txtPhone);
		txtPhone.setColumns(10);

		// TextField Email
		txtEmail = new JTextField();
		txtEmail.setBounds(546, 273, 130, 26);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);

		// Button Add
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addtoList();
			}

			private void addtoList() {
				Customer c = new Customer(txtId.getText(), txtName.getText(), txtPhone.getText(), txtEmail.getText(),
						txtAddress.getText());
				Action action = new Action();
				action.customer = c;
				if (!myResto.addCustomer(c)) {
					JOptionPane.showMessageDialog(frame, "You will be added to the waiting list! ", "LIST FULL!",
							JOptionPane.WARNING_MESSAGE);
					action.actionType = ActionType.ADD_TO_WAITINGLIST;
				} else {
					action.actionType = ActionType.ADD_TO_RESERVATION_LIST;
				}
				undostack.push(action);
			}
		});
		btnAdd.setBounds(19, 342, 117, 29);
		frame.getContentPane().add(btnAdd);

		// Button Search
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] customersFound = search();
				DisplayList.setListData(customersFound);
				list_1.setListData(searchwaitinglist());
			}

			private String[] search() {
				Customer[] customers = myResto.findByPhoneNumber(txtPhone.getText());
				String[] retValue = new String[customers.length];
				for (int i = 0; i < customers.length; i++) {
					retValue[i] = customers[i].toString();
				}
				return retValue;
			}

			private String[] searchwaitinglist() {
				Customer[] customers = myResto.findFromWaitingList(txtPhone.getText());
				String[] retValue = new String[customers.length];
				for (int i = 0; i < customers.length; i++) {
					retValue[i] = customers[i].toString();
				}
				return retValue;
			}
		});
		btnSearch.setBounds(193, 342, 117, 29);
		frame.getContentPane().add(btnSearch);

		// Button Delete
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}

			private void delete() {
				myResto.removeCustomer(txtId.getText(), undostack);
			}
		});
		btnDelete.setBounds(377, 342, 117, 29);
		frame.getContentPane().add(btnDelete);

		// Button Undo
		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Action action = undostack.pop();
				switch (action.actionType) {
				case ADD_TO_RESERVATION_LIST:
					myResto.removeCustomer(action.customer);
					break;

				case ADD_TO_WAITINGLIST:
					ArrayList<Customer> waitinglist = new ArrayList<>();
					while (myResto.waitingList.size() > 1) {
						waitinglist.add(myResto.waitingList.dequeue());
					}
					myResto.waitingList.dequeue();
					for (int i = 0; i < waitinglist.size(); i++) {
						myResto.waitingList.enqueue(waitinglist.get(i));
					}
					break;
				case DELETE_FROM_RESERVATION_LIST_EMPTY_WAITING_LIST:
					myResto.addClient(action.customer);
					break;

				case DELETE_FROM_RESERVATION_LIST:
					ArrayList<Customer> queue = new ArrayList<Customer>();
					while (!myResto.waitingList.isEmpty()) {
						queue.add(myResto.waitingList.dequeue());
					}
					myResto.waitingList.enqueue(action.customer);
					for (Customer customer : queue) {
						myResto.waitingList.enqueue(customer);
					}
					myResto.customers.remove(action.customer);
					myResto.customers.add(action.deleted);
					break;
				default:
					break;
				}
			}
		});
		btnUndo.setBounds(559, 342, 117, 29);
		frame.getContentPane().add(btnUndo);

		// Label ID
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(64, 245, 61, 16);
		frame.getContentPane().add(lblId);

		// Label Name
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(225, 245, 61, 16);
		frame.getContentPane().add(lblName);

		// Label Phone
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(406, 245, 61, 16);
		frame.getContentPane().add(lblPhone);

		// Label Email
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(571, 245, 61, 16);
		frame.getContentPane().add(lblEmail);

		// Display List
		DisplayList = new JList();
		DisplayList.setBounds(899, 289, 320, 145);
		frame.getContentPane().add(DisplayList);

		JButton btnSMS = new JButton("Send SMS");
		btnSMS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String phone = JOptionPane.showInputDialog(frame, "Enter a phone number");
				new SmsSender().SendMsg(phone);
			}
		});
		btnSMS.setBounds(752, 342, 117, 29);
		frame.getContentPane().add(btnSMS);

		txtAddress = new JTextField();
		txtAddress.setBounds(739, 273, 130, 26);
		frame.getContentPane().add(txtAddress);
		txtAddress.setColumns(10);

		// Label Address
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(780, 245, 61, 16);
		frame.getContentPane().add(lblAddress);

		list_1 = new JList();
		list_1.setBounds(884, 496, 320, 145);
		frame.getContentPane().add(list_1);

		// Label Waiting List
		JLabel lblWaitingList = new JLabel("Waiting List");
		lblWaitingList.setBounds(941, 468, 109, 16);
		frame.getContentPane().add(lblWaitingList);

		// Textfield SearchPhone
		txtSearchPhone = new JTextField();
		txtSearchPhone.setColumns(10);
		txtSearchPhone.setBounds(29, 201, 153, 26);
		frame.getContentPane().add(txtSearchPhone);

		// Label Search by phone number
		JLabel lblSearchByPhone = new JLabel("Search by phone number");
		lblSearchByPhone.setBounds(29, 173, 167, 16);
		frame.getContentPane().add(lblSearchByPhone);

		// Button Submit
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] customersFound = search();
				DisplayList.setListData(customersFound);
				list_1.setListData(searchwaitinglist());
			}

			private String[] search() {
				Customer[] customers = myResto.findByPhoneNumber(txtSearchPhone.getText());
				String[] retValue = new String[customers.length];
				for (int i = 0; i < customers.length; i++) {
					retValue[i] = customers[i].toString();
				}
				return retValue;
			}

			private String[] searchwaitinglist() {
				Customer[] customers = myResto.findFromWaitingList(txtSearchPhone.getText());
				String[] retValue = new String[customers.length];
				for (int i = 0; i < customers.length; i++) {
					retValue[i] = customers[i].toString();
				}
				return retValue;
			}
		});
		btnSubmit.setBounds(193, 201, 117, 29);
		frame.getContentPane().add(btnSubmit);
	}

	// Twilio Message
	class SmsSender {
		// Find your Account Sid and Auth Token at twilio.com/console
		public static final String ACCOUNT_SID = "AC3b1c5ba5b56c6e5d52b01f909af53a44";
		public static final String AUTH_TOKEN = "842548aa087de3080a587db68bad342a";

		public SmsSender() {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		}

		public void SendMsg(String toPhoneNumber) {

			Message message = Message.creator(new PhoneNumber("+961" + toPhoneNumber), // to
					new PhoneNumber("+18327028638"), // from
					"Reservation confirmed")// The customer has been moved from the waiting list to the reservation list
					.create();

			System.out.println(message.getSid());
		}
	}
}