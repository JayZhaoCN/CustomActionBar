package com.jayzhao.customactionbar.another_world.mvp;

/**
 * Created by Jay on 16-9-8.
 * View
 * 接口
 */
public interface IUserLoginView {
    String getUserName();
    String getPassword();
    void clearUserName();
    void clearPassword();
    void showLoading();
    void hideLoading();
    void toMainActivity();
    void showLoginFail();
}
