package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Ambulance;
import com.gramtarang.mess.entity.AmbulanceRequest;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.AmbulanceStatus;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.AmbulanceRepository;
import com.gramtarang.mess.repository.AmbulanceRequestRepository;
import com.gramtarang.mess.repository.HostelRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;

    private final AmbulanceRequestRepository ambulanceRequestRepository;
    private final UserRepository userRepository;
    private final AuditUtil auditLog;
    private final HostelRepository hostelRepository;

    public AmbulanceService(AmbulanceRepository ambulanceRepository, UserRepository userRepository, AuditUtil auditLog, AmbulanceRequestRepository ambulanceRequestRepository,
                            HostelRepository hostelRepository) {
        this.ambulanceRepository = ambulanceRepository;
        this.userRepository = userRepository;
        this.auditLog = auditLog;
        this.ambulanceRequestRepository = ambulanceRequestRepository;
        this.hostelRepository = hostelRepository;
    }

    public ResponseEntityDto<Ambulance> getAll(int userId) throws MessException {
        ResponseEntityDto<Ambulance> ambulances = new ResponseEntityDto<>();
        try {
            List<Ambulance> data = ambulanceRepository.findAll();
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                    ambulances.setListOfData(data);
                    ambulances.setMessage("Successfully listed All Ambulances");
                    ambulances.setStatus(true);
            }
        } catch (Exception e) {
            ambulances.setMessage("Can't List the Ambulances");
            ambulances.setStatus(false);
            throw new MessException("Can't List the Ambulances");
        }
        return ambulances;
    }

    public ResponseEntityDto<AmbulanceRequest> getAllRequests(int userId) throws MessException{
        ResponseEntityDto<AmbulanceRequest> ambulanceRequests = new ResponseEntityDto<>();
        try{
            Optional<User> user = userRepository.findById(userId);
            List<AmbulanceRequest> data = ambulanceRequestRepository.findAll();
            if(user.isPresent()){
                if(user.get().getRoleType().equals(RoleType.ADMIN)){
                    ambulanceRequests.setListOfData(data);
                    ambulanceRequests.setMessage("Successfully listed All Ambulance Requests");
                    ambulanceRequests.setStatus(true);
                }else{
                    ambulanceRequests.setMessage("Unauthorized User");
                    ambulanceRequests.setStatus(false);
                }
            }
        } catch (Exception e) {
            ambulanceRequests.setMessage("Can't List the Ambulances Requests");
            ambulanceRequests.setStatus(false);
            throw new MessException("Can't List the Ambulance Requests");
        }
        return ambulanceRequests;
    }

    public ResponseEntityDto<Ambulance> addOrEdit(int ambulance_id, String ambulanceName, String licensePlate, int userId) throws MessException{
        ResponseEntityDto<Ambulance> ambulances = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        Ambulance ambulance = null;
        try {
            if (ambulance_id == 0) {
                ambulance = new Ambulance();
                ambulances.setMessage("Ambulance Added Successfully");
            } else {
                ambulance = ambulanceRepository.findById(ambulance_id).get();
                ambulances.setMessage("Ambulance Updated Successfully");
            }
            ambulance.setAmbulanceName(ambulanceName);
            ambulance.setLicensePlate(licensePlate);
            ambulance = ambulanceRepository.save(ambulance);
            ambulances.setStatus(true);
            ambulances.setData(ambulance);
            if (ambulance_id == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created AmbulanceData :" + ambulance + "RoleType:" + user.get().getRoleType());
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated AmbulanceData :" + ambulance + "RoleType:" + user.get().getRoleType());
        } catch (Exception ex) {
            ambulances.setMessage("Ambulance Already Exists!!");
            ambulances.setStatus(false);
            if (ambulance_id == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created AmbulanceData :" + ambulance + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated AmbulanceData :" + ambulance + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
        }
        return ambulances;
    }

    public ResponseEntityDto<AmbulanceRequest> changeStatus(int requestId, Integer userId) throws MessException{
        ResponseEntityDto<AmbulanceRequest> ambulanceRequests = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        try{
            Optional<AmbulanceRequest> ambulanceRequest = ambulanceRequestRepository.findById(requestId);
            if(ambulanceRequest.isPresent()){
                if(user.get().getRoleType() != RoleType.STUDENT){
                    ambulanceRequest.get().setAmbulanceStatus(AmbulanceStatus.APPROVED);
                    AmbulanceRequest ambulanceRequest1 = ambulanceRequestRepository.save(ambulanceRequest.get());
                    ambulanceRequests.setStatus(true);
                    ambulanceRequests.setMessage("Status Changed");
                    ambulanceRequests.setData(ambulanceRequest1);
                }
                ambulanceRequests.setStatus(false);
                ambulanceRequests.setMessage("Invalid Role");

            }
        } catch (Exception e) {
            ambulanceRequests.setStatus(false);
            ambulanceRequests.setMessage("Invalid Role");
            throw new MessException("Invalid User");
        }
        return ambulanceRequests;
    }

    public ResponseEntityDto<AmbulanceRequest> addRequest(int hostel_id, int ambulance_id, int userId) throws MessException{
        ResponseEntityDto<AmbulanceRequest> ambulanceRequests = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        AmbulanceRequest ambulanceRequest = new AmbulanceRequest();
        try{
            Hostel hostel = hostelRepository.findById(hostel_id).get();
            Ambulance ambulance = ambulanceRepository.findById(ambulance_id).get();
            ambulanceRequest.setRequestDate(LocalDateTime.now());
            ambulanceRequest.setUser(user.get().getUserName());
            ambulanceRequest.setHostel(hostel);
            ambulanceRequest.setAmbulance(ambulance);
            ambulanceRequest.setAmbulanceStatus(AmbulanceStatus.SUBMITTED);
            AmbulanceRequest ambulanceRequest1 = ambulanceRequestRepository.save(ambulanceRequest);
            ambulanceRequests.setStatus(true);
            ambulanceRequests.setMessage("Request Added");
            ambulanceRequests.setData(ambulanceRequest1);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created AmbulanceRequest :" + ambulanceRequest1 + "RoleType:" + user.get().getRoleType());
        } catch (Exception ex) {
            ambulanceRequests.setStatus(false);
            ambulanceRequests.setMessage("Some Error");
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created AmbulanceData :" + ambulanceRequest + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
            throw new MessException("Internal Server Error");
        }
        return ambulanceRequests;
    }

    public ResponseEntityDto<AmbulanceRequest> getRequests(int userId) throws MessException{
        ResponseEntityDto<AmbulanceRequest> ambulanceRequests = new ResponseEntityDto<>();
        try{
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                List<AmbulanceRequest> data = ambulanceRequestRepository.findAllRequestsByUser(user.get().getUserName());
                    ambulanceRequests.setListOfData(data);
                    ambulanceRequests.setMessage("Successfully listed All Ambulance Requests");
                    ambulanceRequests.setStatus(true);
            }
        } catch (Exception e) {
            ambulanceRequests.setMessage("Can't List the Ambulances Requests");
            ambulanceRequests.setStatus(false);
            throw new MessException("Can't List the Ambulance Requests");
        }
        return ambulanceRequests;
    }
}
