package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Ambulance;
import com.gramtarang.mess.entity.AmbulanceRequest;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.AmbulanceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/ambulance")
public class AmbulanceController {
    @Autowired
    private final AmbulanceService ambulanceService;

    public AmbulanceController(AmbulanceService ambulanceService) {
        this.ambulanceService = ambulanceService;
    }

    @PostMapping("/listAllAmbulances")
    public @ResponseBody
    ResponseEntityDto<Ambulance> listAll(@RequestParam("userId") String userId,HttpServletRequest request) throws MessException
    {
            return ambulanceService.getAll(Integer.parseInt(userId));
    }
    @PostMapping("/getAllRequestsByUser")
    public @ResponseBody
    ResponseEntityDto<Ambulance> getAmbulanceListByUser(@RequestParam(value = "userId") String userId, HttpServletRequest request) throws MessException
    {
        return ambulanceService.getListByUser(Integer.parseInt(userId));
    }

    @PostMapping("/addOrEditAmbulance")
    public @ResponseBody
    ResponseEntityDto<Ambulance> addOrEditAmbulance(
                         @RequestParam("ambulance_id") Integer ambulance_id,
                         @RequestParam("ambulanceName") String ambulanceName,
                         @RequestParam("licensePlate") String licensePlate,
                         @RequestParam("userId") String userId,
                         HttpServletRequest request) throws MessException
    {
        return ambulanceService.addOrEdit(ambulance_id, ambulanceName, licensePlate, Integer.parseInt(userId));
    }

    @PostMapping("/changeStatus")
    public @ResponseBody
    ResponseEntityDto<AmbulanceRequest> changeStatus(@RequestParam(value = "requestId") int requestId,
                                                     @RequestParam("userId") String userId,
                                                     HttpServletRequest request) throws MessException
    {
            return ambulanceService.changeStatus(requestId, Integer.parseInt(userId));
    }

    @PostMapping("/addAmbulanceRequest")
    public @ResponseBody
    ResponseEntityDto<AmbulanceRequest> addAmbulanceRequest(
            @RequestBody AmbulanceRequest ambulanceRequest,
            @RequestParam("userId") String userId,
            HttpServletRequest request) throws MessException
    {
        return ambulanceService.addRequest(ambulanceRequest, Integer.parseInt(userId));
    }

}
