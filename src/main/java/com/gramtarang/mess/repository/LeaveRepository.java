package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Feedback;
import com.gramtarang.mess.entity.LeaveData;
import com.gramtarang.mess.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveData, Integer> {


}
