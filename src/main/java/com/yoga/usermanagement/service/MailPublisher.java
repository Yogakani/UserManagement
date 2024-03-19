package com.yoga.usermanagement.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class MailPublisher {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String emailId, String subject, String data) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom("yogakani32@gmail.com", "Foodzie Inc");
        helper.setTo(emailId);
        helper.setSubject(subject);
        helper.setText(data, true);

        log.info("Sending mail to : {}", emailId);
        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendOTP(String emailId, int otp) throws MessagingException, UnsupportedEncodingException {
        String data = getEmailOTPContent(otp);
        sendMail(emailId, "Foodzie - OTP for verification", data);
    }

    private String getEmailOTPContent(int otp) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <meta name=\"theme-color\" content=\"#000000\" />\n" +
                "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"card position-absolute top-50 start-50 translate-middle\" style=\"width: 30rem;\">\n" +
                "        <div class=\"card-header\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"row mt-3 mb-3\">\n" +
                "                    <div class=\"col-3\"></div>\n" +
                "                    <div class=\"col-6 text-center\">\n" +
                "                        <img src=\"https://yogabucket-1.s3.amazonaws.com/bitcoin.png\" alt=\"logo\" />\n" +
                "                    </div>\n" +
                "                    <div class=\"col-3\"></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"card-body\">\n" +
                "            <p>Dear Customer,</p>\n" +
                "            <p>Your One Time Passcode for verifying the email address is <b>" + otp + "</b>.</p>\n" +
                "            <p>Please use this passcode to complete your transaction. Do not share this Passcode with anyone.</p>\n" +
                "            <p class=\"mb-1\">\n" +
                "                Thank you, \n" +
                "            </p>\n" +
                "            <p>\n" +
                "                Fodizee Team.\n" +
                "            </p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }
}
