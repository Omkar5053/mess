package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Feedback;
import com.gramtarang.mess.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("from Feedback where mess = :mess")
    List<Feedback> findByMess(Mess mess);

}
