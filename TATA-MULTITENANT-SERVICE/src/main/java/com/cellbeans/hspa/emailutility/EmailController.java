package com.cellbeans.hspa.emailutility;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/sendmail")
public class EmailController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    EmailRepository emailRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody EmailDTO emailDTO) {
        TenantContext.setCurrentTenant(tenantName);
        if (emailDTO != null) {
            emailRepository.save(emailDTO);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{mailId}")
    public EmailDTO read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mailId") Long mailId) {
        TenantContext.setCurrentTenant(tenantName);
        EmailDTO emailDTO = emailRepository.getById(mailId);
        return emailDTO;
    }

    @RequestMapping("update")
    public EmailDTO update(@RequestHeader("X-tenantId") String tenantName, @RequestBody EmailDTO emailDTO) {
        TenantContext.setCurrentTenant(tenantName);
        return emailRepository.save(emailDTO);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<EmailDTO> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "mailId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return emailRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return emailRepository.findAllByMailUserNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @GetMapping
    @RequestMapping("maillist")
    public List<EmailDTO> mailList(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("emailRepository.findAllByIsActiveTrueAndIsDeletedFalse() :" + emailRepository.findAllByIsActiveTrueAndIsDeletedFalse());
        return emailRepository.findAllByIsActiveTrueAndIsDeletedFalse();
    }

    @PutMapping("delete/{mailId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mailId") Long mailId) {
        TenantContext.setCurrentTenant(tenantName);
        EmailDTO emailDTO = emailRepository.getById(mailId);
        if (emailDTO != null) {
            emailDTO.setIsDeleted(true);
            emailRepository.save(emailDTO);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    public EmailDTO getEmailSetting(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        List<EmailDTO> emailList = emailRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        System.out.println("emailConfig 1:" + emailList.get(0));
        return emailList.get(0);
    }

    public JavaMailSender javaMailService(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        EmailDTO emailConfig = getEmailSetting(tenantName);
        System.out.println("emailConfig 2:" + emailConfig);
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfig.getMailHost());
        javaMailSender.setPort(Integer.parseInt(emailConfig.getMailPort()));
        javaMailSender.setUsername(emailConfig.getMailUserName());
        javaMailSender.setPassword(emailConfig.getMailPassword());
        javaMailSender.setJavaMailProperties(getMailProperties(tenantName));
   /*     javaMailSender.setHost("mail.cellbeanshealthcare.com");
        javaMailSender.setPort(26);
        javaMailSender.setUsername("vijayp@cellbeanshealthcare.com");
        javaMailSender.setPassword("cellbeans@2018");
        javaMailSender.setJavaMailProperties(getMailProperties());*/
        return javaMailSender;
    }

    private Properties getMailProperties(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        EmailDTO emailConfig = getEmailSetting(tenantName);
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", emailConfig.getMailTrasportProtocol());
        properties.setProperty("mail.smtp.auth", emailConfig.getMailSmtpAuth());
        properties.setProperty("mail.smtp.starttls.enable", emailConfig.getMailSmtpStarttlsEnable());
        properties.setProperty("mail.debug", emailConfig.getMailDebug());
        return properties;
    }

    //  public void sendMail(String mailTo, String mailSubject, String mailText) {
    @GetMapping
    @RequestMapping("emailsend")
    public void emailSend(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "mailTo") String mailTo, @RequestParam(value = "mailSubject") String mailSubject, @RequestParam(value = "mailText") String mailText) {
        TenantContext.setCurrentTenant(tenantName);
        JavaMailSender javaMailSender = javaMailService(tenantName);
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

}
