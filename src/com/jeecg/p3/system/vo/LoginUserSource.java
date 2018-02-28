package com.jeecg.p3.system.vo;

import java.io.Serializable;

public class LoginUserSource implements Serializable
{
private static final long serialVersionUID = 1L;
private String userId;
private String userName;
private String password;
private String userStat;
private String opePhoneNum;
private String email;
private String userIcon;

public String getOpePhoneNum() {
	return opePhoneNum;
}
public void setOpePhoneNum(String opePhoneNum) {
	this.opePhoneNum = opePhoneNum;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getUserIcon() {
	return userIcon;
}
public void setUserIcon(String userIcon) {
	this.userIcon = userIcon;
}
public String getUserId()
{
  return this.userId;
}
public void setUserId(String userId) {
  this.userId = userId;
}
public String getUserName() {
  return this.userName;
}
public void setUserName(String userName) {
  this.userName = userName;
}
public String getPassword() {
  return this.password;
}
public void setPassword(String password) {
  this.password = password;
}
public String getUserStat() {
  return this.userStat;
}
public void setUserStat(String userStat) {
  this.userStat = userStat;
}

public String toString() {
  return "LoginUser [userId=" + this.userId + ", userName=" + this.userName + ", userStat=" + this.userStat + ", opePhoneNum=" + this.opePhoneNum + ", email=" + this.email + ", userIcon=" + this.userIcon + "]";
}
}