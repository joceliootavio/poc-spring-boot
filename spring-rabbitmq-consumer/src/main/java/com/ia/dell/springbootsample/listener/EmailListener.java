package com.ia.dell.springbootsample.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ia.dell.springbootsample.controller.dto.Email;

@Component
public class EmailListener {
	
	@Autowired
	public JavaMailSender emailSender;

    public void receiveMessage(Email email) {
        System.out.println("Received <" + email + ">");
        
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getRecipients().toArray(new String[] {}));
		message.setSubject(email.getSubject());
		message.setText(email.getBody());
		emailSender.send(message);
	}        

}