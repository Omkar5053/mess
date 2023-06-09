package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.UserDto;
import com.gramtarang.mess.common.UserLoginDto;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.UserRepository;
import com.gramtarang.mess.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "/auth")
public class AuthController {
    public static final Logger logger = Logger.getLogger(String.valueOf(AuthController.class));
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
                       HttpServletRequest request, HttpSession session) throws MessException {
        User user = userService.authenticateLogin(email, password);
        UserLoginDto dto = new UserLoginDto();
        if (user.getRoleType() != null) {
            dto.setRoleName(user.getRoleType().toString());
        } else {
            dto.setRoleName(null);
        }
        dto.setUsername(user.getUserName());
        dto.setSessionId(String.valueOf(user.getUserId()));
        session.setAttribute("USERID", user.getUserId());
        session.setAttribute("ROLE-TYPE", user.getRoleType());
        logger.info("UserId:" + request.getSession().getAttribute("USERID"));
        logger.info("ROLE-NAME" + request.getSession().getAttribute("ROLE-TYPE"));
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

    @PostMapping("/getStudentsByHostel")
    public @ResponseBody
    List<UserDto> studentsByHostel(@RequestParam(value = "hostel_id") Integer hostel_id,
                                   @RequestParam(value = "roleType") RoleType roleType,
                                   HttpServletRequest request) throws MessException
    {
        if(roleType.toString() != "STUDENT")
        {
           return userService.getStudentsByHostel(hostel_id);
        }
        return null;
    }

    @PostMapping("/getStudents")
    public @ResponseBody
    List<User> students(
                                   @RequestParam(value = "userId") String userId,
                                   HttpServletRequest request) throws MessException
    {

            return userService.getStudents(Integer.parseInt(userId));

    }

    @PostMapping("/getUsers")
    public @ResponseBody
    List<User> userList() throws MessException
    {

        return userService.getList();

    }

    @PostMapping("/add")
    public @ResponseBody
    User addUser(@RequestBody User user,
                 @RequestParam("roleType") RoleType roleType,
                 HttpServletRequest request)
    {
        if(roleType.toString() == "ADMIN")
        {
            return userService.addUser(user);
        }
        return null;
    }

    @PostMapping("/update")
    public @ResponseBody
    User updateUser(@RequestBody User user,
                    @RequestParam("roleType") RoleType roleType,
                    HttpServletRequest request) throws MessException {
        if(roleType.toString() == "ADMIN")
        {
            return userService.updateUser(user);
        }
        return null;
    }
    @PostMapping("/deleteFromHostel")
    public @ResponseBody
    String deleteUserfromHostel(@RequestParam(value = "userId") Integer userId,
                                @RequestParam("roleType") RoleType roleType,
                                HttpServletRequest request) throws MessException {
        if(roleType.toString() != "STUDENT")
        {
            return userService.delete(userId);
        }
        return null;
    }

    @PostMapping("/getUser")
    public @ResponseBody
    User getUserById(
            @RequestParam(value = "userId") String userId,
            HttpServletRequest request) throws MessException
    {

        return userService.get(Integer.parseInt(userId));

    }


}
