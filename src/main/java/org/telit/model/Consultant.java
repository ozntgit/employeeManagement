package org.telit.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Consultant extends Employee {
	// it seems to have 2 fields in common with the 'operation' type, however, since it's not stated
	// anywhere a correlation between the two i preferred to keep the fields duplicate.
	private Double payRate;
	private Double workedHours;
	private Date contractExpirationDate;
	private String serviceLevelAgreementInformation;

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}

	public Double getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(Double workedHours) {
		this.workedHours = workedHours;
	}

	public Date getContractExpirationDate() {
		return contractExpirationDate;
	}

	public void setContractExpirationDate(Date contractExpirationDate) {
		this.contractExpirationDate = contractExpirationDate;
	}

	public String getServiceLevelAgreementInformation() {
		return serviceLevelAgreementInformation;
	}

	public void setServiceLevelAgreementInformation(String serviceLevelAgreementInformation) {
		this.serviceLevelAgreementInformation = serviceLevelAgreementInformation;
	}
}
