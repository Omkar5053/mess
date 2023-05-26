package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Ambulance;
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

//    @PostMapping("/addOrEditAmbulance")
//    public @ResponseBody
//    ResponseEntityDto<Ambulance> addOrEditAmbulance(@RequestBody Ambulance ambulance,
//                         @RequestParam("userId") String userId,
//                         HttpServletRequest request) throws MessException
//    {
//        return ambulanceService.addOrEdit(Integer.parseInt(userId), ambulance);
//    }
//
//    @PostMapping("/changeStatus")
//    public @ResponseBody
//    ResponseEntityDto<Ambulance> changeStatus(@RequestParam(value = "ambulance_id") int ambulance_id,
//                           @RequestParam("roleType") RoleType roleType,
//                           HttpServletRequest request) throws MessException
//    {
//        if(roleType.toString() != "STUDENT")
//        {
//            return ambulanceService.changeStatus(ambulance_id);
//        }
//        throw new MessException("Invalid User");
//
//    }

//    @PostMapping("/delete")
//    public @ResponseBody
//    ResponseEntityDto<Ambulance> deleteAmbulance(@RequestParam("ambulanceId")Integer ambulanceId,
//                         @RequestParam("userId") String userId,
//                         @RequestParam("roleType") RoleType roleType,
//                         HttpServletRequest request) throws MessException
//    {
////        String userId = (String) request.getSession().getAttribute("USERID");
////        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
//        ambulanceService.delete(Integer.parseInt(userId), roleType, ambulanceId);
//        return "Success";
//    }
}
