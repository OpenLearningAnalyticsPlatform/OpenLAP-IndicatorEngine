/*
 *
 *  * Copyright (C) 2015  Tanmaya Mahapatra
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package com.goalla.email;

/**
 * Created by Tanmaya Mahapatra on 25-02-2015.
 */

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class MailPackage implements MailFunctionalities {
    /**
     Outgoing Mail (SMTP) Server
     requires TLS or SSL: smtp.gmail.com (use authentication)
     Use Authentication: Yes
     Port for SSL: 465
     */
    static Logger log = Logger.getLogger(MailPackage.class.getName());
    private final String fromEmail;
    private final String password;
    private String toEmail;
    private Properties mailServerProperties;

    MailPackage() {
        this.fromEmail = null;
        this.password = null;
        this.toEmail = null;
        this.mailServerProperties = null;
    }
    MailPackage(String fromEmail, String password) {
        this.fromEmail = fromEmail;
        this.password = password;
        this.toEmail = null;
        this.mailServerProperties = null;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public Properties getMailServerProperties() {
        return mailServerProperties;
    }

    public void setMailServerProperties(Properties mailServerProperties) {
        this.mailServerProperties = mailServerProperties;
    }

    public void sendMail(String toEmail, String subject, String body, Boolean choice, String filename) {

        this.toEmail = toEmail;
        log.info("sendMail Started");
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getDefaultInstance(mailServerProperties, auth);
        log.info("Session created");
        if(!choice)
            sendEmail(session, toEmail, subject, body);
        else
            sendAttachmentEmail(session, toEmail,subject, body, filename);
        //sendImageEmail(session, toEmail,"MailPackage Testing Subject with Image", "MailPackage Testing Body with Image");

    }
    private void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(this.fromEmail, "GoalOrientedLA"));
            msg.setReplyTo(InternetAddress.parse(this.fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            log.info("Message is ready");
            Transport.send(msg);
            log.info("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendAttachmentEmail(Session session, String toEmail, String subject, String body, String filename){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(this.fromEmail, "GoalOrientedLA"));
            msg.setReplyTo(InternetAddress.parse(this.fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText(body);
            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Second part is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            msg.setContent(multipart);
            // Send message
            Transport.send(msg);
            log.info("EMail Sent Successfully with attachment!!");
        }catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    /**
     * Utility method to send image in email body
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    // Not used as of now. Reserved for future use.
   private void sendImageEmail(Session session, String toEmail, String subject, String body){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@journaldev.com", "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("no_reply@journaldev.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "image.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            //Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);
            //third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached Image</h1>" +
                    "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);
            //Set the multipart message to the email message
            msg.setContent(multipart);
            // Send message
            Transport.send(msg);
            log.info("EMail Sent Successfully with image!!");
        }catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}