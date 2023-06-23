package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.HostelAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HostelAttendanceRepository extends JpaRepository<HostelAttendance, Integer> {
    @Query(value= "SELECT * from hostel_attendance ha where ha.user_id = :user_id", nativeQuery = true)
    List<HostelAttendance> findHostelAttendancesByUser_UserId(Integer user_id);

    @Query(value= "SELECT * from hostel_attendance ha where ha.date = :date", nativeQuery = true)
    List<HostelAttendance> findHostelAttendancesByDate(LocalDate date);
}
