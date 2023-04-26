package com.gramtarang.mess.service;
// REVIEW PENDING

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.entity.auditlog.AuditLog;
import com.gramtarang.mess.entity.auditlog.AuditOperation;
import com.gramtarang.mess.entity.auditlog.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gramtarang.mess.repository.AuditLogDao;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuditUtil {
//    private static final Logger logger = new Logger(AuditUtil.class);

    private final AuditLogDao auditDao;

    public AuditUtil(AuditLogDao auditDao) {
        this.auditDao = auditDao;
    }

    /**
     * Creates a record in EMS_Audit_Log table
     *
     * @param userName
     * @param operation
     * @param status
     */
    /* Simplest log passing basic info and path assumed to be from UI. Called by successful read ops. Expecting that this will be
     * totally removed from the code since successful read ops need not be tracked */
    public void createAuditRead(String userName, AuditOperation operation, Status status, String logData) {
        // createAudit(userName, operation, status, logData, AuditOperationPath.UI, null, -1, -1, -1, -1);
    }

    public void createAudit(String userName, AuditOperation operation, Status status, String logData) throws MessException {
        createAudit(userName, operation, status, logData, 0);
    }

    public void createAudit(String userName, AuditOperation operation, Status status, String logData, int id) throws MessException {
        AuditLog auditLog = new AuditLog();
        auditLog.setLogData(logData);
        auditLog.setLogDate(new Timestamp(new Date().getTime()));
        auditLog.setOperation(operation);
        auditLog.setStatus(status);
        auditLog.setUserName(userName);
        auditDao.save(auditLog);
        auditDao.flush();
    }


}