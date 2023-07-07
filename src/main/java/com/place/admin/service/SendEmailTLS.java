package com.place.admin.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.place.admin.model.PropertyRequest;

import java.util.Properties;

public class SendEmailTLS {

	Properties prop = new Properties();

	public SendEmailTLS() {

	}

	public SendEmailTLS(Properties prop) {

		this.prop = prop;
	}

	public boolean sendMail(String to, String subject, String text) {
		boolean result = false;
		final String username = prop.getProperty("username");
		final String password = prop.getProperty("password");
		prop.remove("username");
		prop.remove("password");
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setContent(message, "text/html");
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
			result = true;
			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		
		

		final String username = "chskoop@gmail.com";
		final String password = "vwtyihfxnydgzsuf";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("chskoop@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("antmabiala@gmail.com, antbforever02@hotmail.com"));
			message.setSubject("Testing Gmail TLS");
			message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}