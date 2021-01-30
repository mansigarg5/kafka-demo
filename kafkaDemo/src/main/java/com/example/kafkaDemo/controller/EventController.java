package com.example.kafkaDemo.controller;

import com.example.kafkaDemo.domain.StudentEvent;
import com.example.kafkaDemo.enums.StudentEventType;
import com.example.kafkaDemo.producer.StudentEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

@RestController
public class EventController {
    @Autowired
    StudentEventProducer studentEventProducer;

    @PostMapping("/v1/student/event")
    public ResponseEntity<StudentEvent> postLibraryEvent(@RequestBody StudentEvent studentEvent)
            throws JsonProcessingException, TimeoutException {
        studentEvent.setStudentEventType(StudentEventType.NEW);
        studentEventProducer.sendStudentEventViaProducerRecord(studentEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentEvent);
    }

    @PutMapping("/v1/student/event")
    public ResponseEntity<?> putLibraryEvent(@RequestBody StudentEvent studentEvent)
            throws JsonProcessingException {
        if(Objects.isNull(studentEvent.getStudentEventId()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please provide the library event id");
        }
        studentEvent.setStudentEventType(StudentEventType.UPDATE);
        studentEventProducer.sendStudentEventViaProducerRecord(studentEvent);
        return ResponseEntity.status(HttpStatus.OK).body(studentEvent);
    }
}
