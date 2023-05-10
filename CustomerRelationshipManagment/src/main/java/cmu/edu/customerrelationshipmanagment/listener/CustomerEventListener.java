package cmu.edu.customerrelationshipmanagment.listener;

import cmu.edu.customerrelationshipmanagment.entity.Customer;
import cmu.edu.customerrelationshipmanagment.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CustomerEventListener.java
 * @andrewID wenyuc2
 * @Description TODO
 */
@Slf4j
@Service
public class CustomerEventListener {
    ObjectMapper mapper;
    @Autowired
    private EmailService emailService;

    CustomerEventListener(){
        this.mapper=new ObjectMapper();
    }

    @KafkaListener(topics = "wenyuc2.customer.evt")
    public void receiveCustomerEvent(ConsumerRecord<String, String> kafkaMessage) throws JsonProcessingException {
        Customer customer = mapper.readValue(kafkaMessage.value(), Customer.class);
        log.info(customer.getUserId());
        emailService.sendRegistrationEmail(customer);
    }
}

