package com.example.project.ApiService.pojo;

import java.io.Serializable;
import java.lang.String;

public class VerifyforgetRequest implements Serializable {
    private String msg;

    private String u_phonenumber;

    private String status;

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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
