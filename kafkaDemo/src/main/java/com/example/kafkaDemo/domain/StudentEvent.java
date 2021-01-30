package com.example.kafkaDemo.domain;

import com.example.kafkaDemo.enums.StudentEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentEvent {
    private Integer studentEventId;
    private Student student;
    private StudentEventType studentEventType;

    public Integer getStudentEventId() {
        return studentEventId;
    }

    public void setStudentEventId(Integer studentEventId) {
        this.studentEventId = studentEventId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentEventType getStudentEventType() {
        return studentEventType;
    }

    public void setStudentEventType(StudentEventType studentEventType) {
        this.studentEventType = studentEventType;
    }
}