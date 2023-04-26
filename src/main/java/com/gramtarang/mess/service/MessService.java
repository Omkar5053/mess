package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.RegistrationStatus;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.enums.UserType;
import com.gramtarang.mess.repository.MessRepository;
import com.gramtarang.mess.repository.MessUserRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessService {
    @Autowired
    public final MessRepository messRepository;
    @Autowired
    public final UserRepository userRepository;
    @Autowired
    public final MessUserRepository messUserRepository;


    public MessService(MessRepository messRepository, UserRepository userRepository, MessUserRepository messUserRepository) {
        this.messRepository = messRepository;
        this.userRepository = userRepository;
        this.messUserRepository = messUserRepository;
    }

    public List<MessUser> listOfStudentsByUserType(int userId, String userType, RoleType roleType) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        List<MessUser> messUser = new ArrayList<>();
        if ((roleType == RoleType.ADMIN) || (roleType == RoleType.CHIEFWARDEN) || (roleType == RoleType.WARDEN)) {
            messUser = messUserRepository.findByUserUserType(UserType.valueOf(userType));
        } else {
            throw new MessException("Chosen wrong RoleType");
        }

        if (messUser.size() == 0) {
            throw new MessException("There is no data for this usertype: " + userType);
        }
        return messUser;
    }

    public MessUser addOrEditStudentDataToMessUser(int messUserId, int userId, RoleType roleType,int messId,int breakfast,int lunch,int dinner) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Mess> mess = messRepository.findById(messId);
        MessUser messUser = null;
        if (messUserId == 0) {
            messUser = new MessUser();
        } else {
            messUser = messUserRepository.findById(messUserId).get();
        }
        messUser.setUser(user.get());
        messUser.setBreakFast(RegistrationStatus.valueOf(breakfast));
        messUser.setLunch(RegistrationStatus.valueOf(lunch));
        messUser.setDinner(RegistrationStatus.valueOf(dinner));
        messUser.setMess(mess.get());
        messUser.setHostel(null);
        messUser.setFoodType(null);
        messUser = messUserRepository.save(messUser);
        messUserRepository.flush();

        return messUser;
    }

    public String deleteStudentMessUserData(int userId, RoleType roleType, int messUserId) throws MessException {
        if ((roleType != RoleType.STUDENT) && (roleType != RoleType.MESSINCHARGE)) {
            messUserRepository.deleteById(messUserId);
            return "Success";
        } else {
            throw new MessException(roleType + " can't delete the data");
        }

    }
}
