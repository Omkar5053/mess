package com.gramtarang.mess.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class HostelAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;

    private Date timeIn;

    private Date timeOut;

    private User user;
    private Hostel hostel;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Date getTimeIn() { return timeIn; }

    public void setTimeIn(Date timeIn) { this.timeIn = timeIn; }

    public Date getTimeOut() { return timeOut; }
    public void setTimeOut(Date timeOut) { this.timeOut = timeOut; }
    @OneToMany
    @JoinColumn(name = "user_id")
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
    @OneToMany
    @JoinColumn(name = "hostel_id")
    public Hostel getHostel() { return hostel; }

    public void setHostel(Hostel hostel) { this.hostel = hostel; }
}
