package dto;

import javax.persistence.Entity;

@Entity
public class Client extends Person {

	private String billAmount;
	private String Tenure;

	public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}

	public String getTenure() {
		return Tenure;
	}

	public void setTenure(String tenure) {
		Tenure = tenure;
	}

}
