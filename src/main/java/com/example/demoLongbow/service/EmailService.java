package com.example.demoLongbow.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    public JavaMailSender javaMailSender;
    @Autowired
    public TemplateEngine templateEngine;
    public void simpleEmailText(String fromEmail, String toEmail,String subject,String mailBody){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(mailBody);
        javaMailSender.send(message);

    }

    public void htmlEmailFormat(String fromEmail, String toEmail,String subject,String mailBody) throws Exception{
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");

    helper.setFrom(fromEmail);
     helper.setTo(toEmail);
     helper.setSubject(subject);
        helper.setText(mailBody);
        javaMailSender.send(message);

    }
    public void templateEmailFormat(String fromEmail, String toEmail,String subject,String fileName) throws Exception{
        Context context=new Context();
        context.setVariable("name","Kumar");
        String mailBody=templateEngine.process(fileName,context);
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(mailBody,true);
        javaMailSender.send(message);

    }


}
