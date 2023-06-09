package com.gramtarang.mess.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gramtarang.mess.common.BaseDto;
import com.gramtarang.mess.enums.LeaveStatus;
import com.gramtarang.mess.enums.LeaveType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class LeaveData extends BaseDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveId;
    @Enumerated(value = EnumType.ORDINAL)
    private LeaveType leaveType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private String reason;
    @Enumerated(value = EnumType.ORDINAL)
    private LeaveStatus status;
    private String parentName;
    private String parentNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    private String remark;

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

    public Hostel getHostel() {
        return hostel;
    }
    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
