package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Ambulance;
import com.gramtarang.mess.entity.Hostel;
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

    public ResponseEntityDto<Ambulance> getAll(int userId) throws MessException {
        ResponseEntityDto<Ambulance> ambulances = new ResponseEntityDto<>();
        try {
            List<Ambulance> data = ambulanceRepository.findAll();
            Optional<User> user = userRepository.findById(userId);
            ambulances.setListOfData(data);
            ambulances.setMessage("Successfully listed All Ambulances");
            ambulances.setStatus(true);

        } catch (Exception e) {
            ambulances.setMessage("Can't List the Ambulances");
            ambulances.setStatus(false);
            throw new MessException("Can't List the Ambulances");
        }
        return ambulances;
    }

    public ResponseEntityDto<Ambulance> getListByUser(int userId) throws MessException{
        ResponseEntityDto<Ambulance> ambulances = new ResponseEntityDto<>();
        try{
            List<Ambulance> data = ambulanceRepository.findAllByUserUserId(userId);
            ambulances.setListOfData(data);
            ambulances.setMessage("Successfully listed All Ambulances by User");
            ambulances.setStatus(true);
        } catch (Exception e) {
            ambulances.setMessage("Can't List the Ambulances");
            ambulances.setStatus(false);
            throw new MessException("Can't List the Ambulances");
        }
        return ambulances;
    }

//    public ResponseEntityDto<Ambulance> addOrEdit(int userId, Ambulance ambulance) throws MessException{
//        ResponseEntityDto<Ambulance> ambulances = new ResponseEntityDto<>();
//        Optional<User> user = userRepository.findById(userId);
//        try {
//
//            ambulance = ambulanceRepository.save(ambulance);
//            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created AmbulanceData :" + ambulance + "RoleType:" + roleType);
//            return ambulance;
//        } catch (Exception ex) {
//            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created AmbulanceData :" + ambulance + "RoleType:" + roleType + " Exception:" + ex);
//        }
//
//        return ambulance;
//    }

//    public ResponseEntityDto<Ambulance> changeStatus(int ambulance_id) throws MessException{
//        Optional<Ambulance> ambulance = ambulanceRepository.findById(ambulance_id);
//        if(ambulance.isPresent()){
//            ambulance.get().setAmbulanceStatus(AmbulanceStatus.APPROVED);
//            return ambulanceRepository.save(ambulance.get());
//        }
//        throw new MessException("Error");
//    }

//    public ResponseEntityDto<Ambulance> delete(int userId, RoleType roleType, int ambulanceId) throws MessException{
//        Optional<User> user = userRepository.findById(userId);
//        Optional<Ambulance> ambulance = ambulanceRepository.findById(ambulanceId);
//        try {
//            ambulanceRepository.deleteById(ambulanceId);
//            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted AmbulanceData :" + ambulance + "RoleType:" + roleType);
//        } catch (Exception ex) {
//            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted AmbulanceData :" + ambulance + "RoleType:" + roleType + " Exception:" + ex);
//        }
//    }
}
