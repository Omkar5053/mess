package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.Internship;
import com.gramtarang.mess.entity.Maintenance;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.MaintenanceStatus;
import com.gramtarang.mess.enums.MaintenanceType;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.HostelRepository;
import com.gramtarang.mess.repository.MaintenanceRepository;
import com.gramtarang.mess.repository.UserRepository;
import com.sun.tools.javac.Main;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final HostelRepository hostelRepository;

    private final UserRepository userRepository;
    private final AuditUtil auditLog;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, HostelRepository hostelRepository, UserRepository userRepository, AuditUtil auditLog) {
        this.maintenanceRepository = maintenanceRepository;
        this.hostelRepository = hostelRepository;
        this.userRepository = userRepository;
        this.auditLog = auditLog;
    }

    public List<Maintenance> getList(MaintenanceType maintenanceType) throws MessException {
        return maintenanceRepository.findAllByMaintenanceType(maintenanceType);
    }

    public List<Maintenance> getListOfMaintenance() throws MessException {
        return maintenanceRepository.findAll();
    }

    public Maintenance getListOfMaintenanceByMaintenanceId(int maintenanceId) throws MessException {
         Optional<Maintenance> maintenance = maintenanceRepository.findById(maintenanceId);
         if (maintenance != null) {
             return maintenance.get();
         } else {
             throw new MessException("The Maintenance Data doesn't exist with " + maintenanceId);
         }
    }

    public Maintenance addOrEdit(int userId, RoleType roleType, int maintenanceId, String userName, int hostelId, String description, String maintenanceStatus, String maintenanceType) throws MessException{
        Maintenance maintenance1 = null;
        Optional<User> user = userRepository.findById(userId);
        try {
            if (maintenanceId == 0) {
                maintenance1 = new Maintenance();
                maintenance1.setMaintenanceStatus(MaintenanceStatus.UNASSIGNED);
                if (user != null) {
                    maintenance1.setUser(user.get());
                }

            } else {
                maintenance1 = maintenanceRepository.findById(maintenanceId).get();
            }
            maintenance1.setMaintenanceType(MaintenanceType.valueOf(maintenanceType));
            maintenance1.setDate(new Date());
            maintenance1.setDescription(description);
           // maintenance1.setImage(maintenance.getImage());
            if (roleType != RoleType.ADMIN) {
                Optional<Hostel> hostel = hostelRepository.findById(hostelId);
                if (hostel != null) {
                    maintenance1.setHostel(hostel.get());
                }
            }
            maintenance1 = maintenanceRepository.save(maintenance1);

            if (maintenanceId == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created MaintenanceData :" + maintenance1 + "RoleType:" + roleType);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated MaintenanceData :" + maintenance1 + "RoleType:" + roleType);
        } catch (Exception ex) {
            if (maintenanceId == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created MaintenanceData :" + maintenance1 + "RoleType:" + roleType + " Exception:" + ex);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated MaintenanceData :" + maintenance1 + "RoleType:" + roleType + " Exception:" + ex);
        }
        return maintenance1;
    }

    public Maintenance editMaintenanceDetailsByAdmin(int maintenanceId, String maintenanceStatus, int userId, RoleType roleType) throws MessException {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(maintenanceId);
        if (maintenance != null) {
            if (roleType == RoleType.ADMIN) {
                maintenance.get().setMaintenanceStatus(MaintenanceStatus.valueOf(maintenanceStatus));
                maintenance = Optional.of(maintenanceRepository.save(maintenance.get()));
                maintenanceRepository.flush();
            } else {
                throw new MessException("The " + roleType + " can't make modifications");
            }
            return maintenance.get();
        } else {
            throw new MessException("Maintenance with " + maintenanceId + " doesn't exists");
        }
    }

    public String deleteMaintenance(int maintenanceId, int userId, RoleType roleType) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Maintenance> maintenance = maintenanceRepository.findById(maintenanceId);
        if ((roleType == RoleType.ADMIN)) {
            try {
                maintenanceRepository.deleteById(maintenanceId);
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted MaintenanceData :" + maintenance + "RoleType:" + roleType);
            } catch (Exception ex) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted MaintenanceData :" + maintenance + "RoleType:" + roleType + " Exception:" + ex);
            }
        } else {
            throw new MessException(roleType + " can't delete the data");
        }
        return "Success";
    }

    public Maintenance changeStatus(MaintenanceStatus maintenanceStatus, Integer userId, Integer maintenanceId) throws MessException{
        Optional<Maintenance> maintenance = maintenanceRepository.findById(maintenanceId);
        if(maintenance.isPresent())
        {
            if(maintenance.get().getMaintenanceStatus().equals(MaintenanceStatus.UNASSIGNED)){
                Optional<User> user = userRepository.findById(userId);
                if(user != null){
                    maintenance.get().setUser(user.get());
                    maintenance.get().setMaintenanceStatus(MaintenanceStatus.ASSIGNED);
                }
            } else{
                maintenance.get().setMaintenanceStatus(maintenanceStatus);
            }
            return maintenanceRepository.save(maintenance.get());
        }
        throw new MessException("Request is Invalid");
    }

    public List<Maintenance> getAllList(int userId)throws MessException {
        return maintenanceRepository.findAllByUserUserId(userId);
    }
}
