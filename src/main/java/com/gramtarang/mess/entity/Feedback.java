package com.gramtarang.mess.entity;

import jakarta.persistence.*;

import java.util.stream.Stream;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    private String feedback;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "mess_id")
    private Mess mess;

    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    public String getFeedback() { return feedback; }

    public void setFeedback(String feedback) { this.feedback = feedback; }


    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }


    public Mess getMess() { return mess; }
    public void setMess(Mess mess) {this.mess = mess; }
}
