package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.UserLoginDto;
import com.gramtarang.mess.entity.Feedback;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.MessService;
import com.gramtarang.mess.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/mess")
public class MessController {
    private final MessService messService;
    public MessController(MessService messService) {
        this.messService = messService;
    }

    @PostMapping("/listOfStudentsByUserType")
    public @ResponseBody
    List<MessUser> listOfStudentsByUserType(@RequestParam("userType") String userType,
                                            HttpServletRequest request) throws MessException {
        String userId = (String) request.getSession().getAttribute("USERID");
        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        List<MessUser> messUserList = messService.listOfStudentsByUserType(Integer.parseInt(userId), userType, roleType);

        return messUserList;
    }

    @PostMapping("/addOrModifyStudentMessUserData")
    public @ResponseBody
    MessUser addOrEditStudentDataToMessUser(@RequestParam("id")int id, @RequestParam("messId")int messId,@RequestParam("breakFast")int breakfast,
                            @RequestParam("lunch")int lunch, @RequestParam("dinner")int dinner, HttpServletRequest request) throws MessException{
        String userId = (String) request.getSession().getAttribute("USERID");
        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        MessUser messUser = messService.addOrEditStudentDataToMessUser(id, Integer.parseInt(userId), roleType, messId, breakfast, lunch, dinner);
        return messUser;
    }

    @PostMapping("/deleteStudentMessUserData")
    public @ResponseBody
    String deleteStudentMessUserData(@RequestParam("messUserId") int messUserId, HttpServletRequest request) throws MessException {
        String userId = (String) request.getSession().getAttribute("USERID");
        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        String replyData = messService.deleteStudentMessUserData(Integer.parseInt(userId), roleType, messUserId);
        return replyData;
    }

}
