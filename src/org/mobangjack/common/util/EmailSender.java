package org.mobangjack.common.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 发送普通邮件，接受普通邮件 发送带有附件的邮件，接收带有附件的邮件 发送html形式的邮件，接受html形式的邮件 发送带有图片的邮件等。
 */
public class EmailSender {

    public static final String EMAIL_BODY_HEADER = "";

    // 邮箱服务器
    private String host = "smtp.exmail.qq.com";
    private String username = "mobangjack@foxmail.com";
    private String password = "";

    private String personalName = "mobangjack";

    private String mail_from = username;

    public EmailSender(String host,String username,String password,String personalName) {
    	this.host = host;
    	this.username = username;
    	this.password = password;
    	this.personalName = personalName==null?this.personalName:personalName;
    }

    /**
     * 此段代码用来发送普通电子邮件
     */
    public boolean send(String subject, String[] mailTo, String mailBody) {
    	boolean flag = false;
        try {
            Properties props = new Properties(); // 获取系统环境
            Authenticator auth = new Email_Autherticator(); // 进行邮件服务器用户认证
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, auth);
            // 设置session,和邮件服务器进行通讯。
            MimeMessage message = new MimeMessage(session);
            // message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
            message.setSubject(subject == null?"":subject); // 设置邮件主题
            message.setText(mailBody); // 设置邮件正文
//			message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
            message.setSentDate(new Date()); // 设置邮件发送日期
            Address address = new InternetAddress(mail_from, personalName);
            message.setFrom(address); // 设置邮件发送者的地址
            Address toAddress = null;
            for (int i = 0; i < mailTo.length; i++) {
                toAddress = new InternetAddress(mailTo[i]); // 设置邮件接收方的地址
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            toAddress = null;
            Transport.send(message); // 发送邮件
            flag = true;
            System.out.println("send ok!");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * 用来进行服务器对用户的认证
     */
    public class Email_Autherticator extends Authenticator {
        public Email_Autherticator() {
            super();
        }

        public Email_Autherticator(String user, String pwd) {
            super();
            username = user;
            password = pwd;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

    public static void main(String[] args) {
        new EmailSender("smtp.exmail.qq.com","mobangjack@foxmail.com","","mobangjack").send("subject", new String[]{"1009584437@qq.com"}, "测试邮件内容");
    }

}
