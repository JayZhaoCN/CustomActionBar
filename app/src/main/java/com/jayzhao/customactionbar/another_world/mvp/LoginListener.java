package com.jayzhao.customactionbar.another_world.mvp;

/**
 * Created by Jay on 16-9-8.
 * 登陆结果的回调接口
 */
public interface LoginListener {
    public void loginSuccess(User user);
    public void loginFail();
}
