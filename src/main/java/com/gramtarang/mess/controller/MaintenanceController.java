package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Maintenance;
import com.gramtarang.mess.enums.MaintenanceStatus;
import com.gramtarang.mess.enums.MaintenanceType;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.InternshipService;
import com.gramtarang.mess.service.MaintenanceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/maintenance")
public class MaintenanceController {

    private MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/listOfMaintenanceData")
    public @ResponseBody
    List<Maintenance> listOfMaintenanceData(HttpServletRequest request) throws MessException
    {
        return maintenanceService.getListOfMaintenance();
    }

    @PostMapping("/listOfMaintenanceDataById")
    public @ResponseBody
    Maintenance listOfMaintenanceDataById(@RequestParam(value = "maintenanceId") int maintenanceId, HttpServletRequest request) throws MessException
    {
        return maintenanceService.getListOfMaintenanceByMaintenanceId(maintenanceId);
    }


    @PostMapping("/listOfMaintenanceDatByMaintenanceType")
    public @ResponseBody
    List<Maintenance> listOfMaintenance(@RequestParam(value = "maintenanceType")MaintenanceType maintenanceType,
                                        HttpServletRequest request) throws MessException
    {
        return maintenanceService.getList(maintenanceType);
    }

    @PostMapping("/addOrEditMaintenanceDetails")
    public @ResponseBody
    ResponseEntityDto<Maintenance> addOrUpdate(@RequestParam("maintenanceId") int maintenanceId,
                                              @RequestParam("userName")String userName,
                                              @RequestParam("hostelName") int hostelId,
                                              @RequestParam("description") String description,
                                              @RequestParam("maintenanceStatus") String maintenanceStatus,
                                              @RequestParam("maintenanceType") String maintenanceType,
                                              @RequestParam("userId") String userId,
                                              @RequestParam("roleType") RoleType roleType,
                                              HttpServletRequest request) throws MessException
    {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        return maintenanceService.addOrEdit(Integer.parseInt(userId), roleType, maintenanceId,userName, hostelId, description, maintenanceStatus, maintenanceType);
    }

    @PostMapping("/editMaintenanceDetailsByAdmin")
    public @ResponseBody
    ResponseEntityDto<Maintenance> editMaintenanceDetailsByAdmin(@RequestParam("maintenanceId") int maintenanceId,
                            @RequestParam("maintenanceStatus") String maintenanceStatus,
                            @RequestParam("userId") String userId,
                            @RequestParam("roleType") RoleType roleType,
                            HttpServletRequest request) throws MessException
    {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        return maintenanceService.editMaintenanceDetailsByAdmin(maintenanceId, maintenanceStatus,Integer.parseInt(userId), roleType);
    }

    @PostMapping("/changeStatus")
    public @ResponseBody
    ResponseEntityDto<Maintenance> changeStatus(@RequestParam(value = "maintenanceStatus")MaintenanceStatus maintenanceStatus,
                             @RequestParam(value = "userId") Integer userId,
                             @RequestParam(value = "maintenanceId") Integer maintenanceId,
                             @RequestParam("roleType") RoleType roleType,
                             HttpServletRequest request) throws MessException
    {
        if(roleType.toString() != "STUDENT")
        {
            return maintenanceService.changeStatus(maintenanceStatus, userId, maintenanceId);
        }
        throw new MessException("Access denied");
    }

    @PostMapping("/getListOfMaintenanceDataByStudent")
    public @ResponseBody
    List<Maintenance> getListByStudent(@RequestParam("userId") String userId,
                                         HttpServletRequest request) throws MessException{
//        String userId = (String) request.getSession().getAttribute("USERID");
        return maintenanceService.getAllList(Integer.parseInt(userId));
    }

    @PostMapping("/deleteMaintenance")
    public @ResponseBody
    ResponseEntityDto<Maintenance> deleteMaintenance(@RequestParam("maintenanceId")int maintenanceId, @RequestParam("userId") int userId, @RequestParam("roleType") RoleType roleType) throws MessException {
        return  maintenanceService.deleteMaintenance(maintenanceId, userId, roleType);
    }
}
