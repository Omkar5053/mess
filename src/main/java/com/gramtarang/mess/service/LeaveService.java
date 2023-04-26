package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.*;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.LeaveStatus;
import com.gramtarang.mess.enums.LeaveType;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    @Autowired
    public final FeedbackRepository feedbackRepository;
    @Autowired
    public final UserRepository userRepository;
    @Autowired
    public final MessRepository messRepository;
    @Autowired
    public final HostelRepository hostelRepository;
    public final AuditUtil auditLog;
    @Autowired
    public final LeaveRepository leaveRepository;


    public LeaveService(FeedbackRepository feedbackRepository, UserRepository userRepository,
                        MessRepository messRepository, LeaveRepository leaveRepository, HostelRepository hostelRepository, AuditUtil auditLog) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.messRepository = messRepository;
        this.leaveRepository = leaveRepository;
        this.hostelRepository = hostelRepository;
        this.auditLog = auditLog;
    }

    public LeaveData addOrEditLeave(int userId, RoleType roleType, int leaveId, int leaveTypeId, Date startDate, Date endDate,
                                   String reason, String parentName, String parentPhoneNo, int hostelId) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        LeaveData leave = null;
        if (roleType == RoleType.STUDENT) {
            try {
                if (leaveId == 0) {
                    leave = new LeaveData();
                    leave.setStatus(LeaveStatus.PENDING);
                } else {
                    leave = leaveRepository.findById(leaveId).get();
                    if ((leave.getStatus() == LeaveStatus.APPROVED) || (leave.getStatus() == LeaveStatus.REJECTED)) {
                        throw new MessException("Can't update the leave data");
                    }
                }
                leave.setLeaveType(LeaveType.valueOf(leaveTypeId));
                leave.setStartDate(startDate);
                leave.setEndDate(endDate);
                leave.setReason(reason);
                leave.setParentName(parentName);
                leave.setParentNumber(parentPhoneNo);
                leave.setUser(user.get());
                Optional<Hostel> hostel = hostelRepository.findById(hostelId);
                if (hostel != null) {
                    leave.setHostel(hostel.get());
                }
                leave = leaveRepository.save(leave);
                leaveRepository.flush();
                if (leaveId == 0)
                    auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created LeaveData :" + leave + "RoleType:" + roleType);
                else
                    auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated LeaveData :" + leave + "RoleType:" + roleType);

            } catch (Exception ex) {
                if(leaveId == 0)
                    auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created LeaveData :" + leave + "RoleType:" + roleType + " Exception:" + ex);
                else
                    auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated LeaveData :" + leave + "RoleType:" + roleType + " Exception:" + ex);
            }
        }

        return leave;
    }

    public String deleteLeave(int userId, RoleType roleType, int leaveId) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Optional<LeaveData> leave = leaveRepository.findById(leaveId);
        if (roleType != RoleType.ADMIN) {
            try {
                leaveRepository.deleteById(leaveId);
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Delete LeaveData :" + leave + "RoleType:" + roleType);
            } catch(Exception ex) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Delete LeaveData :" + leave + "RoleType:" + roleType + " Exception:" + ex);
            }
        } else {
            throw new MessException(roleType + " can't delete the data");
        }
        return "Success";
    }

    public List<LeaveData> listOfLeavesByStudent(int userId, RoleType roleType) throws MessException {
        List<LeaveData> leaveDataList = new ArrayList<>();
        if ((roleType == RoleType.STUDENT)) {
            Optional<User> user = userRepository.findById(userId);
            leaveDataList = leaveRepository.findByUser(user.get());
        }
        return leaveDataList;
    }

    public List<LeaveData> listOfLeavesByHostel(int userId, RoleType roleType,int hostelId) throws MessException {
        List<LeaveData> leaveDataList = new ArrayList<>();
        Optional<Hostel> hostel = hostelRepository.findById(hostelId);
        if ((roleType == RoleType.ADMIN) || (roleType == RoleType.CHIEFWARDEN)) {
            if (hostel != null) {
                leaveDataList = leaveRepository.findByHostel(hostel.get());
            }
        } else if (roleType == RoleType.WARDEN) {
            List<LeaveData> leaveDataListByHostel = leaveRepository.findByHostel(hostel.get());
            for (LeaveData leaveData: leaveDataListByHostel) {
                if (leaveData.getStatus() == LeaveStatus.PENDING) {
                    leaveData.getActions().add("Approve");
                    leaveData.getActions().add("Reject");
                }
                leaveDataList.add(leaveData);
            }
        }
        return leaveDataList;
    }


}
