package com.jayzhao.customactionbar.another_world.mvp;

/**
 * Created by Jay on 16-9-8.
 * 用户类
 * Model中的实体类
 */
public class User {
    private String mUserName = null;
    private String mPassword = null;

    public User(String username, String password) {
        this.mUserName = username;
        this.mPassword = password;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setUserName(String username) {
        this.mUserName = username;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}
