package com.jayzhao.customactionbar.another_world.mvp;

import com.jayzhao.customactionbar.MyUtils;

/**
 * Created by Jay on 16-9-8.
 * Model
 * 业务类
 */
public class UserLogin implements IUserLogin {
    @Override
    public void login(final String username, final String password, final LoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟登陆过程
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //模拟登陆成功
                if(username.equals("Jay") && password.equals("1234")) {
                    User user = new User(username, password);
                    listener.loginSuccess(user);
                } else {
                    listener.loginFail();
                }
            }
        }).start();
    }
}
