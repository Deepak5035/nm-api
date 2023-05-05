package com.nearme.utilities;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SendRegistrationMail {

	@Autowired
	Environment environment;

	
	public void sendMail(String toEmailid , String fromEmailId , String fromPassword , String mailSubject , String mailBody) {

		String to = toEmailid;

		String from = fromEmailId;

		String password = fromPassword;

		// SMTP server address and port number
		String host = "smtp.gmail.com";
		int port = 587;

		// Set properties for SMTP server
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		// Get session for SMTP server
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			// Create a message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(mailSubject);
			message.setText(mailBody);

			// Send the message
			Transport.send(message);

			System.out.println("Email sent successfully.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
