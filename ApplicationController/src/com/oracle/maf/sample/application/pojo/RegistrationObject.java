package com.oracle.maf.sample.application.pojo;

public class RegistrationObject
{
    public int RegistrationID;
    public String FirstName;
    public String LastName;
    public String JobTitle;
    public String Email;
    public String Phone;
    public String Company;

    public RegistrationObject()
    {

    }

    public void setRegistrationID(int RegistrationID) {
        this.RegistrationID = RegistrationID;
    }

    public int getRegistrationID() {
        return RegistrationID;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setJobTitle(String JobTitle) {
        this.JobTitle = JobTitle;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getCompany() {
        return Company;
    }

}
