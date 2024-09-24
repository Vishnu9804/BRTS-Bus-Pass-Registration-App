package com.example.project.ApiService.pojo;

import java.io.Serializable;
import java.lang.String;

public class RegisterRequest implements Serializable {
  private String msg;

  private String firstname;

  private String address;

  private String gender;

  private String city;

  private String phonenumber;

  private String lastname;

  private String password;

  private String pin;

  private String dob;

  private String landmark;

  private String category;

  private String email;

  private String status;

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPhonenumber() {
    return this.phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPin() {
    return this.pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public String getDob() {
    return this.dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getLandmark() {
    return this.landmark;
  }

  public void setLandmark(String landmark) {
    this.landmark = landmark;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
