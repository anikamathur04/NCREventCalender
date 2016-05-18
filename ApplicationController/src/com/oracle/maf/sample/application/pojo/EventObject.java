package com.oracle.maf.sample.application.pojo;

public class EventObject
{
    private int eventID;
    private String eventName;
    private String description;
    private Boolean active;

    public EventObject()
    {

    }

    public EventObject(int EventID, String EventName, String Description,
                 Boolean Active)
    {
        this.eventID = EventID;
        this.eventName = EventName;
        this.description = Description;
        this.active = Active;
    }

    public void setEventID(int eventID)
    {
        this.eventID = eventID;
    }

    public int getEventID()
    {
        return eventID;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Boolean getActive()
    {
        return active;
    }

}
