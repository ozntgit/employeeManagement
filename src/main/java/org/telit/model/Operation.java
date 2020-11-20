package org.telit.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Operation extends Employee{

    private Double workedHours;
    private Date contractExpirationDate;

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
}
