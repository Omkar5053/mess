package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.entity.Internship;
import com.gramtarang.mess.entity.LeaveData;
import com.gramtarang.mess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Integer> {

    @Query("from Internship where hostel = :hostel")
    List<Internship> findByHostel(Hostel hostel);

}
