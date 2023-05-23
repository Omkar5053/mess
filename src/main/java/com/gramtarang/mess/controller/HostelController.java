package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
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
    List<Hostel> getAllHostels(HttpServletRequest request)
    {
        String sessionId = request.getSession().getId();
        logger.info("SessionId:" + sessionId);
        HttpSession httpSession = request.getSession(true);
        logger.info("HttpSession:" + httpSession);
        if (httpSession != null) {
            logger.info("UserId:" + httpSession.getAttribute("USERID"));
        }
//        Object obj = session.getAttribute("USERID");
//       for (String element: userId) {
//           logger.info("EmailId:" + element);
//       }
//        RoleType roleType = (RoleType) session.getAttribute("ROLE-TYPE");
//        logger.info("EmailId:" + obj);
//        logger.info("RoleType:" + roleType);
        return hostelService.getAll();
    }

    @PostMapping("/addHostel")
    public @ResponseBody
    Hostel addHostel(@RequestParam("hostelName")String hostelName,
                     @RequestParam("hostelId") int hostelId,
                     @RequestParam("userId") String userId,
                     @RequestParam("roleType") RoleType roleType,
                     HttpServletRequest request) throws MessException {


//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        return hostelService.add(Integer.parseInt(userId),roleType, hostelId, hostelName);
    }


    @PostMapping("/updateHostel")
    public @ResponseBody
    Hostel updateHostel(@RequestParam("hostel_id") Integer hostel_id,
                     @RequestParam("hostelName") String hostelName,
                     @RequestParam("userId") String userId,
                     @RequestParam("roleType") RoleType roleType,
                     HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        return hostelService.update(Integer.parseInt(userId),roleType, hostel_id, hostelName);
    }
    @PostMapping("/delete")
    public @ResponseBody
    String deleteHostel(@RequestParam(value = "hostel_id") Integer hostel_id,
                        @RequestParam("userId") String userId,
                        @RequestParam("roleType") RoleType roleType,
                        HttpServletRequest request) throws MessException {

//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        String msg = hostelService.delete(Integer.parseInt(userId), roleType, hostel_id);
        return msg;
    }

    @PostMapping("/getBy")
    public @ResponseBody
    Hostel getHostelById(@RequestParam (value = "hostel_id") Integer hostel_id,
                         HttpServletRequest request) throws MessException{
        return hostelService.getHostelById(hostel_id);
    }

}
