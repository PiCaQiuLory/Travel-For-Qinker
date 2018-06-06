package com.ss.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author lory
 * @description 邮件发送
 * @Package util
 * @date 2018年1月15日
 */
public class MailSendUtils {

	private static final String MAIL_TYPE = "smtp";
	
	private static final String MAIL_HOST = "smtp.exmail.qq.com";
	
	private static final String MAIL_AUTO = "true";
	
	private static final String MAIL_SEND_MAIL_ACCOUNT = "email@qinker.com";
	
	private static final String MAIL_SEND_MAIL_PASS = "LoryAndKevin123";
	
	private static final String MAIL_SUBJECT = "订单结果反馈";
	
	private static final String MAIL_FRONNAME = "QINKER COMPANY";
	
	public static void sendMail(String receiveMail,String mailContent,String receiveName) throws Exception {
		Properties pro = new Properties();
		pro.setProperty("mail.transport.protocol", MAIL_TYPE);
		pro.setProperty("mail.smtp.host", MAIL_HOST);
		pro.setProperty("mail.smtp.auth", MAIL_AUTO);
		Session session = Session.getInstance(pro);
		MimeMessage message = createMimeMessage(session, MAIL_SEND_MAIL_ACCOUNT, receiveMail,receiveName,mailContent);
		Transport transport = session.getTransport();
		transport.connect(MAIL_SEND_MAIL_ACCOUNT, MAIL_SEND_MAIL_PASS);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		
	}
	
	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String receiveName,String mailContent) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail,MAIL_FRONNAME, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveName, "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(MAIL_SUBJECT, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(mailContent, "text/html;charset=UTF-8");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 6. 设置发件时间
        message.setSentDate(cal.getTime());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}
