package com.project_work.dearzindagiv02;

public class timings {
    private String content;
    private String time;
    private String name;
    private String numoftimes;
    private String expiry;
    private String description;

    public timings(String time, String content,String name,String numoftimes,String expiry,String description)
    {
        this.content=content;
        this.time=time;
        this.name=name;
        this.numoftimes=numoftimes;
        this.expiry=expiry;
        this.description=description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumoftimes() {
        return numoftimes;
    }

    public void setNumoftimes(String numoftimes) {
        this.numoftimes = numoftimes;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
