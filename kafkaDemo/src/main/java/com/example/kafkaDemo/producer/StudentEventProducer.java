package com.example.kafkaDemo.producer;

import com.example.kafkaDemo.domain.StudentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class StudentEventProducer {

    private static final Logger log = LogManager.getLogger(StudentEventProducer.class);

    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private String topic = "student-events";

    public void sendStudentEvent(StudentEvent studentEvent) throws JsonProcessingException {

        Integer key = studentEvent.getStudentEventId();
        String value = objectMapper.writeValueAsString(studentEvent);
        ListenableFuture<SendResult<Integer,String>> listenableFuture = kafkaTemplate.sendDefault(key,value);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("...Failure..." + ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("...onSuccess..." + result.getProducerRecord().value());
            }
        });
    }


    public void sendStudentSyncEvent(StudentEvent studentEvent) throws JsonProcessingException, java.util.concurrent.TimeoutException {
        Integer key = studentEvent.getStudentEventId();
        String value = objectMapper.writeValueAsString(studentEvent);
        try {
            SendResult<Integer, String> sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    public void sendStudentEventViaProducerRecord(StudentEvent studentEvent) throws JsonProcessingException {

        Integer key = studentEvent.getStudentEventId();
        String value = objectMapper.writeValueAsString(studentEvent);
        List<Header> headerList = List.of(new RecordHeader("source","scanner".getBytes()));
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topic, null, key, value, headerList);
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("...Failure..." + ex);
            }
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("...onSuccess..." + result.getProducerRecord().value());
            }
        });
    }
}