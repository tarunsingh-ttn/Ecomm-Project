package com.TTN.Ecommerce.Services;


import com.TTN.Ecommerce.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;
    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Async
    public void sendEmail(String email,String Subject,String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(Subject);
        mailMessage.setFrom("tarunTTN@gmail.com");
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}
