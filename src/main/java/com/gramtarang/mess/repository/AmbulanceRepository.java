package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Ambulance;
import com.gramtarang.mess.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Integer> {
    @Query(value= "SELECT * from ambulance a where a.user_id = :user_id", nativeQuery = true)
    List<Ambulance> findAllByUserUserId(int user_id);
}
