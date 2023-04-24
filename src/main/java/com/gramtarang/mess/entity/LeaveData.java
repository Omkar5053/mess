package com.gramtarang.mess.entity;

import com.gramtarang.mess.enums.LeaveStatus;
import com.gramtarang.mess.enums.LeaveType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class LeaveData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveId;
    @Enumerated(value = EnumType.ORDINAL)
    private LeaveType leaveType;
    private Date startDate;
    private Date endDate;
    private String reason;
    @Enumerated(value = EnumType.ORDINAL)
    private LeaveStatus status;
    private String parentName;
    private String parentNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }



    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(String parentNumber) {
        this.parentNumber = parentNumber;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
