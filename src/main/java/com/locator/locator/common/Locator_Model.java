package com.locator.locator.common;

import javax.persistence.*;

@Entity
@Table(name = "Locator_LNC")
public class Locator_Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locator_Id")
    private int locatorId;

    @Column(name = "Cust_Name")
    private String custName;

    @Column(name = "Cust_Email")
    private String custEmail;

    @Column(name = "Cust_Phone")
    private String custPhone;

    @Column(name = "Cust_Address")
    private String custAddress;

    public int getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(int locatorId) {
        this.locatorId = locatorId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }
}
