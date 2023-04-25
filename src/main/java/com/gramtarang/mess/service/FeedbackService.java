package com.gramtarang.mess.service;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Feedback;
import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.RegistrationStatus;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.enums.UserType;
import com.gramtarang.mess.repository.FeedbackRepository;
import com.gramtarang.mess.repository.MessRepository;
import com.gramtarang.mess.repository.MessUserRepository;
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


    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository, MessRepository messRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.messRepository = messRepository;
    }

    public Feedback addOrEditFeedback(int userId, int feedbackId, int messId, String feedbackData) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Mess> mess = messRepository.findById(messId);
        Feedback feedback = null;
        if (feedbackId == 0) {
            feedback = new Feedback();
        } else {
            feedback = feedbackRepository.findById(feedbackId).get();
        }
        feedback.setUser(user.get());
        feedback.setFeedback(feedbackData);
        feedback.setMess(mess.get());
        feedback = feedbackRepository.save(feedback);
        feedbackRepository.flush();

        return feedback;
    }

    public String deleteFeedback(int userId, RoleType roleType, int feedbackId) throws MessException {
        if ((roleType != RoleType.STUDENT) && (roleType != RoleType.MESSINCHARGE)) {
            feedbackRepository.deleteById(feedbackId);
            return "Success";
        } else {
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

}
