package com.cometous.graduation.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Devilsen on 2015/5/17.
 */
public class User {

    @JSONField(name="_id")
    private String id;
    private String loginname;
    private String passwd;
    private String email;
    private String phone;
    private String title;
    private String school;
    private String phone_enable;
    private String email_enable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhone_enable() {
        return phone_enable;
    }

    public void setPhone_enable(String phone_enable) {
        this.phone_enable = phone_enable;
    }

    public String getEmail_enable() {
        return email_enable;
    }

    public void setEmail_enable(String email_enable) {
        this.email_enable = email_enable;
    }
}
