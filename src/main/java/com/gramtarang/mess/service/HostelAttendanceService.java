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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HostelAttendanceService {
    private HostelAttendanceRepository hostelAttendanceRepository;
    private final UserRepository userRepository;
    private final StudentDataRepository studentDataRepository;

    private  MyExcelHelper myExcelHelper;



    public HostelAttendanceService(HostelAttendanceRepository hostelAttendanceRepository,
                                   UserRepository userRepository,
                                   StudentDataRepository studentDataRepository,
                                   MyExcelHelper myExcelHelper
                                 ) {
        this.hostelAttendanceRepository = hostelAttendanceRepository;
        this.userRepository = userRepository;
        this.studentDataRepository = studentDataRepository;
        this.myExcelHelper = myExcelHelper;

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
            List<HostelAttendance> todayAttendances = hostelAttendanceRepository.findHostelAttendancesByDate(LocalDate.now());
            if(todayAttendances.size() == 0){
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
            } else{
                attendances.setMessage("Today Attendance Already Submitted!");
                attendances.setStatus(false);
            }
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
            hostelAttendance.setDate(LocalDate.now());
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
            hostelAttendance.setDate(LocalDate.now());
            hostelAttendance.setUser(user.get());
            return hostelAttendanceRepository.save(hostelAttendance);
        }
        return null;
    }

    public ResponseEntityDto<HostelAttendance> saveFile(MultipartFile file) throws MessException{
        ResponseEntityDto<HostelAttendance> response = new ResponseEntityDto<>();
        try {
            List<HostelAttendance> attendances = myExcelHelper.convertExcelToListOfProduct(file.getInputStream());
            attendances = this.hostelAttendanceRepository.saveAll(attendances);
            response.setMessage("Attendance Uploaded Successfully!!!");
            response.setListOfData(attendances);
            response.setStatus(true);
        } catch (IOException e) {
            response.setMessage("Something is Wrong!!!");
            response.setStatus(false);
            throw new MessException("Error in Uploading Attendances");
        }
        return response;
    }
}
