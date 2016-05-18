package com.oracle.maf.sample.application.pojo;

public class SpeakerObject
{
    public int speakerID;
    public String firstName;
    public String lastName;
    public String title;
    public String bio;
    public String website;
    public String Filename;
    public Boolean active;

    public SpeakerObject()
    {
        super();
    }

    public void setSpeakerID(int speakerID)
    {
        this.speakerID = speakerID;
    }

    public int getSpeakerID()
    {
        return speakerID;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getBio()
    {
        return bio;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setFilename(String Filename) {
        this.Filename = Filename;
    }

    public String getFilename() {
        return Filename;
    }

}
