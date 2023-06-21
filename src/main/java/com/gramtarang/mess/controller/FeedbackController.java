package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.Feedback;
import com.gramtarang.mess.entity.Internship;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.FeedbackService;
import com.gramtarang.mess.service.MessService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/addOrEditFeedback")
    public Feedback addOrEditFeedback(@RequestParam("feedbackId")int feedbackId,
                                      @RequestParam("userId") String userId,
                                      @RequestParam("messId")int messId,@RequestParam("feedback")String feedbackData, HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        Feedback feedback = feedbackService.addOrEditFeedback(Integer.parseInt(userId), feedbackId, messId, feedbackData);
        return feedback;
    }

    @PostMapping("/deleteFeedback")
    public @ResponseBody
    String deleteFeedback(@RequestParam("feedbackId") int feedbackId,
                          @RequestParam("userId") String userId,
                          @RequestParam("roleType") RoleType roleType,
                          HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        String replyData = feedbackService.deleteFeedback(Integer.parseInt(userId), roleType, feedbackId);
        return replyData;
    }

    @PostMapping("/listOfFeedbacksByMess")
    public @ResponseBody
    List<Feedback> listOfFeedbacks(@RequestParam("messId")int messId,
                                   @RequestParam("userId") String userId,
                                   @RequestParam("roleType") RoleType roleType,
                                   HttpServletRequest request) throws MessException {
//        String userId = (String) request.getSession().getAttribute("USERID");
//        RoleType roleType = (RoleType) request.getSession().getAttribute("ROLE-TYPE");
        List<Feedback> feedbackList = feedbackService.listOfFeedbacks(Integer.parseInt(userId), roleType, messId);
        return feedbackList;
    }

    @PostMapping("/listOfFeedbacksByUser")
    public @ResponseBody
    List<Feedback> listOfFeedbacksByUser(
                                   @RequestParam("userId") String userId,
                                   HttpServletRequest request) throws MessException {

        List<Feedback> feedbackList = feedbackService.listOfFeedbackByUser(Integer.parseInt(userId));
        return feedbackList;
    }

    @PostMapping("/listOfFeedbacks")
    public @ResponseBody
    List<Feedback> listOfFeedbacks(HttpServletRequest request) throws MessException {
        List<Feedback> feedbackList = feedbackService.listOfFeedbacks();
        return feedbackList;
    }
}
