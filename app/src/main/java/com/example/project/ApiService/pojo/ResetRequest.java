package com.example.project.ApiService.pojo;

import java.io.Serializable;
import java.lang.String;

public class ResetRequest implements Serializable {
    private String msg;

    private String u_phonenumber;

    private String u_email;

    private String status;

    private String u_username;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getU_phonenumber() {
        return this.u_phonenumber;
    }

    public void setU_phonenumber(String u_phonenumber) {
        this.u_phonenumber = u_phonenumber;
    }

    public String getU_email() {
        return this.u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
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
