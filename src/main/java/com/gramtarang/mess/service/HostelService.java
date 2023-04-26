package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.HostelRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
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

    public List<Hostel> getAll() {
        return hostelRepository.findAll();
    }

    public Hostel add(int userId, RoleType roleType, Hostel hostel) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        try {
            hostel = hostelRepository.save(hostel);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created HostelData :" + hostel + "RoleType:" + roleType);
            return hostel;
        } catch (Exception ex) {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created HostelData :" + ex + " roleType " + roleType);
            throw new MessException(String.valueOf(ex));
        }
    }

    public void delete(int userId, RoleType roleType, Integer hostel_id) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Hostel> hostel = hostelRepository.findById(hostel_id);
        try {
            hostelRepository.deleteById(hostel_id);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted HostelData :" + hostel + "RoleType:" + roleType);
        } catch (Exception ex) {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted HostelData :" + hostel + "RoleType:" + roleType + " Exception:" + ex);
        }
    }
}
