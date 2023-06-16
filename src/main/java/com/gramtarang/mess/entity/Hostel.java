package com.gramtarang.mess.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hostel_id;
    private String hostelName;

    private int noOfFloors;

    private int noOfRoomPerFloor;

    private int noOfStudentPerRoom;



    public int getHostel_id() {
        return hostel_id;
    }

    public void setHostel_id(int hostel_id) {
        this.hostel_id = hostel_id;
    }

    public String getHostelName() { return hostelName; }

    public void setHostelName(String hostelName) { this.hostelName = hostelName; }

    public int getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(int noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public int getNoOfRoomPerFloor() {
        return noOfRoomPerFloor;
    }

    public void setNoOfRoomPerFloor(int noOfRoomPerFloor) {
        this.noOfRoomPerFloor = noOfRoomPerFloor;
    }

    public int getNoOfStudentPerRoom() {
        return noOfStudentPerRoom;
    }

    public void setNoOfStudentPerRoom(int noOfStudentPerRoom) {
        this.noOfStudentPerRoom = noOfStudentPerRoom;
    }
}
