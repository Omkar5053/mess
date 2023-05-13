package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
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
    public final AuditUtil auditLog;


    public MessService(MessRepository messRepository, UserRepository userRepository, MessUserRepository messUserRepository, AuditUtil auditLog) {
        this.messRepository = messRepository;
        this.userRepository = userRepository;
        this.messUserRepository = messUserRepository;
        this.auditLog = auditLog;
    }

    public List<Mess> listOfMessData() {
        List<Mess> messList = messRepository.findAll();
        return messList;
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

    public MessUser addOrEditStudentDataToMessUser(int messUserId, int userId, RoleType roleType,int messId,int breakfast,int lunch,int dinner) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Mess> mess = messRepository.findById(messId);
        MessUser messUser = null;
        try {
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
            if (messId == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created MessUserData :" + messUser + "RoleType:" + roleType);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated MessUserData :" + messUser + "RoleType:" + roleType);
        } catch (Exception ex) {
            if (messId == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created MessUserData :" + messUser + "RoleType:" + roleType + " Exception:" + ex);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated MessUserData :" + messUser + "RoleType:" + roleType + " Exception:" + ex);
        }
        return messUser;
    }

    public String deleteStudentMessUserData(int userId, RoleType roleType, int messUserId) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Optional<MessUser> messUser = messUserRepository.findById(messUserId);
        if ((roleType != RoleType.STUDENT) && (roleType != RoleType.MESSINCHARGE)) {
            try {
                messUserRepository.deleteById(messUserId);
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted MessUserData :" + messUser + "RoleType:" + roleType);

            } catch (Exception ex) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted MessUserData :" + messUser + "RoleType:" + roleType + " Exception:" + ex);
            }
        } else {
            throw new MessException(roleType + " can't delete the data");
        }
        return "Success";
    }
}
