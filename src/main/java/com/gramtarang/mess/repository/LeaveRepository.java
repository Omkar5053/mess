package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveData, Integer> {

    @Query("from LeaveData where user = :user")
    List<LeaveData> findByUser(User user);

    @Query("from LeaveData where hostel = :hostel")
    List<LeaveData> findByHostel(Hostel hostel);

}
