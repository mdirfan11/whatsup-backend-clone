package com.mdtech.whatsupbackendclone.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

    private String error;
    private String msg;
    private LocalDateTime timeStamp;

    public ErrorDetails() {}

    public ErrorDetails(String error, String msg, LocalDateTime timeStamp) {
        this.error = error;
        this.msg = msg;
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
