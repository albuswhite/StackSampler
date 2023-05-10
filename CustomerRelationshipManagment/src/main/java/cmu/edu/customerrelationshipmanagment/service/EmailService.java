package cmu.edu.customerrelationshipmanagment.service;

import cmu.edu.customerrelationshipmanagment.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName EmailService.java
 * @andrewID wenyuc2
 * @Description TODO
 */
@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationEmail(Customer customer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getUserId());
        message.setSubject("Activate your book store account");
        message.setText(createEmailBody(customer));
        log.info(Arrays.toString(message.getTo()));
        javaMailSender.send(message);
    }

    private String createEmailBody(Customer customer) {
        return "Dear " + customer.getName() + ",\n\n" +
                "Welcome to the Book store created by wenyuc2.\n" +
                "Exceptionally this time we won't ask you to click a link to activate your account.";
    }
}

