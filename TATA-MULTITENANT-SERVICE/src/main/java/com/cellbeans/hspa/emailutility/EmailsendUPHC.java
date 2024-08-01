package com.cellbeans.hspa.emailutility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.TimerTask;

@Component
public class EmailsendUPHC extends TimerTask {

    public static void main(String args[]) {
     /*   String date=new Date().toString();
        String mailToBcc[]={"vijayp@cellbeanshealthcare.com"};

        EmailsendUPHC ob = new EmailsendUPHC();
        try{
      //      String msg="Patient Count at UPHC NGP on 27.07.2018 is 233";
       //    System.out.println(msg);
        //   ob.sendMail("cbhisoft@gmail.com","apurvap@cellbeanshealthcare.com",mailToBcc ,"Test Mail from Medseva UPHC ", msg);
        }catch (Exception e){
            System.out.println("mail error :"+e);
        }*/
    }

    @Override
    public void run() {
        System.out.println("hello vijay");
    }

    //587 465 25
    public JavaMailSender javaMailService() throws Exception {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("Model.uphc.project@gmail.com");
        javaMailSender.setPassword("nagpur@2018");
        javaMailSender.setJavaMailProperties(getMailProperties());
       /* JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("mail.cellbeanshealthcare.com");
        javaMailSender.setPort(26);
        javaMailSender.setUsername("uphc@cellbeanshealthcare.com");
        javaMailSender.setPassword("1JS-O0we;m[M");
        javaMailSender.setJavaMailProperties(getMailProperties());*/
        return javaMailSender;
    }

    private Properties getMailProperties() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "false");
        return properties;
    }

    public void sendMail(String mailFrom, String mailTo, String[] mailToBcc, String mailSubject, String mailText) throws Exception {
        JavaMailSender javaMailSender = javaMailService();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailTo);
            helper.setFrom(new InternetAddress(mailFrom)); // b@gmail.com
            helper.setSubject(mailSubject);
            helper.setBcc(mailToBcc);
            helper.setText(mailText);
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            System.out.println("e :" + e);
        }

    }

}
