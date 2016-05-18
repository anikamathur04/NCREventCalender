package com.oracle.maf.sample.application.pojo;

public class SponsorObject
{
    public int sponsorID;
    public String name;
    public String profile;
    public String website;
    public Boolean active;

    public SponsorObject()
    {
        super();
    }

    public void setSponsorID(int sponsorID)
    {
        this.sponsorID = sponsorID;
    }

    public int getSponsorID()
    {
        return sponsorID;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getProfile()
    {
        return profile;
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
}
