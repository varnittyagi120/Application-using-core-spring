package service;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import configuration.ApplicationConfig;
import dao.DbOperations;
import dto.Client;
import util.Utility;
@Component
public class CMSManagement extends Utility implements CRUDContainer {

	enum clientFields {
		name, number, email, billamount, tenure
	}

	Scanner scanner = new Scanner(System.in);
	String stringFormatOne = "%-5s%-36s %-5s %-12s %-5s %-12s %-5s %-10s %-5s %-28s %-5s %-15s %-5s %-10s %-5s\n";
	String StringFormatTwo = "%-5s%-33s %-5s %-12s %-5s %-12s %-5s %-13s %-5s %-28s %-5s %-15s %-5s %-10s %-5s\n";

	@Autowired
	DbOperations<Client> dbOperations;
	@Autowired
	Client client;
	public void add() {
		String uniqueID = UUID.randomUUID().toString();
		client.setUid(uniqueID);
		String instruction;
		instruction = "Enter First name (required) \nFor example : Varnit";
		client.setFname(inputOperationsDuringAddition(instruction, "name"));

		instruction = "Enter Last name (required) \nFor example : Tyagi";
		client.setLname(inputOperationsDuringAddition(instruction, "name"));

		instruction = "Enter Mobile Number (required) \nFor India : only 10 digits integer with correct format";
		client.setNumber(inputOperationsDuringAddition(instruction, "number"));

		instruction = "Enter Emailid (required) \nFor example : example@gmail.com";
		client.setEmail(inputOperationsDuringAddition(instruction, "email"));

		instruction = "Enter billmount (required) \nFor example :100.00 ";
		client.setBillAmount(inputOperationsDuringAddition(instruction, "billamount"));

		instruction = "Enter tenure of customer (optional) \nFor example :10 ";
		client.setTenure(inputOperationsDuringAddition(instruction, "tenure"));

		if (dbOperations.addData(client)) {
			System.out.println("Data has been sucessfully inserted into database table");
		} else {
			System.out.println("Data has not been inserted into database table due to some exception");
		}
	}

	public void read() {

		try {
			String uid = getUid();
			List<Client> list = dbOperations.read(uid, client);
			if (!list.isEmpty()) {
				printColumnNamesOfTable();
				printDataOfTable(0, list);
				System.out.println(String.format("%s",
						"--------------------------------------------------------------------------------------------------------------"
								+ "-----------------------------------------------------------------"));
			} else {
				System.out.println("Desired entry not found please try again");
			}
		} catch (Exception exception) {
			System.out.println("Got an Exception " + exception.getMessage());
		}
	}

	public void readall() {

		try {
			List<Client> list = dbOperations.readAll(client);
			if (!list.isEmpty()) {
				printColumnNamesOfTable();
				for (int i = 0; i < list.size(); i++) {
					printDataOfTable(i, list);
				}
				System.out.println(String.format("%s",
						"--------------------------------------------------------------------------------------------------------------"
								+ "-----------------------------------------------------------------"));
			} else {
				System.out.println("Database is empty no entry found");
			}
		} catch (Exception exception) {
			System.out.println("Got an exception " + exception.getMessage());
		}
	}

	public void update() {
		try {
			String uid = getUid();
			List<Client> list = dbOperations.checkUidExsist(uid, client);
			if (!list.isEmpty()) {
				client.setUid(list.get(0).getUid());

				String instruction;
				instruction = "Your old first name " + list.get(0).getFname() + "\n"
						+ "Enter new fname if u want to update else press enter";
				client.setFname(inputOperationsDuringUpdate(instruction, "name", list.get(0).getFname()));

				instruction = "Your old last name " + list.get(0).getLname() + "\n"
						+ "Enter new last name if u want to update else press enter";
				client.setLname(inputOperationsDuringUpdate(instruction, "name", list.get(0).getLname()));

				instruction = "Your old Mobile Number " + list.get(0).getNumber() + "\n"
						+ "Enter new mobile number if u want to update else press enter";
				client.setNumber(inputOperationsDuringUpdate(instruction, "number", list.get(0).getNumber()));

				instruction = "Your old email-id " + list.get(0).getEmail() + "\n"
						+ "Enter new email-id if u want to update else press enter";
				client.setEmail(inputOperationsDuringUpdate(instruction, "email", list.get(0).getEmail()));
				instruction = "Your old billAmount " + list.get(0).getBillAmount() + "\n"
						+ "Enter new billAmount if u want to update else press enter";
				client.setBillAmount(
						inputOperationsDuringUpdate(instruction, "billamount", list.get(0).getBillAmount()));

				instruction = "Your old tenure " + list.get(0).getTenure() + "\n"
						+ "Enter new tenure if u want to update else press enter";
				client.setTenure(inputOperationsDuringUpdate(instruction, "tenure", list.get(0).getTenure()));

				dbOperations.update(client);
			} else {
				System.out.println("entered Id does not exsist");
			}
		} catch (Exception exception) {
			System.out.println("Got an Exception " + exception.getMessage());
		}
	}

	public void delete() {
		String uid = getUid();
		dbOperations.delete(uid, client);
	}

	public String inputOperationsDuringAddition(String instruction, String choice) {
		String str;
		boolean flag;
		do {
			flag = true;
			System.out.println(instruction);
			str = scanner.nextLine();
			if (!choice.equals("tenure")) {
				flag = checkRequiredField(str);
			} else {
				if (str.isEmpty()) {
					return str;
				}
			}
			if (flag) {
				flag = switchCasesForPerformValidation(choice, str);
			}
		} while (!flag);
		return str;
	}

	public String inputOperationsDuringUpdate(String Instriction, String choice, String oldValueOfColumn) {

		String str;
		boolean flag;
		do {
			flag = true;
			System.out.println(Instriction);
			str = scanner.nextLine();
			if (str.isEmpty()) {
				return oldValueOfColumn;
			}
			if (flag) {
				flag = switchCasesForPerformValidation(choice, str);
			}
		} while (!flag);
		return str;
	}

	public boolean switchCasesForPerformValidation(String choice, String str) {
		boolean flag = true;
		clientFields choiceUsingEnum = clientFields.valueOf(choice);
		switch (choiceUsingEnum) {

		case name:
			if (!validateName(str)) {
				System.out.println("Please enter only characters");
				flag = false;
			}
			break;
		case number:
			if (!validateMobileNumber(str)) {
				System.out.println("Please enter correct mobile number");
				flag = false;
			}
			break;
		case email:
			if (!validateEmail(str)) {
				System.out.println("Please enter correct email address");
				flag = false;
			}
			break;
		case billamount:
			try {
				Double.parseDouble(str);
			} catch (Exception exception) {
				System.out.println("Please enter billAmount in numeric format");
				flag = false;
			}
			break;
		case tenure:
			try {
				Integer.parseInt(str);
			} catch (Exception exception) {
				System.out.println("Please enter tenure in Integers format");
				flag = false;
			}
			break;

		default:

			System.out.println("Wrong choice");
		}
		return flag;
	}

	public void printColumnNamesOfTable() {
		System.out.println(String.format("%s",
				"--------------------------------------------------------------------------------------------------------------"
						+ "-----------------------------------------------------------------"));
		System.out.format(stringFormatOne, "|", "UniqueID", "|", "First Name", "|", "Last Name", "|", "Mobile Number",
				"|", "E-mail", "|", "BillingAmount", "|", "Tenure", "|");

	}

	public void printDataOfTable(int i, List<Client> list) {
		System.out.println(String.format("%s",
				"--------------------------------------------------------------------------------------------------------------"
						+ "-----------------------------------------------------------------"));
		System.out.format(StringFormatTwo, "|", list.get(i).getUid(), "|", list.get(i).getFname(), "|",
				list.get(i).getLname(), "|", list.get(i).getNumber(), "|", list.get(i).getEmail(), "|",
				list.get(i).getBillAmount(), "|", list.get(i).getTenure(), "|");

	}

//	public Client getClientClassObject() {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		Client client = ctx.getBean(Client.class);
//		return client;
//	}

}
