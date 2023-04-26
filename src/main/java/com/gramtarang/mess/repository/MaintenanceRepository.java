package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Maintenance;
import com.gramtarang.mess.enums.MaintenanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    List<Maintenance> findAllByMaintenanceType(MaintenanceType maintenanceType);

    @Query(value= "SELECT * from maintenance m where m.user_id = :user_id", nativeQuery = true)
    List<Maintenance> findAllByUserUserId(int user_id);
}
