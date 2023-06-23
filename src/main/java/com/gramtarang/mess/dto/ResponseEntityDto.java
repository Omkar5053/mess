package com.gramtarang.mess.dto;

import java.util.List;

public class ResponseEntityDto<T> {
    private String message;
    private boolean status;
    private List<T> listOfData;
    private T data;


    public ResponseEntityDto() {
    }

    public ResponseEntityDto(String message) {
        this.message = message;
    }

    public ResponseEntityDto(String message, boolean status, List<T> listOfData, T data) {
        this.message = message;
        this.status = status;
        this.listOfData = listOfData;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<T> getListOfData() {
        return listOfData;
    }

    public void setListOfData(List<T> listOfData) {
        this.listOfData = listOfData;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
