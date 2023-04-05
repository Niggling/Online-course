package com.example.internetLessons.mapper.mainInterface;
//import com.example.internetLessons.pojo.six.Employee_project;
import com.example.internetLessons.pojo.mainPojo.AccountData;
import com.example.internetLessons.pojo.mainPojo.LessonData;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AccountDataMapper {


        /**
         * 自动注册
         */
        void insertAccount(String name, String e_mail,String password,String identity);
        //查询学生数量
        List<AccountData> selectNumberOfStudent();
        /**
         * 修改密码
         */
/*
        void updatePassword(String newPassword,String username, String password);

*/


        //根据用户名密码查找用户表（登录）

        //账号登录,根据account在用户表查找用户
        AccountData loginByAccount(String account, String password);
        //邮箱登录,根据mail在用户表查找用户
        AccountData loginByMail(String mail);




}
