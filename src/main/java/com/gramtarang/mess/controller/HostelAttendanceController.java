package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
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

    @PostMapping("/addAttendance")
    public @ResponseBody
    ResponseEntityDto<HostelAttendance> addStudentAttendances(
            @RequestParam("hostel_id") int hostel_id,
            @RequestParam("floorNo") int floorNo,
            @RequestParam("studentAttendances") String userIds,
                                            HttpServletRequest request) throws MessException
    {
            String[] ids = userIds.split(",");
            int[] arr = new int[ids.length];
            for (int i = 0; i < ids.length; i++) {
                arr[i] = Integer.valueOf(ids[i]);
                System.out.println(arr[i]);
            }
            return hostelAttendanceService.add(hostel_id, floorNo, arr);
    }
}