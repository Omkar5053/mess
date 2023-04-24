package com.gramtarang.mess.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hostel {

    private int id;
    private String hostelName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getHostelName() { return hostelName; }

    public void setHostelName(String hostelName) { this.hostelName = hostelName; }
}
