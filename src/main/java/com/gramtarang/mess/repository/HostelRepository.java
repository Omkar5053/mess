package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Integer> {


    Hostel findByHostelName(String hostelName);
}
