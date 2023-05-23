package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Ambulance;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.AmbulanceStatus;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.AmbulanceRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;
    private final UserRepository userRepository;
    private final AuditUtil auditLog;

    public AmbulanceService(AmbulanceRepository ambulanceRepository, UserRepository userRepository, AuditUtil auditLog) {
        this.ambulanceRepository = ambulanceRepository;
        this.userRepository = userRepository;
        this.auditLog = auditLog;
    }

    public List<Ambulance> getAll() throws MessException {
        return ambulanceRepository.findAll();
    }

    public List<Ambulance> getListByUser(int userId) throws MessException{
        return ambulanceRepository.findAllByUserUserId(userId);
    }

    public Ambulance add(int userId, RoleType roleType, Ambulance ambulance) throws MessException{
        Optional<User> user = userRepository.findById(userId);
        try {
            ambulance.setAmbulanceStatus(AmbulanceStatus.SUBMITTED);
            ambulance.setUser(user.get());
            ambulance.setLastMaintenanceDate(LocalDateTime.now());
            ambulance = ambulanceRepository.save(ambulance);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created AmbulanceData :" + ambulance + "RoleType:" + roleType);
            return ambulance;
        } catch (Exception ex) {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created AmbulanceData :" + ambulance + "RoleType:" + roleType + " Exception:" + ex);
        }

        return ambulance;
    }

    public Ambulance changeStatus(int ambulance_id) throws MessException{
        Optional<Ambulance> ambulance = ambulanceRepository.findById(ambulance_id);
        if(ambulance.isPresent()){
            ambulance.get().setAmbulanceStatus(AmbulanceStatus.APPROVED);
            return ambulanceRepository.save(ambulance.get());
        }
        throw new MessException("Error");
    }

    public void delete(int userId, RoleType roleType, int ambulanceId) throws MessException{
        Optional<User> user = userRepository.findById(userId);
        Optional<Ambulance> ambulance = ambulanceRepository.findById(ambulanceId);
        try {
            ambulanceRepository.deleteById(ambulanceId);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted AmbulanceData :" + ambulance + "RoleType:" + roleType);
        } catch (Exception ex) {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted AmbulanceData :" + ambulance + "RoleType:" + roleType + " Exception:" + ex);
        }
    }
}
