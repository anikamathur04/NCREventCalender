package com.oracle.maf.sample.mobile.mbeans.custom;

import com.oracle.maf.sample.mobile.mbeans.utils.DataControlsUtil;

import java.util.ArrayList;
import java.util.HashMap;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class WorklistAPIBacking
{
  private String managerName;
  private PropertyChangeSupport propertyChangeSupport =
    new PropertyChangeSupport(this);
  private String output;

  public void setOutput(String output)
  {
    String oldOutput = this.output;
    this.output = output;
    propertyChangeSupport.firePropertyChange("output", oldOutput, output);
  }

  public String getOutput()
  {
    return output;
  }

  public void setManagerName(String managerName)
  {
    String oldManagerName = this.managerName;
    this.managerName = managerName;
    propertyChangeSupport.firePropertyChange("managerName", oldManagerName,
                                             managerName);
  }

  public String getManagerName()
  {
    return managerName;
  }

  public WorklistAPIBacking()
  {
  }

  public void getWorklistItemsAction(ActionEvent actionEvent)
  {
    // Add event code here...
    String uri               = "/mobile/custom/ebsexpense/worklist";
    String httpMethod        = "GET";
    String payload           = "";
    
    if (getManagerName() != null)
    {
      uri = uri + "/" + getManagerName();
    }
    HashMap<String,String> httpHeaders = new HashMap<String, String>();
    httpHeaders.put("Accept", "application/json");
    
    //Here is how the method signature in the MobileBackednDC looks like: invokeCustomMcsAPI(String mafConnection, 
    //String requestURI, String httpMethod, String payload, HashMap httpHeaders)
    
    ArrayList<String>   paramNames = new ArrayList<String>();        
    paramNames.add("mafConnection");
    paramNames.add("requestURI");
    paramNames.add("httpMethod");
    paramNames.add("payload");
    paramNames.add("httpHeaders");
    
    ArrayList<Object>   paramValues = new ArrayList<Object>();        
    paramValues.add("MCSUTILRESTCONN"); //as defined in Application Resources --> Connections --> REST
    
            
    paramValues.add(uri);
    paramValues.add(httpMethod);
    paramValues.add(payload);
    paramValues.add(httpHeaders);
    
    ArrayList<Class> paramTypes = new ArrayList<Class>();
    paramTypes.add(String.class);
    paramTypes.add(String.class);
    paramTypes.add(String.class);
    paramTypes.add(String.class);
    paramTypes.add(HashMap.class);
    
    DataControlsUtil.invokeOnDataControl("invokeCustomMcsAPI", paramNames, paramValues, paramTypes);                                    
  }

  public void addPropertyChangeListener(PropertyChangeListener l)
  {
    propertyChangeSupport.addPropertyChangeListener(l);
  }

  public void removePropertyChangeListener(PropertyChangeListener l)
  {
    propertyChangeSupport.removePropertyChangeListener(l);
  }

}
