package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by topwolf on 2017/5/11.
 */

public class UserBean implements Serializable{

    public String userName;
    public String realName;
    public String age;
    public int sex;
    public String birthday;
    public String token;
    private long userId;

    public UserBean(String userName, long userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getName() {
        return userName;
    }

    public long getId() {
        return userId;
    }

    @Invoke
    public static void staticMethod(String devName) {
        System.out.printf("Hi %s, I'm a static method", devName);
    }

    @Invoke
    public void publicMethod() {
        System.out.println("I'm a public method");
    }

    @Invoke
    private void privateMethod() {
        System.out.println("I'm a private method");
    }


    public @interface Invoke{

    }
}
