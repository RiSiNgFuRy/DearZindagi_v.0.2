package com.project_work.dearzindagiv02;

public class timings {
    private String content;
    private String time;

    public timings(String time, String content)
    {
        this.content=content;
        this.time=time;
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
}
