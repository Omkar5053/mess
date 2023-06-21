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
    @Query(value= "SELECT * from feedback a where a.user_id = :user_id", nativeQuery = true)
    List<Feedback> findAllByUser(int user_id);

}
