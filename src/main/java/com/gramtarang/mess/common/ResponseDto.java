package com.gramtarang.mess.common;
// REVIEW PENDING

import java.util.ArrayList;
import java.util.List;

public class ResponseDto {

    private String commonErrorMessage;
    private int totalCount;
    private int createdCount;
    private int updatedCount;
    private int successCount;
    private int failureCount;
    private String headerMessage;
    private int numRecords;
    private boolean processingComplete = true;
    private final List<ResponseEntryDto> responses = new ArrayList<ResponseEntryDto>();

    public ResponseDto() {

    }

    public ResponseDto(String message) {
        this.commonErrorMessage = message;
    }

    public String getCommonErrorMessage() {
        return commonErrorMessage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public int getCreatedCount() {
        return createdCount;
    }

    public int getUpdatedCount() {
        return updatedCount;
    }

    public List<ResponseEntryDto> getResponses() {
        return responses;
    }

    public void incrementSuccessCount() {
        successCount++;
    }

    public void incrementFailureCount() {
        failureCount++;
    }

    public void incrementTotalCount() {
        totalCount++;
    }

    public void incrementCreatedCount() {
        createdCount++;
    }

    public void incrementUpdatedCount() {
        updatedCount++;
    }

    public void addResponseEntry(long id, int index, String message, boolean action) {
        ResponseEntryDto dto = new ResponseEntryDto(id, index, message, action);
        responses.add(dto);
    }

    public String getHeaderMessage() {
        if (headerMessage != null)
            return headerMessage;
        StringBuffer sb = new StringBuffer();
        sb.append("Total = ").append(numRecords);
        sb.append(", Processed = ").append(totalCount);
        sb.append(", Success = ").append(successCount);
        sb.append(", Failure = ").append(failureCount);
        sb.append(", Created = ").append(createdCount);
        sb.append(", Updated = ").append(updatedCount);
        return sb.toString();
    }

    public void setHeaderMessage(String message) {
        headerMessage = message;
    }

    public boolean getProcessingComplete() {
        return processingComplete;
    }

    public void setProcessingComplete(boolean processingComplete) {
        this.processingComplete = processingComplete;
    }

    public int getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }
}

