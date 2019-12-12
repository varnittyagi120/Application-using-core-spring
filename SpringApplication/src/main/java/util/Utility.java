package util;

import java.util.Calendar;
import java.util.Scanner;

public class Utility {
	static Scanner scanner = new Scanner(System.in);
	static String regxForNumber = "(0/91)?[6-9][0-9]{9}";
	static String regxForEmail = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	public static boolean validateName(String str) {
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public static boolean validateMobileNumber(String number) {
		return (!number.matches(regxForNumber)) ? false : true;
	}

	public static boolean validateEmail(String email) {
		return (!email.matches(regxForEmail)) ? false : true;
	}

	public static boolean checkRequiredField(String str) {
		if (str.isEmpty()) {
			System.out.println("required");
			return false;
		}
		return true;
	}

	public static String getUid() {
		System.out.println("Enter uuid");
		String str = scanner.nextLine();
		return str;
	}

	public static boolean validateYear(int year) {
		boolean flag = false;
		if (year >= 2000 && year <= Calendar.getInstance().get(Calendar.YEAR)) {
			flag = true;
		} else {
			System.out.println("please enter year between 2000>= and <=" + Calendar.getInstance().get(Calendar.YEAR));
			flag = false;
		}
		return flag;
	}

	public static boolean validateMonth(int year, int month) {
		boolean flag = true;
		if (year == Calendar.getInstance().get(Calendar.YEAR)) {
			if (month >= 1 && month <= Calendar.getInstance().get(Calendar.MONTH)) {
				flag = true;
			} else {
				System.out.println("Date of joining month can not be outside the given range");
				flag = false;
			}
		} else {
			if (month >= 1 && month <= 12) {
				flag = true;
			} else {
				System.out.println("please enter month between 1>= and <=12 ");
				flag = false;
			}
		}
		return flag;

	}

	public static boolean validateLeapYear(int year) {
		boolean flag = false;
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					flag = true;
				} else {
					flag = false;
				}
			} else {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public static boolean validateDate(int year, boolean leap, int month, int date) {
		boolean flag;
		if (year == Calendar.getInstance().get(Calendar.YEAR) && month == Calendar.getInstance().get(Calendar.MONTH)) {
			if (date >= 1 && date <= Calendar.getInstance().get(Calendar.DATE)) {
				flag = true;
			} else {
				flag = false;
				System.out.println("Date of joining can not be outside the given range");
			}
		} else {
			if (month == 2) {
				if (leap) {
					if (date >= 1 && date <= 29) {
						flag = true;
					} else {
						flag = false;
					}
				} else {
					if (date >= 1 && date <= 28) {
						flag = true;
					} else {
						flag = false;
					}
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (date >= 1 && date <= 30) {
					flag = true;
				} else {
					flag = false;
				}
			} else {
				if (date >= 1 && date <= 31) {
					flag = true;
				} else {
					flag = false;
				}
			}
			if (!flag) {
				System.out.println("Please enter correct date");
			}
		}
		return flag;
	}

	public static String getTableName(String className) {
		if (className.equals("Employee")) {
			return "employee100";
		} else if (className.equals("Client")) {
			return "client100";
		}
		return null;
	}

}
