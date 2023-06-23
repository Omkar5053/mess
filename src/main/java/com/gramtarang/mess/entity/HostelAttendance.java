package com.gramtarang.mess.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gramtarang.mess.enums.AttendanceStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class HostelAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hostel_attendance_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate date;

    @Enumerated(value = EnumType.ORDINAL)
    private AttendanceStatus attendanceStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public int getHostel_attendance_id() {
        return hostel_attendance_id;
    }

    public void setHostel_attendance_id(int hostel_attendance_id) {
        this.hostel_attendance_id = hostel_attendance_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
