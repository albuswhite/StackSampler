package cmu.ediss.customerservice.service;

import cmu.ediss.customerservice.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerEventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCustomerRegisteredEvent(Customer customer) {
        String topic = "wenyuc2.customer.evt";
        log.info("v4");
        log.info("Message: {}", customer.toString());
        kafkaTemplate.send(topic, customer);
    }
}

