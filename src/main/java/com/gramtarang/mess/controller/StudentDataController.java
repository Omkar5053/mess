package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.StudentData;
import com.gramtarang.mess.service.StudentDataService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/student")
public class StudentDataController {

    private final StudentDataService studentDataService;

    public StudentDataController(StudentDataService studentDataService) {
        this.studentDataService = studentDataService;
    }

    @PostMapping("/addOrUpdateData")
    public @ResponseBody
    ResponseEntityDto<StudentData> AddOrUpdateData(@RequestParam("id") int id,
                                                     @RequestParam("floorNo") int floorNo,
                                                     @RequestParam("roomNo") int roomNo,
                                                     @RequestParam("hostel_id") int hostel_id,
                                                     @RequestParam("userId") int userId,
                                                     HttpServletRequest request) throws MessException {
        return studentDataService.addOrUpdate(id, floorNo, roomNo, hostel_id, userId);
    }

    @PostMapping("/listStudentsByHostel")
    public @ResponseBody
    ResponseEntityDto<StudentData> listByHostel(
                                                   @RequestParam("hostel_id") int hostel_id,
                                                   @RequestParam("userId") String userId,
                                                   HttpServletRequest request) throws MessException {
        return studentDataService.getList(hostel_id, Integer.parseInt(userId));
    }

    @PostMapping("/listStudentsByFloor")
    public @ResponseBody
    ResponseEntityDto<StudentData> listByFloor(
            @RequestParam("hostel_id") int hostel_id,
            @RequestParam("floorNo") int floorNo,
            @RequestParam("userId") String userId,
            HttpServletRequest request) throws MessException {
        return studentDataService.getListByFloor(hostel_id, floorNo, Integer.parseInt(userId));
    }

    @PostMapping("/listStudentsByRoomNo")
    public @ResponseBody
    ResponseEntityDto<StudentData> listByRoomNo(
            @RequestParam("hostel_id") int hostel_id,
            @RequestParam("floorNo") int floorNo,
            @RequestParam("roomNo") int roomNo,
            @RequestParam("userId") String userId,
            HttpServletRequest request) throws MessException {
        return studentDataService.getListByRoom(hostel_id, floorNo, roomNo, Integer.parseInt(userId));
    }


}
