package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.HostelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.util.IStructureModel;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/hostel")
public class HostelController {
    public static final Logger logger = Logger.getLogger(String.valueOf(AuthController.class));
    private HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @PostMapping("/getAllHostels")
    public @ResponseBody
    ResponseEntityDto<Hostel> getAllHostels(@RequestParam("userId") String userId,
                                            HttpSession session) throws MessException
    {
        System.out.println(session.getAttribute("USERID"));
        return hostelService.getAll(Integer.parseInt(userId));
    }

    @PostMapping("/addOrUpdateHostel")
    public @ResponseBody
    ResponseEntityDto<Hostel> AddOrUpdateHostel(@RequestParam("hostel_id") Integer hostel_id,
                        @RequestParam("hostelName") String hostelName,
                        @RequestParam("userId") String userId,
                        HttpServletRequest request) throws MessException {
        return hostelService.addOrUpdate(Integer.parseInt(userId), hostel_id, hostelName);
    }

    @PostMapping("/delete")
    public @ResponseBody
    ResponseEntityDto<Hostel> deleteHostel(@RequestParam(value = "hostel_id") Integer hostel_id,
                        @RequestParam("userId") String userId,
                        HttpServletRequest request) throws MessException {

        return hostelService.delete(Integer.parseInt(userId), hostel_id);
    }

    @PostMapping("/getBy")
    public @ResponseBody
    Hostel getHostelById(@RequestParam (value = "hostel_id") Integer hostel_id,
                         HttpServletRequest request) throws MessException{
        return hostelService.getHostelById(hostel_id);
    }

}
