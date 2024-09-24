package com.example.project.ApiService.pojo;

import java.io.Serializable;
import java.lang.String;

public class SlotRequest implements Serializable {
    private String msg;

    private String u_timeslotdate;

    private String u_id;

    private String u_slot_success;

    private String u_timeslottime;

    private String status;

    private String u_username;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getU_timeslotdate() {
        return this.u_timeslotdate;
    }

    public void setU_timeslotdate(String u_timeslotdate) {
        this.u_timeslotdate = u_timeslotdate;
    }

    public String getU_id() {
        return this.u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_slot_success() {
        return this.u_slot_success;
    }

    public void setU_slot_success(String u_slot_success) {
        this.u_slot_success = u_slot_success;
    }

    public String getU_timeslottime() {
        return this.u_timeslottime;
    }

    public void setU_timeslottime(String u_timeslottime) {
        this.u_timeslottime = u_timeslottime;
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
