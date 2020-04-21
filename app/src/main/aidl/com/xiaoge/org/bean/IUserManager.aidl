// aidl文件所在包名 生成的java文件会在此包中
package com.xiaoge.org.bean;

import com.xiaoge.org.bean.User;
import com.xiaoge.org.bean.IOnNewUserAdded;

interface IUserManager{
    void addUser(in/*关键必须有*/ User user);
    List<User> getUsers();

    void registerListener(IOnNewUserAdded listener);
    void unRegisterListener(IOnNewUserAdded listener);
}