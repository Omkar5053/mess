package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.auditlog.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuditLogDao extends JpaRepository<AuditLog, Integer>{

    @Query("FROM AuditLog WHERE logDate >= :startDate and logDate <= :endDate")
    List<AuditLog> find(Date startDate, Date endDate);


}
