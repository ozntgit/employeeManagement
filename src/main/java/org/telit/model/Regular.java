package org.telit.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.telit.api.util.BCryptSensistiveDataDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class Regular extends Employee {

	@Column(name = "ssn", length = 60) // BCrypted data will be always 60char
	@JsonDeserialize(using = BCryptSensistiveDataDeserializer.class)
	private String SSN;
	private String pensionInformation;

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String SSN) {
		this.SSN = SSN;
	}

	public String getPensionInformation() {
		return pensionInformation;
	}

	public void setPensionInformation(String pensionInformation) {
		this.pensionInformation = pensionInformation;
	}
}
