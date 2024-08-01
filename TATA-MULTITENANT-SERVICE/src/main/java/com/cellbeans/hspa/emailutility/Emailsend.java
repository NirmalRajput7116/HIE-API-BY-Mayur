package com.cellbeans.hspa.emailutility;

import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.sql.ResultSet;
import java.util.Properties;

@Component
public class Emailsend {
//    @Autowired
//    EmailRepository emailRepository;
//
//    @Autowired
//    private com.cellbeans.hspa.applicationproperty.Propertyconfig propertyconfig;
//
//   private String databaseUrl;
//    private String dbUserName;
//    private String dbPassword;
//    private String dbUrl;
//    public Emailsend() {
////        databaseUrl = propertyconfig.getDatabaseUrl();
////        dbUserName = propertyconfig.getDbUserName();
////        dbPassword = propertyconfig.getDbPassword();
//        System.out.println("hello "+databaseUrl);
//        System.out.println("hello "+dbUserName);
//        System.out.println("hello "+dbPassword);
//        try {
//            StringTokenizer st=new StringTokenizer(databaseUrl,"?");
//            if(st.hasMoreTokens())
//            {
//                 dbUrl=st.nextToken();
//            }
//
//            System.out.println("hello "+dbUrl);
//
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection(
//                    dbUrl, dbUserName, dbPassword);
////here sonoo is database name, root is username and password
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from email_configuration");
//            if (rs.next()) {
//                System.out.println("hello :" + rs.getString("mail_user_name"));
//            //    Emailsend ob = new Emailsend();
//          //      ob.sendMail("vijayp@cellbeanshealthcare.com", "hello test ", "hello hi", rs);
//            }
//
//            con.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
//
//    public EmailDTO getEmailSetting() {
//
//        List<EmailDTO> emailList = emailRepository.findAllByIsActiveTrueAndIsDeletedFalse();
//        System.out.println("emailConfig 1:" + emailList.get(0));
//        return emailList.get(0);
//    }

    public static void main(String args[]) {
      /*  EmailFormatCreateController mailFormat=new EmailFormatCreateController();
        String msg= mailFormat.getEmailData();
        System.out.println("mail msg :"+msg);*/
        //     Emailsend ob = new Emailsend();
//        EmailController ob = new EmailController() ;
//        ob.emailSend("vijayp@cellbeanshealthcare.com","hello test ","hello hi");
//        System.out.println("123 :" + ob.emailSend("vijayp@cellbeanshealthcare.com","hello test ","hello hi"));
        //     Emailsend obj = new Emailsend();
        //     System.out.println("e :" + obj.getEmailSetting());
        //     obj.sendMail("vijayp@cellbeanshealthcare.com", "Test Mail", "Hello Vijay");
        //    ob.sendMail("vijayp@cellbeanshealthcare.com", "hello test ", "hello hi", rs);
/*

   try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/qatar8", "root", "root");
//here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from email_configuration");
            if (rs.next()) {
                System.out.println("hello :" + rs.getString("mail_user_name"));
                Emailsend ob = new Emailsend();
                ob.sendMail("vijayp@cellbeanshealthcare.com", "hello test ", "hello hi", rs);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
*/
    }

    public JavaMailSender javaMailService(ResultSet rs) throws Exception {
        //     EmailDTO emailConfig = getEmailSetting();
        //     System.out.println("emailConfig 2:" + emailConfig);
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost(emailConfig.getMailHost());
//        javaMailSender.setPort(Integer.parseInt(emailConfig.getMailPort()));
//        javaMailSender.setUsername(emailConfig.getMailUserName());
//        javaMailSender.setPassword(emailConfig.getMailPassword());
        //javaMailSender.setJavaMailProperties(getMailProperties(rs));
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(rs.getString("mail_host"));
        javaMailSender.setPort(Integer.parseInt(rs.getString("mail_port")));
        javaMailSender.setUsername(rs.getString("mail_user_name"));
        javaMailSender.setPassword(rs.getString("mail_password"));
        javaMailSender.setJavaMailProperties(getMailProperties(rs));
        return javaMailSender;
    }

    private Properties getMailProperties(ResultSet rs) throws Exception {
        //   EmailDTO emailConfig = getEmailSetting();
//        Properties properties = new Properties();
//        properties.setProperty("mail.transport.protocol", emailConfig.getMailTrasportProtocol());
//        properties.setProperty("mail.smtp.auth", emailConfig.getMailSmtpAuth());
//        properties.setProperty("mail.smtp.starttls.enable", emailConfig.getMailSmtpStarttlsEnable());
//        properties.setProperty("mail.debug", emailConfig.getMailDebug());
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", rs.getString("mail_trasport_protocol"));
        properties.setProperty("mail.smtp.auth", rs.getString("mail_smtp_auth"));
        properties.setProperty("mail.smtp.starttls.enable", rs.getString("mail_smtp_starttls_enable"));
        properties.setProperty("mail.debug", rs.getString("mail_debug"));
        return properties;
    }

    public void sendMail(String mailTo, String mailSubject, String mailText, ResultSet rs) throws Exception {
        JavaMailSender javaMailSender = javaMailService(rs);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailTo);
            helper.setFrom(new InternetAddress(mailTo)); // b@gmail.com
            helper.setSubject(mailSubject);
            helper.setText(mailText);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("e :" + e);
        }

    }

    public void sendMAil1(String To, String msgContent, String subject) {
        if (Propertyconfig.getClientName().toLowerCase().contains("healthspring")) {
            final String username = Propertyconfig.getEmailUsername();
            final String password = Propertyconfig.getEmailPassword();
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username.toLowerCase(), password);
                }
            });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username.toLowerCase()));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To.trim()));
                message.setSubject(subject);
//                message.setText(msgContent, "text/html");
                message.setContent(msgContent, "text/html");
                Transport.send(message);
                System.out.println("Mail Sent");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMailWithAttachment(String To, String msgContent, String subject, MimeBodyPart attachment) {
        if (Propertyconfig.getClientName().toLowerCase().contains("healthspring")) {
            final String username = Propertyconfig.getEmailUsername();
            final String password = Propertyconfig.getEmailPassword();
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username.toLowerCase(), password);
                }
            });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username.toLowerCase()));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To.trim()));
                message.setSubject(subject);
//                message.setText(msgContent, "text/html");
                message.setContent(msgContent, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(attachment);
                message.setContent(multipart);
                Transport.send(message);
                System.out.println("Mail Sent");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

}
