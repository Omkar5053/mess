package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Feedback;
import com.gramtarang.mess.entity.Internship;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.repository.FeedbackRepository;
import com.gramtarang.mess.repository.MessRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    public final FeedbackRepository feedbackRepository;
    @Autowired
    public final UserRepository userRepository;
    @Autowired
    public final MessRepository messRepository;
    @Autowired
    public final AuditUtil auditLog;


    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository, MessRepository messRepository, AuditUtil auditLog) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.messRepository = messRepository;
        this.auditLog = auditLog;
    }

    public Feedback addOrEditFeedback(int userId, int feedbackId, int messId, String feedbackData) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Feedback feedback = null;
        try {
            Optional<Mess> mess = messRepository.findById(messId);
            if (feedbackId == 0) {
                feedback = new Feedback();
            } else {
                feedback = feedbackRepository.findById(feedbackId).get();
            }
            feedback.setUser(user.get());
            feedback.setFeedback(feedbackData);
            feedback.setMess(mess.get());


            if (feedbackId == 0) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.SUCCESS, String.valueOf(user.get()));
            } else {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.SUCCESS, String.valueOf(user.get()));
            }
           return feedbackRepository.save(feedback);
        } catch(Exception ex) {
            if (feedbackId == 0)
                auditLog.createAudit(user.get().getUserName(), AuditOperation.CREATE, Status.FAIL, String.valueOf(ex));
            else
                auditLog.createAudit(user.get().getUserName(), AuditOperation.MODIFY, Status.FAIL, String.valueOf(ex));
        }

        return feedback;
    }

    public String deleteFeedback(int userId, RoleType roleType, int feedbackId) throws MessException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if ((roleType != RoleType.STUDENT) && (roleType != RoleType.MESSINCHARGE)) {
            try {
                feedbackRepository.deleteById(feedbackId);
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.SUCCESS, "deleteFeedback:" + "Success: input Data: " + "FeedbackData deleted: " + feedback);
                return "Success";
            } catch (Exception ex) {
                auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "deleteFeedback:" + "Failed: output Data: " + ex + "FeedbackData deleted: " + feedback);
                return "Fail";
            }
        } else {
            auditLog.createAudit(user.get().getUserName(), AuditOperation.DELETE, Status.FAIL, "deleteFeedback:" + "Failed: output Data: " + "Feedback can't be delete by: " + roleType);
            throw new MessException(roleType + " can't delete the data");
        }

    }

    public List<Feedback> listOfFeedbacks(int userId, RoleType roleType, int messId) throws MessException {
        List<Feedback> feedbackList = new ArrayList<>();
        if ((roleType == RoleType.ADMIN)) {
            Mess mess = messRepository.findById(messId).get();
            feedbackList = feedbackRepository.findByMess(mess);
        } else if (roleType == RoleType.MESSINCHARGE) {
            User user = userRepository.findById(userId).get();
            Mess mess = messRepository.findByUser(user);
            feedbackList = feedbackRepository.findByMess(mess);
        } else {
            throw new MessException("Couldn't fetch the data");
        }
        return feedbackList;
    }


    public List<Feedback> listOfFeedbacks() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackList;
    }

    public List<Feedback> listOfFeedbackByUser(int userId) {
        return feedbackRepository.findAllByUser(userId);
    }
}
