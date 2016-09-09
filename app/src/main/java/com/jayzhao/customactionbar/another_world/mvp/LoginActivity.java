package com.jayzhao.customactionbar.another_world.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jayzhao.customactionbar.MainActivity;
import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.MyUtils;
import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-9-8.
 * View
 * 登陆界面
 */
public class LoginActivity extends MyBaseTitleActivity implements View.OnClickListener, IUserLoginView {
    private EditText mUserNameEdit = null;
    private EditText mPasswordEdit = null;
    private Button mLoginButton = null;
    private Button mClearButton = null;

    private UserLoginPresenter mPresenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        initViews();
        mPresenter = new UserLoginPresenter(this);
    }

    private void initViews() {
        mUserNameEdit = (EditText) findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mClearButton = (Button) findViewById(R.id.clear_btn);

        mLoginButton.setOnClickListener(this);
        mClearButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                mPresenter.login();
                break;
            case R.id.clear_btn:
                mPresenter.clear();
                break;
        }
    }

    @Override
    public String getUserName() {
        return mUserNameEdit.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEdit.getText().toString();
    }

    @Override
    public void clearUserName() {
        mUserNameEdit.setText("");
    }

    @Override
    public void clearPassword() {
        mPasswordEdit.setText("");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void showLoginFail() {
        MyUtils.showToast(this, "登陆失败");
    }
}
