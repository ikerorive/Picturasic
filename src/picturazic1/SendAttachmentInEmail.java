package picturazic1;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
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

import com.sun.mail.smtp.SMTPTransport;

public class SendAttachmentInEmail {

	public static void mail(String filepath, String emailDest) {
		// Recipient's email ID needs to be mentioned.
		String to = emailDest;

		// Sender's email ID needs to be mentioned
		String from = "picturasic@gmail.com"; // picturasic@gmail.com 12345678aA@

		final String username = "picturasic@gmail.com";// change accordingly
		final String password = "12345678aA@";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		// String host = "relay.jangosmtp.net";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		// props.put("mail.smtp.connectiontimeout", "500");
		// props.put("mail.smtp.timeout", "500");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// System.out.println("1");
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			// System.out.println("2");
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// System.out.println("3");
			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			// System.out.println("4");
			// Set Subject: header field
			message.setSubject("Usuario Picturasic");
			// System.out.println("5");
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// System.out.println("6");
			// Now set the actual message
			messageBodyPart.setText("Gracias por unirte a la comunidad de Picturasic." + "\n"
					+ " Aqu� tienes tu QR identificativo." + "\n" + "\n" + "El equipo de Picturasic");
			// System.out.println("7");
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// System.out.println("8");
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// System.out.println("9");
			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = filepath;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			// System.out.println("10");
			// Send the complete message parts
			message.setContent(multipart);
			// System.out.println("11");
			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
