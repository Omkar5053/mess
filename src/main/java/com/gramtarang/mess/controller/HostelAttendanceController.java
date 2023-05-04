package com.gramtarang.mess.controller;

import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.HostelAttendanceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/hostelAttendance")
public class HostelAttendanceController {
    private HostelAttendanceService hostelAttendanceService;

    public HostelAttendanceController(HostelAttendanceService hostelAttendanceService) {
        this.hostelAttendanceService = hostelAttendanceService;
    }

    @PostMapping("/getAttendance")
    public @ResponseBody
    List<HostelAttendance> getStudentAttendances(@RequestParam(value = "user_id") Integer user_id,
                                                 @RequestParam("roleType") RoleType roleType,
                                                 HttpServletRequest request)
    {
        if(roleType.toString() != "STUDENT")
        {
            return hostelAttendanceService.getStudentAttendances(user_id);
        }
        return null;

    }
}