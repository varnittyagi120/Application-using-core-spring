package dto;

import javax.persistence.Entity;

@Entity
public class Employee extends Person {
	private String salary;
	private String dateOfJoining;

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
}
