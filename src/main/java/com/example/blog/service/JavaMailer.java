package com.example.blog.service;

import com.example.blog.dto.request.JavaMailerRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Configuration
public class JavaMailer implements MailService{
    private final JavaMailSender mailSender;

    @Override
    public void send(JavaMailerRequest javaMailerRequest) {
        String mailReceiver = javaMailerRequest.getTo();
        String  message = javaMailerRequest.getMessage();
        String subject = javaMailerRequest.getSubject();
        String sender = javaMailerRequest.getFrom();
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mailReceiver);
            mailMessage.setText(message);
            mailMessage.setSubject(subject);
            mailMessage.setFrom(sender);

            mailSender.send(mailMessage);
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }

    }
}