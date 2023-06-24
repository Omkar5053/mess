package com.gramtarang.mess.controller;

import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.ResponseDto;
import com.gramtarang.mess.dto.ResponseEntityDto;
import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.service.HostelAttendanceImportService;
import com.gramtarang.mess.service.HostelAttendanceService;
import com.gramtarang.mess.service.MyExcelHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/hostelAttendance")
public class HostelAttendanceController {
    Logger logger = LoggerFactory.getLogger(HostelAttendanceController.class);
    private HostelAttendanceService hostelAttendanceService;

    private HostelAttendanceImportService hostelAttendanceImportService;

    private MyExcelHelper myExcelHelper;

    public HostelAttendanceController(HostelAttendanceService hostelAttendanceService, HostelAttendanceImportService hostelAttendanceImportService,MyExcelHelper myExcelHelper) {
        this.hostelAttendanceService = hostelAttendanceService;
        this.hostelAttendanceImportService = hostelAttendanceImportService;
        this.myExcelHelper = myExcelHelper;
    }

    @PostMapping("/addAttendance")
    public @ResponseBody
    ResponseEntityDto<HostelAttendance> addStudentAttendances(
            @RequestParam("hostel_id") int hostel_id,
            @RequestParam("floorNo") int floorNo,
            @RequestParam("studentAttendances") String userIds,
                                            HttpServletRequest request) throws MessException
    {
            String[] ids = userIds.split(",");
            int[] arr = new int[ids.length];
            for (int i = 0; i < ids.length; i++) {
                arr[i] = Integer.valueOf(ids[i]);
                System.out.println(arr[i]);
            }
            return hostelAttendanceService.add(hostel_id, floorNo, arr);
    }


    @RequestMapping(value = "/importAttendance", method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto importData(@RequestParam(value = "file", required = false) MultipartFile inputFile, HttpServletRequest request) throws MessException, IOException, IllegalStateException, MessException {
        System.out.println("FileName:" + inputFile);
        File tempFile = null;
        String errorMessage = null;
        StringWrapper outputMessage = null;
        boolean isMultipart = true;
        ResponseDto result = null;

        if (isMultipart) {
            byte[] data = inputFile.getBytes();
            String filename = inputFile.getOriginalFilename().split(".xls")[0];
            tempFile = File.createTempFile(filename, ".xls");
            System.out.println("TempFile:" + tempFile);
            FileOutputStream fos = new FileOutputStream(tempFile);
            System.out.println("fileOutPutStream:" + fos);
            fos.write(data);
            fos.flush();
            fos.close();
            if (tempFile != null) {
                System.out.println("AbsolutePath:" + tempFile.getAbsolutePath());
                result = hostelAttendanceImportService.addHostelAttendance(tempFile.getAbsolutePath());
            } else {
                errorMessage = "Could not find the file";
            }
            tempFile.delete();   // delete the read file
        } else {
            errorMessage = "Wrong mapping";
        }
        if (result != null)
            return result;
        else if (outputMessage != null) {
            logger.debug(outputMessage.getContent());
            result = new ResponseDto();
            result.setHeaderMessage(outputMessage.getContent());
            logger.debug(result.getHeaderMessage());
        } else {
            result = new ResponseDto(errorMessage);
        }
        return result;
    }

    @PostMapping("/addAttendanceByExcel")
    public @ResponseBody
    ResponseEntityDto<HostelAttendance> addAttendances(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws MessException
    {
        if (myExcelHelper.checkExcelFormat(file)) {
            return hostelAttendanceService.saveFile(file);
        }
        return new ResponseEntityDto<HostelAttendance>("Please Upload Excel FIle Only!!!");
    }
}