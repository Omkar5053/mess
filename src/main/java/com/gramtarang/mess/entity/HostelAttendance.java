package com.gramtarang.mess.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class HostelAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hostel_attendance_id;
    private Date date;

    private Date timeIn;

    private Date timeOut;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    public int getHostel_attendance_id() {
        return hostel_attendance_id;
    }

    public void setHostel_attendance_id(int hostel_attendance_id) {
        this.hostel_attendance_id = hostel_attendance_id;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Date getTimeIn() { return timeIn; }

    public void setTimeIn(Date timeIn) { this.timeIn = timeIn; }

    public Date getTimeOut() { return timeOut; }
    public void setTimeOut(Date timeOut) { this.timeOut = timeOut; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Hostel getHostel() { return hostel; }

    public void setHostel(Hostel hostel) { this.hostel = hostel; }
}
