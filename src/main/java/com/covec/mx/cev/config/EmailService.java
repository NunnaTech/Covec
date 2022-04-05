package com.covec.mx.cev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String recipientEmail,String restPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("soportecovec@gmail.com", "Covec | Support");
        helper.setTo(recipientEmail);
        String subject = "COVEC: Solicitud de reestablecimiento de contraseña";

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("email",recipientEmail);
        map.put("restPasswordLink",restPasswordLink);
        Context context = new Context();
        context.setVariables(map);

        String html= templateEngine.process("email/sendEmail",context);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }
}
