package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface HostelAttendanceRepository extends JpaRepository<HostelAttendance, Integer> {
    @Query(value= "SELECT * from hostel_attendance ha where ha.user_id = :user_id", nativeQuery = true)
    List<HostelAttendance> findHostelAttendancesByUser_UserId(Integer user_id);
    @Query(value= "Select * from hostel_attendance where user_id = :user_id", nativeQuery = true)
    HostelAttendance findByUser(int user_id);
}
