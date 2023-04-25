package com.gramtarang.mess.service;

import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.repository.HostelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelService {
    private HostelRepository hostelRepository;

    public HostelService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    public List<Hostel> getAll() {
        return hostelRepository.findAll();
    }

    public Hostel add(Hostel hostel) {
        return hostelRepository.save(hostel);
    }

    public void delete(Integer hostel_id) {
        hostelRepository.deleteById(hostel_id);
    }
}
