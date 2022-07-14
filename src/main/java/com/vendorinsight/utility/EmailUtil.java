package com.vendorinsight.utility;

import java.io.FileInputStream;
import java.io.IOException;
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

public class EmailUtil {
	
//	public static void main (String[] args) throws IOException {
//		sendEmail("msingh@trinitypartners.com", "C:\\Users\\msingh\\trinityworkspace\\VendorInsightAutomation\\src\\main\\resources\\extent.properties");
//	}

              public static void sendEmail(String receiverEmail, String testFilePath) throws IOException {
                             

                             FileInputStream fis = new FileInputStream("C:\\Users\\msingh\\trinityworkspace\\VendorInsightAutomation\\Resources\\config.properties");
                             Properties props2 = new Properties();
                             props2.load(fis);
                             
                             final String smtpSenderEmail=props2.getProperty("SENDER_EMAIL_ID");
                             String smtpHost = props2.getProperty("HOST_NAME");
                             final String smtpPassword = props2.getProperty("SENDER_EMAIL_PASSWORD");
                             
                             System.out.println(smtpSenderEmail);
                            
                             Properties props = new Properties();
                             props.put("mail.smtp.host", smtpHost);
                             props.put("mail.smtp.socketFactory.port", "587");
                             props.put("mail.smtp.starttls.enable", "true");
              props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                             props.put("mail.smtp.auth", "true");
                             props.put("mail.smtp.port", "587");

                             Session session = Session.getDefaultInstance(props,
                                                          new javax.mail.Authenticator() {
                                                                        protected PasswordAuthentication getPasswordAuthentication() {
                                                                        return new PasswordAuthentication(smtpSenderEmail, smtpPassword);
                                                                        }

                                                          });

                             try {

                                           Message message = new MimeMessage(session);
                                           message.setFrom(new InternetAddress(smtpSenderEmail));
                             message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiverEmail));
                                           message.setSubject("VendorInsight_Automation_Report");
                                           BodyPart messageBodyPart1 = new MimeBodyPart();
                                           messageBodyPart1.setText("This is the automated generated HTML report for Vendor Insight regression test");
                                           MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                                           String filename = testFilePath;
                                           DataSource source = new FileDataSource(filename);
                                           messageBodyPart2.setDataHandler(new DataHandler(source));
                                           messageBodyPart2.setFileName(filename);
                                           Multipart multipart = new MimeMultipart();
                                           multipart.addBodyPart(messageBodyPart2);
                                           multipart.addBodyPart(messageBodyPart1);
                                           message.setContent(multipart);
                                           Transport.send(message);

                                           System.out.println("=====Email Sent=====");

                             } catch (MessagingException e) {

                                           throw new RuntimeException(e);

                             }

              }

}
