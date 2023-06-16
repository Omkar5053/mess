package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDataRepository extends JpaRepository<StudentData, Integer> {

    @Query(value = "SELECT * FROM student_data sd where sd.hostel_id = :hostel_id",nativeQuery = true)
    List<StudentData> findAllStudentsByHostel(int hostel_id);
    @Query(value = "SELECT * FROM student_data sd where sd.hostel_id = :hostel_id AND sd.floor_no = :floor_no",nativeQuery = true)
    List<StudentData> findAllStudentsByHostelAndFloor(int hostel_id, int floor_no);
    @Query(value = "SELECT * FROM student_data sd where sd.hostel_id = :hostel_id AND sd.floor_no = :floor_no AND sd.room_no = :room_no",nativeQuery = true)
    List<StudentData> findAllStudentsByHostelAndFloorAndRoom(int hostel_id, int floor_no, int room_no);

}
