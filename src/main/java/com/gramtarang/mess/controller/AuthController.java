package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.UserDto;
import com.gramtarang.mess.common.UserLoginDto;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.repository.UserRepository;
import com.gramtarang.mess.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final String ERP_SESSION_COOKIE = "ERPSessionId";
    private final UserService userService;
    private final UserRepository userRepository;
    public AuthController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public @ResponseBody
    UserLoginDto login(@RequestParam("loginId") String email, @RequestParam("password") String password,
                       HttpServletRequest request) throws MessException {
        User user = userService.authenticateLogin(email, password);
        UserLoginDto dto = new UserLoginDto();
        if (user.getRoleType() != null) {
            dto.setRoleName(user.getRoleType().toString());
        } else {
            dto.setRoleName(null);
        }
        dto.setUsername(user.getUserName());
        dto.setSessionId(String.valueOf(user.getUserId()));
        request.getSession().setAttribute("USERID", user.getUserId());
        request.getSession().setAttribute("ROLE-NAME", user.getRoleType().toString());
        return dto;
    }

    @PostMapping("/logout")
    public @ResponseBody
    UserLoginDto logout(HttpServletRequest request) throws MessException {
        request.getSession().invalidate();
        request.getSession().removeAttribute("USERID");
        request.getSession().removeAttribute("ROLE-TYPE");
        return new UserLoginDto();
    }

    @PostMapping("/getStudents")
    public @ResponseBody
    List<UserDto> studentsByHostel(@RequestParam(value = "hostel_id") Integer hostel_id,
                                   HttpServletRequest request)
    {
        if(request.getSession().getAttribute("ROLE-TYPE") != "STUDENT")
        {
            return userService.getStudentsByHostel(hostel_id);
        }
        return null;
    }
    @PostMapping("/add")
    public @ResponseBody
    User addUser(@RequestBody User user, HttpServletRequest request)
    {
//        if(request.getSession().getAttribute("ROLE-TYPE") == "ADMIN")
//        {
//            return userService.addUser(user);
//        }
        return userService.addUser(user);
    }

    @PostMapping("/update")
    public @ResponseBody
    User updateUser(@RequestBody User user, HttpServletRequest request) throws MessException {
        if(request.getSession().getAttribute("ROLE-TYPE") == "ADMIN")
        {
            return userService.updateUser(user);
        }
        return null;
    }
    @PostMapping("/deleteFromHostel")
    public @ResponseBody
    String deleteUserfromHostel(@RequestParam(value = "userId") Integer userId,
                                HttpServletRequest request) throws MessException
    {
        if(request.getSession().getAttribute("ROLE-TYPE") != "STUDENT")
        {
            return userService.delete(userId);
        }
        return null;
    }

}
