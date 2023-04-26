package com.gramtarang.mess.repository;

import java.util.Date;
import java.util.List;

import com.gramtarang.mess.entity.auditlog.AuditLog;
import com.gramtarang.mess.entity.auditlog.Status;
import com.gramtarang.mess.entity.auditlog.AuditOperation;

public interface AuditLogCustomDao {

    List<AuditLog> find(Date startDate, Date endDate,
                        Status status, AuditOperation operation,
                        String logData, String regNo, long subjectId, long examId);

}