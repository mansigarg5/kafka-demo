package com.example.kafkaDemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StudentEventConsumer {
    private static final Logger log = LogManager.getLogger(StudentEventConsumer.class);

    @KafkaListener(topics = {"student-events"})
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord){
        log.info("Consumer Record :: {}",consumerRecord);
    }
}
