package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Internship;
import com.gramtarang.mess.entity.LeaveData;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.FeedbackService;
import com.gramtarang.mess.service.InternshipService;
import com.gramtarang.mess.service.LeaveService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/internship")
public class InternshipController {
    private final InternshipService internshipService;
    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @PostMapping("/addOrEditInternship")
    public Internship addOrEditInternship(@RequestParam("internshipId") int internshipId,
                                          @RequestParam("registrationNo")String registrationNo,
                                          @RequestParam("name")String name,
                                          @RequestParam("phoneNo")String phoneNo,
                                          @RequestParam("emailId")String emailId,
                                          @RequestParam("purpose")String purpose,
                                          @RequestParam("noOfDays")int noOfDays,
                                          @RequestParam("hostelId")int hostelId,
                                          @RequestParam("messId")int messId,
                                          @RequestParam("userId") String userId,
                                          @RequestParam("roleType") RoleType roleType,
                                          HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        Internship internshipData = internshipService.addOrEditInternship(Integer.parseInt(userId), roleType, internshipId, registrationNo, name, phoneNo,
                emailId, purpose,noOfDays, hostelId, messId);
        return internshipData;
    }

    @PostMapping("/deleteInternship")
    public @ResponseBody
    String deleteInternship(@RequestParam("internshipId") int internshipId,
                            @RequestParam("userId") String userId,
                            @RequestParam("roleType") RoleType roleType,
                            HttpServletRequest request) throws MessException {

//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        String replyData = internshipService.deleteInternship(Integer.parseInt(userId), roleType, internshipId);
        return replyData;
    }

    @PostMapping("/listOfInternshipStudentsByHostel")
    public @ResponseBody
    List<Internship> listOfInternshipStudentsByHostel(@RequestParam("hostelId")int hostel_id,
                                                      @RequestParam("userId") String userId,
                                                      @RequestParam("roleType") RoleType roleType,
                                                      HttpServletRequest request) throws MessException {

//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        List<Internship> internshipList = internshipService.listOfInternshipStudentsByHostel(Integer.parseInt(userId), roleType, hostel_id);

        return internshipList;
    }

//    @PostMapping("/listOfInternships")
//    public @ResponseBody
//    List<Internship> listOfInternshipStudents(HttpServletRequest request) throws MessException {
//        List<Internship> internshipList = internshipService.listOfInternshipStudents();
//        return internshipList;
//    }

    @PostMapping("/listOfInternships")
    public @ResponseBody
    List<Internship> listOfInternships(HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        List<Internship> internshipList = internshipService.listAll();

        return internshipList;
    }


    @PostMapping("/getById")
    public @ResponseBody
    Internship getInternshipById(@RequestParam(value = "internshipId") Integer internshipId) throws MessException{
        return internshipService.getById(internshipId);
    }

}
