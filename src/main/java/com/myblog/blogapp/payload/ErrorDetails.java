package com.myblog.blogapp.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;

    public ErrorDetails(Date timeStamp, String message, String details){
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }
    //this works as setters.

    //now generating getters.
    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
