package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Ambulance;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.enums.*;
import com.gramtarang.mess.service.AmbulanceService;
import com.gramtarang.mess.service.HostelService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/common")
public class CommonController {
    private final AmbulanceService ambulanceService;
    private final HostelService hostelService;

    public CommonController(AmbulanceService ambulanceService, HostelService hostelService) {
        this.ambulanceService = ambulanceService;
        this.hostelService = hostelService;
    }


    @RequestMapping(value = "/roleType", method = RequestMethod.GET)
    public @ResponseBody
    List<RoleType> getAllRoleType() {
        List<RoleType> types = new ArrayList<RoleType>();
        for (RoleType t : RoleType.values()) {
            types.add(t);
        }
        return types;
    }

    @RequestMapping(value = "/userType", method = RequestMethod.GET)
    public @ResponseBody
    List<UserType> getAllUserType() {
        List<UserType> types = new ArrayList<UserType>();
        for (UserType t : UserType.values()) {
            types.add(t);
        }
        return types;
    }

    @RequestMapping(value = "/maintenanceType", method = RequestMethod.GET)
    public @ResponseBody
    List<MaintenanceType> getAllMaintenanceType() {
        List<MaintenanceType> types = new ArrayList<MaintenanceType>();
        for (MaintenanceType t : MaintenanceType.values()) {
            types.add(t);
        }
        return types;
    }

    @RequestMapping(value = "/leaveType", method = RequestMethod.GET)
    public @ResponseBody
    List<LeaveType> getAllLeaveType() {
        List<LeaveType> types = new ArrayList<LeaveType>();
        for (LeaveType t : LeaveType.values()) {
            types.add(t);
        }
        return types;
    }
}
