package com.example.internetLessons.pojo.mainPojo;

import lombok.Data;
@Data
public class AccountData {

    private String account;

    private String name;

    private String mail;
//
//    private String newPassword;//新密码或确认密码，数据库不含该属性

    private String password;//加密

    private String identity;



}
