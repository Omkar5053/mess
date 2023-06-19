package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.entity.StudentData;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.AttendanceStatus;
import com.gramtarang.mess.repository.HostelAttendanceRepository;
import com.gramtarang.mess.repository.StudentDataRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HostelAttendanceService {
    private HostelAttendanceRepository hostelAttendanceRepository;
    private final UserRepository userRepository;
    private final StudentDataRepository studentDataRepository;

    public HostelAttendanceService(HostelAttendanceRepository hostelAttendanceRepository,
                                   UserRepository userRepository,
                                   StudentDataRepository studentDataRepository) {
        this.hostelAttendanceRepository = hostelAttendanceRepository;
        this.userRepository = userRepository;
        this.studentDataRepository = studentDataRepository;
    }



    public ResponseEntityDto<HostelAttendance> add(int hostel_id, int floorNo, int[] userIds) throws MessException{
        ResponseEntityDto<HostelAttendance> attendances = new ResponseEntityDto<>();
        HostelAttendance returnedData = null;
        HostelAttendance absentsUsers = null;
        List<HostelAttendance> data = new ArrayList<>();
        List<StudentData> studentDataList = studentDataRepository.findAllStudentsByHostelAndFloor(hostel_id, floorNo);
        List<Integer> absentIds = new ArrayList<>();
        for(StudentData d : studentDataList){
            absentIds.add(d.getUser().getUserId());
        }
        try{

            for(Integer id : userIds){
                System.out.println(id);
                absentIds.remove(id);
                returnedData = addingAttendance(id);
                hostelAttendanceRepository.flush();
                if(returnedData != null) {
                    data.add(returnedData);
                }
            }
            for (int i:absentIds) {
                absentsUsers = addingAbsentUsers(i);
                hostelAttendanceRepository.flush();
                if(absentsUsers != null)
                {
                    data.add(absentsUsers);
                }
            }
            attendances.setListOfData(data);
            attendances.setMessage("SUCCESS");
            attendances.setStatus(true);
        } catch (Exception e) {
            attendances.setMessage("Server Error");
            attendances.setStatus(false);
            throw new MessException("Internal Server Error!!");
        }
        return attendances;
    }

    private HostelAttendance addingAbsentUsers(int userId) {
        HostelAttendance hostelAttendance = new HostelAttendance();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
        {
            hostelAttendance.setAttendanceStatus(AttendanceStatus.ABSENT);
            hostelAttendance.setDate(LocalDateTime.now());
            hostelAttendance.setUser(user.get());
            return hostelAttendanceRepository.save(hostelAttendance);
        }
        return null;
    }

    private HostelAttendance addingAttendance(int userId) {
        HostelAttendance hostelAttendance = new HostelAttendance();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
        {
            hostelAttendance.setAttendanceStatus(AttendanceStatus.PRESENT);
            hostelAttendance.setDate(LocalDateTime.now());
            hostelAttendance.setUser(user.get());
            return hostelAttendanceRepository.save(hostelAttendance);
        }
        return null;
    }
}
