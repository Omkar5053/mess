package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Hostel;
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
import org.springframework.stereotype.Service;

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

    public Maintenance addOrEdit(int userId, RoleType roleType, Maintenance maintenance) throws MessException{
        Maintenance maintenance1 = null;
        Optional<User> user = userRepository.findById(maintenance.getUser().getUserId());
        try {
            if (maintenance.getMaintenanceId() == 0) {
                maintenance1 = new Maintenance();
                maintenance1.setMaintenanceStatus(MaintenanceStatus.UNASSIGNED);

            } else {
                maintenance1 = maintenanceRepository.findById(maintenance.getMaintenanceId()).get();
            }
            maintenance1.setMaintenanceType(maintenance.getMaintenanceType());
            maintenance1.setDate(maintenance.getDate());
            maintenance1.setDescription(maintenance.getDescription());
            maintenance1.setImage(maintenance.getImage());
            Optional<Hostel> hostel = hostelRepository.findById(maintenance.getHostel().getHostel_id());
            if (hostel != null) {
                maintenance1.setHostel(hostel.get());
            }

            if (user != null) {
                maintenance1.setUser(user.get());
            }
            maintenance1 = maintenanceRepository.save(maintenance1);

            if (maintenance.getMaintenanceId() == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created MaintenanceData :" + maintenance1 + "RoleType:" + roleType);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated MaintenanceData :" + maintenance1 + "RoleType:" + roleType);
        } catch (Exception ex) {
            if (maintenance.getMaintenanceId() == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created MaintenanceData :" + maintenance1 + "RoleType:" + roleType + " Exception:" + ex);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated MaintenanceData :" + maintenance1 + "RoleType:" + roleType + " Exception:" + ex);
        }
        return maintenance1;
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
