package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.AmbulanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmbulanceRequestRepository extends JpaRepository<AmbulanceRequest, Integer> {
    @Query(value= "SELECT * from ambulance_request ha where ha.user = :user", nativeQuery = true)
    List<AmbulanceRequest> findAllRequestsByUser(String user);
}
