package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.FoodType;
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
import java.util.logging.Logger;

@Service
public class MessService {
    public static final Logger logger = Logger.getLogger(MessService.class.toString());
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

//    public List<Mess> listOfMessData() {
//        List<Mess> messList = messRepository.findAll();
//        return messList;
//    }


    public ResponseEntityDto<Mess> addOrUpdateMess(int userId, RoleType roleType, int messId, String messName) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        ResponseEntityDto<Mess> messData = new ResponseEntityDto<>();
        int status = 0;
        Mess mess = null;
        try {
            if (messId == 0) {
                mess = messRepository.findByMessName(messName);
                if (mess == null) {
                    mess = new Mess();
                    status = 1;
                    messData.setStatus(true);
                    messData.setMessage("Mess Added Successfully!!");
                } else {
                    messData.setStatus(false);
                    throw new MessException("Already " + messName + " exists");
                }
            } else {
               mess = messRepository.findById(messId).get();
                messData.setStatus(true);
                messData.setMessage("Mess Updated Successfully!!");
               status = 2;
            }
            mess.setMessName(messName);
            mess.setUser(user.get());
            mess = messRepository.save(mess);
            messData.setData(mess);
            if (status == 1) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created MessData :" + mess + "RoleType:" + roleType);
            } else {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated MessData :" + mess + "RoleType:" + roleType);
            }
        } catch (Exception ex) {
            messData.setStatus(false);
            messData.setMessage("Server Error");
            if (status == 1) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created MessData :" + mess + "RoleType:" + roleType);
            } else {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated MessData :" + mess + "RoleType:" + roleType);
            }            throw new MessException(String.valueOf(ex));
        }
        return messData;
    }

    public void delete(int userId, RoleType roleType, Integer messId) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Mess> mess = messRepository.findById(messId);
        try {
            messRepository.deleteById(messId);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted MessData :" + mess + "RoleType:" + roleType);
        } catch (Exception ex) {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted MessData :" + mess + "RoleType:" + roleType + " Exception:" + ex);
        }
    }

    public List<MessUser> listOfStudentsByUserType(int userId, String userType, RoleType roleType) throws MessException {
        logger.info("Entered to listOfStudentsByUserType");
        logger.info("UserId:" +userId);
        logger.info("UserType:" + userType);
        logger.info("RoleType:" +roleType);
        Optional<User> user = userRepository.findById(userId);
        List<MessUser> messUser = new ArrayList<>();
        if ((roleType == RoleType.ADMIN) || (roleType == RoleType.CHIEFWARDEN) || (roleType == RoleType.WARDEN)) {
            logger.info("UserType:" + UserType.valueOf(userType));
            List<MessUser> messUserList =  messUserRepository.findByUserUserType(UserType.valueOf(userType));
            for (MessUser messUser1: messUserList) {
                messUser.add(messUser1);
            }
            logger.info("MessUser:" + messUser);
        } else {
            throw new MessException("Chosen wrong RoleType");
        }

        if (messUser.size() == 0) {
            throw new MessException("There is no data for this usertype: " + userType);
        }
        return messUser;
    }

    public ResponseEntityDto<MessUser> addOrEditStudentDataToMessUser(int messUserId, int userId, RoleType roleType, int messId, int breakfast, int lunch, int dinner, FoodType foodType) throws MessException {
        ResponseEntityDto<MessUser> messUserResponseData = new ResponseEntityDto<>();
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
            messUser.setFoodType(foodType);
            messUser = messUserRepository.save(messUser);
            messUserResponseData.setData(messUser);
            messUserResponseData.setStatus(true);
            messUserResponseData.setMessage("Successfully saved the MessUser data");
            if (messId == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created MessUserData :" + messUser + "RoleType:" + roleType);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, "Updated MessUserData :" + messUser + "RoleType:" + roleType);
        } catch (Exception ex) {
            messUserResponseData.setData(messUser);
            messUserResponseData.setStatus(false);
            messUserResponseData.setMessage("Failed saved the MessUser data");
            if (messId == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created MessUserData :" + messUser + "RoleType:" + roleType + " Exception:" + ex);
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, "Updated MessUserData :" + messUser + "RoleType:" + roleType + " Exception:" + ex);
        }
        return messUserResponseData;
    }

    public ResponseEntityDto<MessUser> deleteStudentMessUserData(int userId, RoleType roleType, int messUserId) throws MessException {
        ResponseEntityDto<MessUser> messUserResponseData = new ResponseEntityDto<>();
        Optional<User> user = userRepository.findById(userId);
        Optional<MessUser> messUser = messUserRepository.findById(messUserId);
        if ((roleType != RoleType.STUDENT) && (roleType != RoleType.MESSINCHARGE)) {
            try {
                messUserRepository.deleteById(messUserId);
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted MessUserData :" + messUser + "RoleType:" + roleType);
                messUserResponseData.setStatus(true);
                messUserResponseData.setMessage("Successfully deleted the MessUser data");
            } catch (Exception ex) {
                messUserResponseData.setStatus(false);
                messUserResponseData.setMessage("Failed to delete the MessUser data");
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted MessUserData :" + messUser + "RoleType:" + roleType + " Exception:" + ex);
            }
        } else {
            messUserResponseData.setStatus(false);
            messUserResponseData.setMessage(roleType + " can't delete the data");
            throw new MessException(roleType + " can't delete the data");
        }
        return messUserResponseData;
    }

    public List<Mess> listOfMessData() {
        List<Mess> messList = messRepository.findAll();
        return messList;
    }

    public Mess add(int userId, RoleType roleType, Mess mess) throws MessException{
        Optional<User> user = userRepository.findById(userId);
        System.out.println(user.get());
        try {
            mess = messRepository.save(mess);
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, "Created HostelData :" + mess + "RoleType:" + roleType);
            return mess;
        } catch (Exception ex) {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, "Created HostelData :" + ex + " roleType " + roleType);
            throw new MessException(String.valueOf(ex));
        }
    }

    public Mess update(int userId, RoleType roleType, Integer messId, String messName) throws MessException{
        Optional<Mess> mess = messRepository.findById(messId);
        Optional<User> user  = userRepository.findById(userId);
        if(mess.isPresent()){
            mess.get().setMessName(messName);
            return messRepository.save(mess.get());
        }
        return null;
    }

    public Mess getMessById(Integer messId) {
        return messRepository.findById(messId).get();
    }

    public List<MessUser> listOfMessUserData() {
        return messUserRepository.findAll();
    }

//    public void delete(int userId, RoleType roleType, Integer messId) throws MessException{
//        Optional<User> user = userRepository.findById(userId);
//        Optional<Mess> mess = messRepository.findById(messId);
//        try {
//            messRepository.deleteById(messId);
//            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "Deleted HostelData :" + mess + "RoleType:" + roleType);
//        } catch (Exception ex) {
//            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "Deleted HostelData :" + mess + "RoleType:" + roleType + " Exception:" + ex);
//        }
//    }
}
