package com.tencent.cloud.ai.equipmentmanagement.form;

public class LoginForm {

    private String username;
    private String password;
    private Integer userType;

    public LoginForm setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginForm setPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginForm setUserType(Integer userType) {
        this.userType = userType;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserType() {
        return userType;
    }
}
