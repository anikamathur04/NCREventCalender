package com.oracle.maf.sample.application.pojo;

public class AgendaObject
{
    private int agendaID;
    private int eventID;
    private String time;
    private int speakerID;
    private String speakerName;
    private String topic;

    public AgendaObject()
    {
        super();
    }

    public void setAgendaID(int agendaID)
    {
        this.agendaID = agendaID;
    }

    public int getAgendaID()
    {
        return agendaID;
    }

    public void setEventID(int eventID)
    {
        this.eventID = eventID;
    }

    public int getEventID()
    {
        return eventID;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getTime()
    {
        return time;
    }

    public void setSpeakerName(String speakerName)
    {
        this.speakerName = speakerName;
    }

    public String getSpeakerName()
    {
        return speakerName;
    }
    public void setSpeakerID(int speakerID)
    {
        this.speakerID = speakerID;
    }

    public int getSpeakerID()
    {
        return speakerID;
    }
    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getTopic()
    {
        return topic;
    }

}
