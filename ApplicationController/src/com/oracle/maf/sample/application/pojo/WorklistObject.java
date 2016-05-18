package com.oracle.maf.sample.application.pojo;

public class WorklistObject
{
  private long id;
  private String managerName;
  private String employeeName;
  private String requisitionInformation;
  private String description;
  private double total;
  private double tax;
  private String justification;
  private String fromEmployeeName;
  private String status;
  private String sentDate;

  public WorklistObject()
  {
    super();
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public long getId()
  {
    return id;
  }

  public void setManagerName(String managerName)
  {
    this.managerName = managerName;
  }

  public String getManagerName()
  {
    return managerName;
  }

  public void setEmployeeName(String employeeName)
  {
    this.employeeName = employeeName;
  }

  public String getEmployeeName()
  {
    return employeeName;
  }

  public void setRequisitionInformation(String requisitionInformation)
  {
    this.requisitionInformation = requisitionInformation;
  }

  public String getRequisitionInformation()
  {
    return requisitionInformation;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getDescription()
  {
    return description;
  }

  public void setTotal(double total)
  {
    this.total = total;
  }

  public double getTotal()
  {
    return total;
  }

  public void setTax(double tax)
  {
    this.tax = tax;
  }

  public double getTax()
  {
    return tax;
  }

  public void setJustification(String justification)
  {
    this.justification = justification;
  }

  public String getJustification()
  {
    return justification;
  }

  public void setFromEmployeeName(String fromEmployeeName)
  {
    this.fromEmployeeName = fromEmployeeName;
  }

  public String getFromEmployeeName()
  {
    return fromEmployeeName;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getStatus()
  {
    return status;
  }

  public void setSentDate(String sentDate)
  {
    this.sentDate = sentDate;
  }

  public String getSentDate()
  {
    return sentDate;
  }
}
