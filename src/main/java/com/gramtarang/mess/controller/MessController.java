package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.UserLoginDto;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.*;
import com.gramtarang.mess.enums.FoodType;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.MessService;
import com.gramtarang.mess.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/mess")
public class MessController {
    private final MessService messService;
    public MessController(MessService messService) {
        this.messService = messService;
    }

//    @PostMapping("/listOfAllMess")
//    public @ResponseBody
//    List<Mess> listOfAllMessData(HttpServletRequest request) {
//        List<Mess> messList = messService.listOfMessData();
//        return messList;
//    }

    @PostMapping("/listOfStudentsByUserType")
    public @ResponseBody
    List<MessUser> listOfStudentsByUserType(@RequestParam("userType") String userType,@RequestParam("userId")String userId,
                                            @RequestParam("roleType")String roleType, HttpServletRequest request) throws MessException {

        List<MessUser> messUserList = messService.listOfStudentsByUserType(Integer.parseInt(userId), userType, RoleType.valueOf(roleType));

        return messUserList;
    }

    @PostMapping("/addOrModifyStudentMessUserData")
    public @ResponseBody
    ResponseEntityDto<MessUser> addOrEditStudentDataToMessUser(@RequestParam("id")int id, @RequestParam("messId")int messId,
                                                              @RequestParam("breakFast")int breakfast,
                                                              @RequestParam("lunch")int lunch, @RequestParam("dinner")int dinner,
                                                              @RequestParam("foodType") FoodType foodType,
                                                              @RequestParam("userId") String userId,
                                                              @RequestParam("roleType") RoleType roleType,
                                                              HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        ResponseEntityDto<MessUser> messUser = messService.addOrEditStudentDataToMessUser(id, Integer.parseInt(userId), roleType, messId, breakfast, lunch, dinner,foodType);
        return messUser;
    }

    @PostMapping("/deleteStudentMessUserData")
    public @ResponseBody
    ResponseEntityDto<MessUser> deleteStudentMessUserData(@RequestParam("messUserId") int messUserId,
                                     @RequestParam("userId") String userId,
                                     @RequestParam("roleType") RoleType roleType,
                                     HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        ResponseEntityDto<MessUser> replyData = messService.deleteStudentMessUserData(Integer.parseInt(userId), roleType, messUserId);
        return replyData;
    }

    @PostMapping("/listOfAllMess")
    public @ResponseBody
    List<Mess> listOfAllMessData(HttpServletRequest request) {
        List<Mess> messList = messService.listOfMessData();
        return messList;
    }

    @PostMapping("/addMess")
    public @ResponseBody
    Mess addHostel(@RequestBody Mess mess,
                     @RequestParam("userId") String userId,
                     @RequestParam("roleType") RoleType roleType,
                     HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        return messService.add(Integer.parseInt(userId),roleType, mess);
    }

    @PostMapping("/updateMess")
    public @ResponseBody
    Mess updateHostel(@RequestParam("messId") Integer messId,
                        @RequestParam("messName") String messName,
                        @RequestParam("userId") String userId,
                        @RequestParam("roleType") RoleType roleType,
                        HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        return messService.update(Integer.parseInt(userId),roleType, messId, messName);
    }

    @PostMapping("/getBy")
    public @ResponseBody
    Mess getHostelById(@RequestParam (value = "messId") Integer messId,
                         HttpServletRequest request) throws MessException{
        return messService.getMessById(messId);
    }

    @PostMapping("/delete")
    public @ResponseBody
    String deleteMess(@RequestParam(value = "messId") Integer messId,
                        @RequestParam("userId") String userId,
                        @RequestParam("roleType") RoleType roleType,
                        HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        messService.delete(Integer.parseInt(userId), roleType, messId);
        return "DELETE SUCCESSFULLY";
    }

    @PostMapping("/addOrUpdateMess")
    public @ResponseBody
    ResponseEntityDto<Mess> addOrUpdateHostel(@RequestParam("messName")String messName,
                     @RequestParam("messId") int messId,
                     @RequestParam("userId") int userId,
                     @RequestParam("roleType") RoleType roleType,
                     HttpServletRequest request) throws MessException {
        return messService.addOrUpdateMess(userId,roleType, messId, messName);
    }

    @PostMapping("/deleteMess")
    public @ResponseBody
    String deleteHostel(@RequestParam(value = "messId") Integer messId,
                        @RequestParam("userId") String userId,
                        @RequestParam("roleType") RoleType roleType,
                        HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        messService.delete(Integer.parseInt(userId), roleType, messId);
        return "DELETE SUCCESSFULLY";
    }


    @PostMapping("/listOfAllMessUsers")
    public @ResponseBody
    List<MessUser> listOfAllMessUsers(HttpServletRequest request) {
        List<MessUser> messList = messService.listOfMessUserData();
        return messList;
    }



}
