package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.StudentData;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.repository.HostelRepository;
import com.gramtarang.mess.repository.StudentDataRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentDataService {

    private HostelRepository hostelRepository;
    private UserRepository userRepository;
    private StudentDataRepository studentDataRepository;
    @Autowired
    private AuditUtil auditLog;

    public StudentDataService(HostelRepository hostelRepository, AuditUtil auditLog, UserRepository userRepository, StudentDataRepository studentDataRepository) {
        this.hostelRepository = hostelRepository;
        this.auditLog = auditLog;
        this.userRepository = userRepository;
        this.studentDataRepository = studentDataRepository;
    }
    public ResponseEntityDto<StudentData> addOrUpdate(int id, int floorNo, int roomNo, int hostel_id, int userId) throws MessException {
        ResponseEntityDto<StudentData> studentData = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        StudentData student = null;
        try {
            List<StudentData> studentDataRoomWise = studentDataRepository.findAllStudentsByHostelAndFloorAndRoom(hostel_id, floorNo, roomNo);
            Hostel hostel = hostelRepository.findById(hostel_id).get();
            if(studentDataRoomWise.size() < hostel.getNoOfStudentPerRoom())
            {
                if (id == 0) {
                    student = new StudentData();
                    studentData.setMessage("Data Added Successfully");
                } else {
                    student = studentDataRepository.findById(id).get();
                    studentData.setMessage("Data Updated Successfully");
                }
                user.get().setHostel(hostel);
                userRepository.save(user.get());
                student.setHostel(hostel);
                student.setUser(user.get());
                student.setFloorNo(floorNo);
                student.setRoomNo(roomNo);
                student = studentDataRepository.save(student);
                studentData.setStatus(true);
                studentData.setData(student);
                if (id == 0)
                    auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created StudentData :" + student + "RoleType:" + user.get().getRoleType());
                else
                    auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated StudentData :" + student + "RoleType:" + user.get().getRoleType());
            } else{
                throw new MessException("Room is already Occupied");
            }
        } catch (Exception ex) {
            studentData.setMessage("User added Already!!");
            studentData.setStatus(false);
            if (id == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created StudentData :" + student + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated StudentData :" + student + "RoleType:" + user.get().getRoleType() + " Exception:" + ex);
        }
        return studentData;
    }

    public ResponseEntityDto<StudentData> getList(int hostel_id, int userId)  throws MessException{
        ResponseEntityDto<StudentData> studentData = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        try{
            List<StudentData> data = studentDataRepository.findAllStudentsByHostel(hostel_id);
            studentData.setListOfData(data);
            studentData.setMessage("SUCCESS");
            studentData.setStatus(true);
        } catch (Exception e) {
            studentData.setMessage("Server Error");
            studentData.setStatus(false);
            throw new MessException("Internal Server Error!!");
        }
        return studentData;
    }


    public ResponseEntityDto<StudentData> getListByFloor(int hostel_id, int floorNo, int userId) throws MessException {
        ResponseEntityDto<StudentData> studentData = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        try{
            List<StudentData> data = studentDataRepository.findAllStudentsByHostelAndFloor(hostel_id, floorNo);
            studentData.setListOfData(data);
            studentData.setMessage("SUCCESS");
            studentData.setStatus(true);
        } catch (Exception e) {
            studentData.setMessage("Server Error");
            studentData.setStatus(false);
            throw new MessException("Internal Server Error!!");
        }
        return studentData;
    }

    public ResponseEntityDto<StudentData> getListByRoom(int hostel_id, int floorNo, int roomNo, int userId) throws MessException{
        ResponseEntityDto<StudentData> studentData = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        try{
            List<StudentData> data = studentDataRepository.findAllStudentsByHostelAndFloorAndRoom(hostel_id, floorNo, roomNo);
            studentData.setListOfData(data);
            studentData.setMessage("SUCCESS");
            studentData.setStatus(true);
        } catch (Exception e) {
            studentData.setMessage("Server Error");
            studentData.setStatus(false);
            throw new MessException("Internal Server Error!!");
        }
        return studentData;
    }
}
