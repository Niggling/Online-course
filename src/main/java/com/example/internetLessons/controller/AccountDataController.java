package com.example.internetLessons.controller;

import org.apache.ibatis.session.SqlSession;
import com.example.internetLessons.mapper.mainInterface.*;
import com.example.internetLessons.pojo.mainPojo.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;
import com.example.internetLessons.utils.SqlSessionUtils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@CrossOrigin(origins = {"*", "null"})


@RestController
@RequestMapping(value = "/accountData")
public  class AccountDataController {

    public AccountDataMapper getAccountDataMapper(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        AccountDataMapper mapper = sqlSession.getMapper(AccountDataMapper.class);
        return mapper;
    }

    //使用账号登录
    @PostMapping("/account")
    @ResponseBody
    public AccountData loginByAccount(@RequestBody AccountData account) {
        AccountData he = getAccountDataMapper().loginByAccount(account.getAccount(), account.getPassword());
        return he;//没有时返回空值
    }

    //使用邮箱登录时发送验证码或注册信件

    @PostMapping("/sendMessage")
    @ResponseBody
    public int sendMessage(@RequestBody AccountData account) throws MessagingException {

        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        //端口号，QQ邮箱端口587
        props.put("mail.smtp.port", "587");
        // 此处填写，写信人的账号
        props.put("mail.user", "657803436@qq.com");
        // 此处填写16位STMP口令
        props.put("mail.password", "cjlnayxjehsqbdib");
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress(account.getMail());//209283193   657803436
        message.setRecipient(MimeMessage.RecipientType.TO, to);
        // 设置邮件标题
        message.setSubject("网络学堂系统登录验证");
        int flag = 0;
        for (int i = 0; i <= 100; i++) {
            flag = new Random().nextInt(999999);
            if (flag < 100000) {
                flag += 100000;
            }
        }
        AccountData he = getAccountDataMapper().loginByMail(account.getMail());
        if (he != null)// 设置邮件的内容体
        {
            message.setContent("您的验证码为： " + flag, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);

        } else {
            int password = 0;
            for (int i = 0; i <= 100; i++) {
                password = new Random().nextInt(999999);
                if (password < 100000) {
                    password += 100000;
                }
            }

                try {
                    getAccountDataMapper().insertAccount("学生甲", account.getMail(),
                            String.valueOf(password), "学生");
                }catch(Exception e)
                {
                    return 0;
                }

                AccountData she = getAccountDataMapper().loginByMail(account.getMail());

                message.setContent("您的账号为：" + she.getAccount()+"  "+
                         "您的登录名为：" + she.getName()+"  "+
                         "您的密码为：" + she.getPassword()+"  "+
                         "您的身份为：" + she.getIdentity()+"  "+
                         "您的验证码为： " + flag, "text/html;charset=UTF-8");
                // 发送邮件
                Transport.send(message);

            }
        return flag;
    }

    //使用邮箱登录
    @PostMapping("/mail")
    @ResponseBody
    public AccountData loginByMail(@RequestBody AccountData account) {

        AccountData he = getAccountDataMapper().loginByMail(account.getMail());
        return he;
        // 默认是开启记住我的
    }

    //统计学员数量
    @PostMapping("/selectStudentNumber")
    @ResponseBody
    public int selectStudentNumber() {

        return getAccountDataMapper().selectNumberOfStudent().size();
    }
}


