package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.Maintenance;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.MaintenanceStatus;
import com.gramtarang.mess.enums.MaintenanceType;
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

    public MaintenanceService(MaintenanceRepository maintenanceRepository, HostelRepository hostelRepository, UserRepository userRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.hostelRepository = hostelRepository;
        this.userRepository = userRepository;
    }

    public List<Maintenance> getList(MaintenanceType maintenanceType) throws MessException {
        return maintenanceRepository.findAllByMaintenanceType(maintenanceType);
    }

    public Maintenance addOrEdit(Maintenance maintenance) throws MessException{
        Maintenance maintenance1 = null;
        if(maintenance.getMaintenanceId() == 0)
        {
            maintenance1 = new Maintenance();
            maintenance1.setMaintenanceStatus(MaintenanceStatus.UNASSIGNED);

        } else{
            maintenance1 = maintenanceRepository.findById(maintenance.getMaintenanceId()).get();
        }
        maintenance1.setMaintenanceType(maintenance.getMaintenanceType());
        maintenance1.setDate(maintenance.getDate());
        maintenance1.setDescription(maintenance.getDescription());
        maintenance1.setImage(maintenance.getImage());
        Optional<Hostel> hostel = hostelRepository.findById(maintenance.getHostel().getHostel_id());
        if(hostel != null) {
            maintenance1.setHostel(hostel.get());
        }
        Optional<User> user = userRepository.findById(maintenance.getUser().getUserId());
        if(user != null) {
            maintenance1.setUser(user.get());
        }
        return maintenanceRepository.save(maintenance1);
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
