package com.gramtarang.mess.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gramtarang.mess.enums.AmbulanceStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AmbulanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    private String user;
    @ManyToOne
    @JoinColumn(name = "ambulance_id")
    private Ambulance ambulance;

    @Enumerated(value = EnumType.ORDINAL)
    private AmbulanceStatus ambulanceStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm:ss")
    private LocalDateTime requestDate;


    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Ambulance getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(Ambulance ambulance) {
        this.ambulance = ambulance;
    }

    public AmbulanceStatus getAmbulanceStatus() {
        return ambulanceStatus;
    }

    public void setAmbulanceStatus(AmbulanceStatus ambulanceStatus) {
        this.ambulanceStatus = ambulanceStatus;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
}
