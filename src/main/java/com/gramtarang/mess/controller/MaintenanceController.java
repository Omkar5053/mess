package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Maintenance;
import com.gramtarang.mess.enums.MaintenanceStatus;
import com.gramtarang.mess.enums.MaintenanceType;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.MaintenanceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/maintenance")
public class MaintenanceController {

    private MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/listBy")
    public @ResponseBody
    List<Maintenance> listOfMaintenance(@RequestParam(value = "maintenanceType")MaintenanceType maintenanceType,
                                        HttpServletRequest request) throws MessException
    {
        return maintenanceService.getList(maintenanceType);
    }

    @PostMapping("/addOrEdit")
    public @ResponseBody
    Maintenance addOrUpdate(@RequestBody Maintenance maintenance,
                            HttpServletRequest request) throws MessException
    {
        return maintenanceService.addOrEdit(maintenance);
    }

    @PostMapping("/changeStatus")
    public @ResponseBody
    Maintenance changeStatus(@RequestParam(value = "maintenanceStatus")MaintenanceStatus maintenanceStatus,
                             @RequestParam(value = "userId") Integer userId,
                             @RequestParam(value = "maintenanceId") Integer maintenanceId,
                             HttpServletRequest request) throws MessException
    {
        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        if(roleType.toString() != "STUDENT")
        {
            return maintenanceService.changeStatus(maintenanceStatus, userId, maintenanceId);
        }
        throw new MessException("Access denied");
    }

    public @ResponseBody
    List<Maintenance> getListByStudent(HttpServletRequest request) throws MessException{
        String userId = (String) request.getSession().getAttribute("USERID");
        return maintenanceService.getAllList(Integer.parseInt(userId));
    }
}
