package com.gramtarang.mess.controller;

import com.gramtarang.mess.service.MyExcelHelper;
import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.service.HostelAttendanceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/hostelAttendance")
public class HostelAttendanceController {
    private HostelAttendanceService hostelAttendanceService;
    private MyExcelHelper myExcelHelper;

    public HostelAttendanceController(HostelAttendanceService hostelAttendanceService,MyExcelHelper myExcelHelper) {
        this.hostelAttendanceService = hostelAttendanceService;
        this.myExcelHelper = myExcelHelper;
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

    @PostMapping("/addAttendanceByExcel")
    public @ResponseBody
    ResponseEntityDto<HostelAttendance> addAttendances(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws MessException
    {
        if (myExcelHelper.checkExcelFormat(file)) {
            return hostelAttendanceService.saveFile(file);
        }
        return new ResponseEntityDto<HostelAttendance>("Please Upload Excel FIle Only!!!");
    }


}