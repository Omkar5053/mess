package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Integer> {


    Hostel findByHostelName(String hostelName);
//    @Query(value = "SELECT * FROM hostel h where h.hostel_id = :hostel_id",nativeQuery = true)
//    List<StudentData> findAllStudentsByHostel(int hostel_id);
}
