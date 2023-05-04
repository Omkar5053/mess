package com.gramtarang.mess.entity.auditlog;

//Audit Log entity

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Audit_Log")
public class AuditLog {
    private long id;
    private AuditOperation operation;
    private String userName;
    private Status status;
    private String logData;
    private Date logDate;
    //Added to store the IP informations
    private String ipdetails;
    private String previousData;
    private String newData;
    // New fields added for easy search
    private String studentRegNo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "operation")
    @Enumerated(EnumType.ORDINAL)
    public AuditOperation getOperation() {
        return operation;
    }

    public void setOperation(AuditOperation operation) {
        this.operation = operation;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "log_data")
    public String getLogData() {
        return logData;
    }

    public void setLogData(String logdata) {
        this.logData = logdata;
    }

    @Column(name = "log_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm:ss")
    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logdate) {
        this.logDate = logdate;
    }

    // Added to track IP informations
    @Column(name = "ip_details")
    public String getIPDetails() {
        return ipdetails;
    }

    public void setIPDetails(String ipdetails) {
        this.ipdetails = ipdetails;
    }

    @Column(name = "previous_data")
    public String getPreviousData() {
        return previousData;
    }

    public void setPreviousData(String previousData) {
        this.previousData = previousData;
    }

    @Column(name = "new_data")
    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    @Column(name = "student_reg_no", nullable = true)
    public String getStudentRegNo() {
        return studentRegNo;
    }

    public void setStudentRegNo(String studentRegNo) {
        this.studentRegNo = studentRegNo;
    }


}
