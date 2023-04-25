package com.gramtarang.mess.service;

import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.repository.HostelAttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelAttendanceService {
    private HostelAttendanceRepository hostelAttendanceRepository;

    public HostelAttendanceService(HostelAttendanceRepository hostelAttendanceRepository) {
        this.hostelAttendanceRepository = hostelAttendanceRepository;
    }

    public List<HostelAttendance> getStudentAttendances(Integer user_id) {
        return hostelAttendanceRepository.findHostelAttendancesByUser_UserId(user_id);
    }
}
