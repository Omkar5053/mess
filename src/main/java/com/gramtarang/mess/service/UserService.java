package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.UserDto;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private AuditUtil auditLog;

    public UserService(UserRepository userRepository, AuditUtil auditLog) {

        this.userRepository = userRepository;
        this.auditLog = auditLog;
    }

      public User authenticateLogin(String email, String password) throws MessException {
          User user = this.userRepository.findUserByEmailAddressAndPassword(email, password);
          if (user != null) {
             return user;
          }
          else {
              throw new MessException("Invalid User or Password");
          }
      }


    public List<UserDto> getStudentsByHostel(Integer hostel_id) {
        List<User> users = userRepository.findUsersByHostel_HostelId(hostel_id);
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users)
        {
            userDtos.add(convertToUserDto(user));
        }
        return userDtos;

    }
    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserStatus(user.getActive().toString());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRoleType().toString());
        userDto.setFullName(user.getFirstName()+" "+user.getLastName());
        userDto.setHostel(user.getHostel().getHostelName());
        return userDto;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) throws MessException {
        Optional<User> user1 = userRepository.findById(user.getUserId());
        if(user1.isPresent())
        {
            try {
                user1.get().setUserName(user.getUserName());
                user1.get().setActive(user.getActive());
                user1.get().setEmail(user.getEmail());
                user1.get().setFirstName(user.getFirstName());
                user1.get().setLastName(user.getLastName());
                user1.get().setPhoneNo(user.getPhoneNo());
                user1.get().setPassword(user.getPassword());
                user1.get().setHostel(user.getHostel());
                user1.get().setRoleType(user.getRoleType());

                user1 = Optional.of(userRepository.save(user1.get()));
                auditLog.createAudit(user1.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, String.valueOf(user1.get()));
                return user1.get();
            } catch (Exception ex) {
                auditLog.createAudit(user1.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, String.valueOf(ex));
            }
        } else {
            throw new MessException("User Not found");
        }

        return null;
    }

    public String delete(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
        {
            try {
                user.get().setHostel(null);
                userRepository.save(user.get());
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, String.valueOf(user.get()));
                return "DELETED SUCCESSFULLY";
            } catch(Exception ex) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, String.valueOf(ex));
            }
        }
        auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, String.valueOf("User doesn't exist"));
        return "User Doesn't Exists";
    }
}