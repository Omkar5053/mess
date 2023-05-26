package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.RegistrationStatus;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.HostelRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelService {
    private HostelRepository hostelRepository;
    private UserRepository userRepository;
    @Autowired
    private AuditUtil auditLog;

    public HostelService(HostelRepository hostelRepository, AuditUtil auditLog, UserRepository userRepository) {
        this.hostelRepository = hostelRepository;
        this.auditLog = auditLog;
        this.userRepository = userRepository;
    }

    public ResponseEntityDto<Hostel> getAll(int userId)throws MessException {
        ResponseEntityDto<Hostel> hostels = new ResponseEntityDto<>();
        try {
            List<Hostel> data = hostelRepository.findAll();
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                if(user.get().getRoleType().toString() == "ADMIN"){
                    hostels.setListOfData(data);
                    hostels.setMessage("Successfully listed All Hostels");
                    hostels.setStatus(true);
                } else{
                    hostels.setMessage("Only ADMIN can list");
                    hostels.setStatus(false);
                }
            }
        } catch (Exception e) {
            hostels.setMessage("Can't List the Hostels");
            hostels.setStatus(false);
            throw new MessException("Can't List the Hostels");
        }
        return hostels;
    }

    public ResponseEntityDto<Hostel> addOrUpdate(int userId, Integer hostel_id, String hostelName) throws MessException{
        ResponseEntityDto<Hostel> hostels = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        Hostel hostel = null;
        try {
            if (hostel_id == 0) {
                hostel = new Hostel();
                hostels.setMessage("Hostel Added Successfully");
            } else {
                hostel = hostelRepository.findById(hostel_id).get();
                hostels.setMessage("Hostel Updated Successfully");
            }
            hostel.setHostelName(hostelName);
            hostel = hostelRepository.save(hostel);
            hostels.setStatus(true);
            hostels.setData(hostel);
            if (hostel_id == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created MessUserData :" + hostel + "RoleType:" + user.get().getRoleType());
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated MessUserData :" + hostel + "RoleType:" + user.get().getRoleType());
        } catch (Exception ex) {
            hostels.setMessage("Hostel Already Exists!!");
            hostels.setStatus(false);
            if (hostel_id == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created MessUserData :" + hostel + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated MessUserData :" + hostel + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
        }
        return hostels;
    }

    public ResponseEntityDto<Hostel> delete(int userId, Integer hostel_id) throws MessException {
        ResponseEntityDto<Hostel> hostels = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        Optional<Hostel> hostel = hostelRepository.findById(hostel_id);
        try {
            hostelRepository.deleteById(hostel_id);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted HostelData :" + hostel + "RoleType:" + user.get().getRoleType());
            hostels.setMessage("Hostel Deleted Successfully!!");
            hostels.setStatus(true);
        } catch (Exception ex) {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted HostelData :" + hostel + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
            hostels.setMessage("You Can't delete this Hostel!!");
            hostels.setStatus(false);
        }
        return hostels;
    }

    public Hostel getHostelById(Integer hostel_id) {
        return hostelRepository.findById(hostel_id).get();
    }



}
