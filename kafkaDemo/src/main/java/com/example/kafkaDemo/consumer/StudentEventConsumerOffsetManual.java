package com.example.kafkaDemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class StudentEventConsumerOffsetManual implements AcknowledgingMessageListener<Integer,String> {
    private static final Logger log = LogManager.getLogger(StudentEventConsumerOffsetManual.class);

    @Override
    public void onMessage(ConsumerRecord<Integer, String> data, Acknowledgment acknowledgment) {
        log.info("Consumer : {}",data);
        acknowledgment.acknowledge();
    }
}
