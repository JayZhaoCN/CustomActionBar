package com.jayzhao.customactionbar.another_world.mvp;

import android.os.Handler;
import android.util.Log;

/**
 * Created by Jay on 16-9-8.
 * Presenter
 */
public class UserLoginPresenter {
    private static final String TAG = "UserLoginPresenter";

    private IUserLogin mUserLogin = null;
    private IUserLoginView mUserLoginView = null;

    private Handler mHandler = null;

    public UserLoginPresenter(IUserLoginView loginView) {
        this.mUserLoginView = loginView;
        mUserLogin = new UserLogin();

        mHandler = new Handler();
    }

    public void login() {
        mUserLoginView.showLoading();
        mUserLogin.login(mUserLoginView.getUserName(), mUserLoginView.getPassword(), new LoginListener() {
            @Override
            public void loginSuccess(User user) {
                Log.i(TAG, "loginSuccess");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mUserLoginView.hideLoading();
                        mUserLoginView.toMainActivity();
                    }
                });
            }

            @Override
            public void loginFail() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mUserLoginView.hideLoading();
                        mUserLoginView.showLoginFail();
                    }
                });
            }
        });
    }

    public void clear() {
        mUserLoginView.clearPassword();
        mUserLoginView.clearUserName();
    }

}
