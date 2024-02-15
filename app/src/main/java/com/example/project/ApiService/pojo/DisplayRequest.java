package com.example.project.ApiService.pojo;

import java.io.Serializable;
import java.lang.String;

public class DisplayRequest implements Serializable {
    private String msg;

    private String u_category;

    private String u_firstname;

    private String u_id;

    private String eligible;

    private String u_slot_success;

    private String u_doc_success;

    private String u_doc_status;

    private String status;

    private String u_username;

    private String u_lastname;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getU_category() {
        return this.u_category;
    }

    public void setU_category(String u_category) {
        this.u_category = u_category;
    }

    public String getU_firstname() {
        return this.u_firstname;
    }

    public void setU_firstname(String u_firstname) {
        this.u_firstname = u_firstname;
    }

    public String getU_id() {
        return this.u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
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

    public String getU_lastname() {
        return this.u_lastname;
    }

    public void setU_lastname(String u_lastname) {
        this.u_lastname = u_lastname;
    }
}
