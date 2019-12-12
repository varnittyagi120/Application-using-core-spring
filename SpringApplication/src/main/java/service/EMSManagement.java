package service;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import configuration.ApplicationConfig;
import dao.DbOperations;
import dto.Employee;
import util.Utility;

@Component
public class EMSManagement extends Utility implements CRUDContainer {
	enum employeeFields {
		name, number, email, year, month, date, salary
	}

	Scanner scanner = new Scanner(System.in);
	String stringFormatOne = "%-5s%-36s %-5s %-12s %-5s %-12s %-5s %-10s %-5s %-28s %-5s %-15s %-5s %-10s %-5s\n";
	String StringFormatTwo = "%-5s%-33s %-5s %-12s %-5s %-12s %-5s %-13s %-5s %-28s %-5s %-15s %-5s %-10s %-5s\n";
	public int yearInInteger = 0, monthInInteger = 0, dateInInteger = 0;

	@Autowired
	DbOperations<Employee> dbOperations;
    @Autowired
    Employee employee;
	public void add() {
		String instruction;
		String uniqueID = UUID.randomUUID().toString();
		employee.setUid(uniqueID);
		instruction = "Enter First name (required) \nFor example : Varnit";
		employee.setFname(inputOperationsDuringAddition(instruction, "name"));

		instruction = "Enter Last name (required) \nFor example : Tyagi";
		employee.setLname(inputOperationsDuringAddition(instruction, "name"));

		instruction = "Enter Mobile Number (required) \nFor India : only 10 digits integer with correct format";
		employee.setNumber(inputOperationsDuringAddition(instruction, "number"));

		instruction = "Enter Emailid (required) \nFor example : example@gmail.com";
		employee.setEmail(inputOperationsDuringAddition(instruction, "email"));

		System.out.println("Enter date of joining (required)");
		instruction = "Enter year between 2000>= and <=" + Calendar.getInstance().get(Calendar.YEAR);
		String yearInString = inputOperationsDuringAddition(instruction, "year");
		yearInInteger = Integer.valueOf(yearInString);

		String monthInString = inputOperationsDuringAddition(instruction, "month");
		monthInInteger = Integer.valueOf(monthInString);

		String dateInString = inputOperationsDuringAddition(instruction, "date");
		dateInInteger = Integer.valueOf(dateInString);

		String dateOfJoining = String.valueOf(dateInInteger) + "-" + String.valueOf(monthInInteger) + "-"
				+ String.valueOf(yearInInteger);
		employee.setDateOfJoining(dateOfJoining);

		instruction = "Enter salary (optional) \nBetween 10000>= to <=100000";
		employee.setSalary(inputOperationsDuringAddition(instruction, "salary"));

		if (dbOperations.addData(employee)) {
			System.out.println("Data has been successfully inserted into database table");
		} else {
			System.out.println("Data has not been  inserted into database table");
		}
	}

	public void read() {
		try {
			String uid = getUid();
			List<Employee> list = dbOperations.read(uid, employee);
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
			List<Employee> list = dbOperations.readAll(employee);
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
			List<Employee> list = dbOperations.checkUidExsist(uid, employee);
			if (!list.isEmpty()) {
				String instruction;
				String uniqueID = list.get(0).getUid();
				employee.setUid(uniqueID);
				instruction = "Your old first name " + list.get(0).getFname() + "\n"
						+ "Enter new fname if u want to update else press enter";
				employee.setFname(inputOperationsDuringUpdate(instruction, "name", list.get(0).getFname()));

				instruction = "Your old last name " + list.get(0).getLname() + "\n"
						+ "Enter new last name if u want to update else press enter";
				employee.setLname(inputOperationsDuringUpdate(instruction, "name", list.get(0).getLname()));

				instruction = "Your old Mobile Number " + list.get(0).getNumber() + "\n"
						+ "Enter new mobile number if u want to update else press enter";
				employee.setNumber(inputOperationsDuringUpdate(instruction, "number", list.get(0).getNumber()));

				instruction = "Your old email-id " + list.get(0).getEmail() + "\n"
						+ "Enter new email-id if u want to update else press enter";
				employee.setEmail(inputOperationsDuringUpdate(instruction, "email", list.get(0).getEmail()));

				String[] date = list.get(0).getDateOfJoining().split("-");
				System.out.println("Your old date of joining is " + list.get(0).getDateOfJoining());
				System.out.println(
						"Your old year " + date[2] + "\n" + "Enter new year if u want to update else press enter");
				instruction = "Enter year between 2000>= and <=" + Calendar.getInstance().get(Calendar.YEAR);
				String yearInString = inputOperationsDuringUpdate(instruction, "year", date[2]);
				yearInInteger = Integer.valueOf(yearInString);

				System.out.println(
						"Your old month " + date[1] + "\n" + "Enter new month if u want to update else press enter");
				String monthInString = inputOperationsDuringUpdate(instruction, "month", date[1]);
				monthInInteger = Integer.valueOf(monthInString);

				System.out.println(
						"Your old date " + date[0] + "\n" + "Enter new month if u want to update else press enter");
				String dateInString = inputOperationsDuringUpdate(instruction, "date", date[0]);
				dateInInteger = Integer.valueOf(dateInString);

				StringBuilder dateOfJoining = new StringBuilder().append(dateInString).append("-").append(monthInString)
						.append("-").append(yearInString);
				System.out.println("updated date " + dateOfJoining);
				employee.setDateOfJoining(dateOfJoining.toString());

				instruction = "Your old Salary " + list.get(0).getSalary() + "\n"
						+ "Enter new salary if u want to update else press enter";
				employee.setSalary(inputOperationsDuringUpdate(instruction, "salary", list.get(0).getSalary()));
				dbOperations.update(employee);
			} else {
				System.out.println("Desired entry not found please try again");
			}
		} catch (Exception exception) {
			System.out.println("Got an Exception " + exception.getMessage());
		}

	}

	public void delete() {
		String uid = getUid();
		dbOperations.delete(uid, employee);
	}

	public String inputOperationsDuringAddition(String instruction, String choice) {
		String str;
		boolean flag;
		do {
			flag = true;
			printInstructionForFields(instruction, choice);
			str = scanner.nextLine();
			if (!choice.equals("salary")) {
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

	public String inputOperationsDuringUpdate(String instruction, String choice, String oldValueOfColumn) {
		String str;
		boolean flag;
		do {
			flag = true;
			printInstructionForFields(instruction, choice);
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

	public void printInstructionForFields(String instruction, String choice) {
		if (choice.equals("month")) {
			String printInstruction = (yearInInteger == Calendar.getInstance().get(Calendar.YEAR))
					? "Enter month between 1>= and <=" + Calendar.getInstance().get(Calendar.MONTH)
					: "Enter month between 1>= and <=12";
			System.out.println(printInstruction);
		} else if (choice.equals("date")) {
			String printInstruction = (yearInInteger == Calendar.getInstance().get(Calendar.YEAR)
					&& monthInInteger == Calendar.getInstance().get(Calendar.MONTH))
							? "Enter date according to the month between 1>= and <="
									+ Calendar.getInstance().get(Calendar.DATE)
							: "Enter date according to the month between 1>= and <=31";
			System.out.println(printInstruction);
		} else {
			System.out.println(instruction);
		}
	}

	public boolean switchCasesForPerformValidation(String choice, String str) {
		boolean flag = true;
		employeeFields choiceUsingEnum = employeeFields.valueOf(choice);
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
		case year:
			int year = 0;
			try {
				year = Integer.parseInt(str);
				flag = validateYear(year);
			} catch (Exception e) {
				System.out.println("Please enter only integers");
				flag = false;
			}
			break;
		case month:
			try {
				int month = Integer.parseInt(str);
				flag = validateMonth(yearInInteger, month);
			} catch (Exception e) {
				System.out.println("Please enter only integers");
				flag = false;
			}
			break;
		case date:
			try {
				int date = Integer.parseInt(str);
				flag = validateDate(yearInInteger, validateLeapYear(yearInInteger), monthInInteger, date);
			} catch (Exception e) {
				System.out.println("Please enter only integers");
				flag = false;
			}
			break;
		case salary:
			try {
				double salaryInDouble = Double.parseDouble(str);
				if (!validateSalary(salaryInDouble)) {
					System.out.println("Please enter salary between 10000>= to <=100000");
					flag = false;
				}
			} catch (Exception exception) {
				System.out.println("please enter salary only numeric type");
				flag = false;
			}
			break;

		default:

			System.out.println("Wrong choice");
		}
		return flag;
	}

	public boolean validateSalary(Double salary) {
		return (salary >= 10000 && salary <= 100000) ? true : false;
	}

	public void printColumnNamesOfTable() {
		System.out.println(String.format("%s",
				"--------------------------------------------------------------------------------------------------------------"
						+ "-----------------------------------------------------------------"));
		System.out.format(stringFormatOne, "|", "UniqueID", "|", "First Name", "|", "Last Name", "|", "Mobile Number",
				"|", "E-mail", "|", "DateOfJoining", "|", "Salary", "|");
	}

	public void printDataOfTable(int i, List<Employee> list) {
		System.out.println(String.format("%s",
				"--------------------------------------------------------------------------------------------------------------"
						+ "-----------------------------------------------------------------"));
		System.out.format(StringFormatTwo, "|", list.get(i).getUid(), "|", list.get(i).getFname(), "|",
				list.get(i).getLname(), "|", list.get(i).getNumber(), "|", list.get(i).getEmail(), "|",
				list.get(i).getDateOfJoining(), "|", list.get(i).getSalary(), "|");

	}

//	public Employee getEmployeeClassObject() {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		Employee employee = ctx.getBean(Employee.class);
//		return employee;
//	}

}