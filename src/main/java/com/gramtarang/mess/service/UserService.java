package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.UserDto;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.repository.HostelRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        userDto.setRole(user.getRole().toString());
        userDto.setFullName(user.getFirstName()+" "+user.getLastName());
        userDto.setHostel(user.getHostel().getHostelName());
        return userDto;
    }
}