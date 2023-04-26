package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Ambulance;
import com.gramtarang.mess.enums.AmbulanceStatus;
import com.gramtarang.mess.repository.AmbulanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;

    public AmbulanceService(AmbulanceRepository ambulanceRepository) {
        this.ambulanceRepository = ambulanceRepository;
    }

    public List<Ambulance> getAll() throws MessException {
        return ambulanceRepository.findAll();
    }

    public List<Ambulance> getListByUser(int userId) throws MessException{
        return ambulanceRepository.findAllByUserUserId(userId);
    }

    public Ambulance add(Ambulance ambulance) throws MessException{
        ambulance.setAmbulanceStatus(AmbulanceStatus.SUBMITTED);
        return ambulanceRepository.save(ambulance);
    }

    public Ambulance changeStatus(int ambulance_id) throws MessException{
        Optional<Ambulance> ambulance = ambulanceRepository.findById(ambulance_id);
        if(ambulance.isPresent()){
            ambulance.get().setAmbulanceStatus(AmbulanceStatus.APPROVED);
            return ambulanceRepository.save(ambulance.get());
        }
        throw new MessException("Error");
    }
}
