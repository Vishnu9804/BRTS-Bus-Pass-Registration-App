package com.example.project.ApiService.pojo;

import java.io.Serializable;
import java.lang.String;

public class LoginRequest implements Serializable {
    private String msg;

    private String u_id;

    private String u_mobileno;

    private String u_email;

    private String u_city;

    private String eligible;

    private String u_slot_success;

    private String u_doc_success;

    private String u_doc_status;

    private String status;

    private String u_username;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getU_id() {
        return this.u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_mobileno() {
        return this.u_mobileno;
    }

    public void setU_mobileno(String u_mobileno) {
        this.u_mobileno = u_mobileno;
    }

    public String getU_email() {
        return this.u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_city() {
        return this.u_city;
    }

    public void setU_city(String u_city) {
        this.u_city = u_city;
    }

    public String getEligible() {
        return this.eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }

    public String getU_slot_success() {
        return this.u_slot_success;
    }

    public void setU_slot_success(String u_slot_success) {
        this.u_slot_success = u_slot_success;
    }

    public String getU_doc_success() {
        return this.u_doc_success;
    }

    public void setU_doc_success(String u_doc_success) {
        this.u_doc_success = u_doc_success;
    }

    public String getU_doc_status() {
        return this.u_doc_status;
    }

    public void setU_doc_status(String u_doc_status) {
        this.u_doc_status = u_doc_status;
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
