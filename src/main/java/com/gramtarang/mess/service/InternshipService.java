package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.Internship;
import com.gramtarang.mess.entity.LeaveData;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.LeaveStatus;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {
    @Autowired
    public final UserRepository userRepository;
    @Autowired
    public final InternshipRepository internshipRepository;
    @Autowired
    public final HostelRepository hostelRepository;

    public InternshipService(UserRepository userRepository, InternshipRepository internshipRepository, HostelRepository hostelRepository) {
        this.userRepository = userRepository;
        this.internshipRepository = internshipRepository;
        this.hostelRepository = hostelRepository;
    }

    public Internship addOrEditInternship(int userId, RoleType roleType, int internshipId, String registrationNo, String name,
                                         String phoneNo, String emailId, String campus, String purpose, int noOfDays, int hostelId, int messId) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Internship internship = null;
        if ((roleType == RoleType.WARDEN) || (roleType == RoleType.CHIEFWARDEN) || (roleType == RoleType.ADMIN)){
            if (internshipId == 0) {
                internship = new Internship();
            } else {
                internship = internshipRepository.findById(internshipId).get();
            }
            internship.setRegistrationNumber(registrationNo);
            internship.setName(name);
            internship.setPhoneNo(phoneNo);
            internship.setEmailId(emailId);
            internship.setCampus(campus);
            internship.setPurpose(purpose);
            internship.setNoOfDays(noOfDays);
            Optional<Hostel> hostel = hostelRepository.findById(hostelId);
            if(hostel != null) {
                internship.setHostel(hostel.get());
            }
            internship = internshipRepository.save(internship);
            internshipRepository.flush();

        }

        return internship;
    }

    public String deleteInternship(int userId, RoleType roleType, int internshipId) throws MessException {
        if ((roleType == RoleType.ADMIN) || (roleType == RoleType.WARDEN)) {
            internshipRepository.deleteById(internshipId);
            return "Success";
        } else {
            throw new MessException(roleType + " can't delete the data");
        }

    }

    public List<Internship> listOfInternshipStudentsByHostel(int userId, RoleType roleType, int hostelId) throws MessException {
        List<Internship> internshipList = new ArrayList<>();
        if ((roleType != RoleType.STUDENT)) {
            Optional<Hostel> hostel = hostelRepository.findById(hostelId);
            internshipList = internshipRepository.findByHostel(hostel.get());
        }
        return internshipList;
    }


}
