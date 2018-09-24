package com.acme.anvil.service.mail;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;
import java.util.logging.XMLFormatter;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/** should trigger cloud-readiness rule for mail and logging to file **/
@Singleton
@Startup
public class MailSender {

	private static final Logger LOG = Logger.getLogger(MailSender.class.getName());

	public void sendMail() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.mailtrap.io");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("not", "existent");
			}
		});

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("from@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
			message.setSubject("Mail Subject");

			String msg = "This is my first email using JavaMailer";

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			log(e.getMessage());
		}

	}

	public void log(String logMessage) {
		Handler handler;
		try {
			handler = new SocketHandler("localhost", 8080);
			LogRecord logRec = new LogRecord(Level.INFO, "Log recorded");
			handler.publish(logRec);
			handler.setFormatter(new XMLFormatter());
			LOG.addHandler(handler);
			LOG.info("socket handler info message");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
