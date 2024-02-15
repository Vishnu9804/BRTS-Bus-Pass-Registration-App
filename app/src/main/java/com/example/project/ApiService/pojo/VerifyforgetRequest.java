package com.example.project.ApiService.pojo;

import java.io.Serializable;
import java.lang.String;

public class VerifyforgetRequest implements Serializable {
    private String msg;

    private String status;

    private String u_username;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getU_username() {
        return this.u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }
}
