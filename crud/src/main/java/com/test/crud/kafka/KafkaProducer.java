package com.test.crud.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void pushMessage(String message) {
        System.out.println("Pushing Message: " + message);
        this.kafkaTemplate.send("test_crud", message);
    }
}