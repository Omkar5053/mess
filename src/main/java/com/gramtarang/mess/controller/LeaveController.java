package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Feedback;
import com.gramtarang.mess.entity.LeaveData;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.FeedbackService;
import com.gramtarang.mess.service.LeaveService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/leave")
public class LeaveController {
    private final FeedbackService feedbackService;
    private final LeaveService leaveService;
    public LeaveController(FeedbackService feedbackService, LeaveService leaveService) {
        this.feedbackService = feedbackService;
        this.leaveService = leaveService;
    }

    @PostMapping("/addOrEditLeave")
    public ResponseEntityDto<LeaveData> addOrEditLeave(@RequestParam("leaveId")int leaveId, @RequestParam("leaveTypeId")int leaveTypeId,
                                                      @RequestParam("startDate")Date startDate, @RequestParam("endDate") Date endDate,
                                                      @RequestParam("reason")String reason, @RequestParam("leaveStatusId")int leaveStatusId,
                                                      @RequestParam("parentName")String parentName, @RequestParam("parentPhoneNo")String parentPhoneNo,
                                                      @RequestParam("hostelId")int hostelId,
                                                      @RequestParam("userId") String userId,
                                                      @RequestParam("roleType") RoleType roleType,
                                                      HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        ResponseEntityDto<LeaveData> leave = leaveService.addOrEditLeave(Integer.parseInt(userId), roleType, leaveId, leaveTypeId, startDate, endDate,
                reason, parentName, parentPhoneNo, hostelId);
        return leave;
    }

    @PostMapping("/deleteLeave")
    public @ResponseBody
    ResponseEntityDto<LeaveData> deleteLeave(@RequestParam("leaveId") int leaveId,
                                             @RequestParam("userId") String userId,
                                             @RequestParam("roleType") RoleType roleType,
                                             HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        ResponseEntityDto<LeaveData> replyData = leaveService.deleteLeave(Integer.parseInt(userId), roleType, leaveId);
        return replyData;
    }

    @PostMapping("/listOfLeavesByStudent")
    public @ResponseBody
    List<LeaveData> listOfLeavesByStudent(
            @RequestParam("userId") String userId,
            @RequestParam("roleType") RoleType roleType,
            HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        List<LeaveData> leaveDataList = leaveService.listOfLeavesByStudent(Integer.parseInt(userId), roleType);

        return leaveDataList;
    }

    @PostMapping("/listOfLeavesByHostel")
    public @ResponseBody
    List<LeaveData> listOfLeavesByHostel(@RequestParam("hostelId")int hostelId,
                                         @RequestParam("userId") String userId,
                                         @RequestParam("roleType") RoleType roleType,
                                         HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        List<LeaveData> leaveDataListByHostel = leaveService.listOfLeavesByHostel(Integer.parseInt(userId), roleType, hostelId);

        return leaveDataListByHostel;
    }

}
