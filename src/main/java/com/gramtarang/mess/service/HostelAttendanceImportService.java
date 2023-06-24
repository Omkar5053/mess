package com.gramtarang.mess.service;
// REVIEW PENDING

import com.gramtarang.mess.common.ImportBaseClass;
import com.gramtarang.mess.common.MessException;
import com.gramtarang.mess.common.ResponseDto;
import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.AttendanceStatus;
import com.gramtarang.mess.repository.HostelAttendanceRepository;
import com.gramtarang.mess.repository.UserRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class HostelAttendanceImportService extends ImportBaseClass {
    Logger logger = LoggerFactory.getLogger(HostelAttendanceImportService.class);

    public final HostelAttendanceRepository hostelAttendanceRepository;

    public final UserRepository userRepository;

    public HostelAttendanceImportService(HostelAttendanceRepository hostelAttendanceRepository,
                                   UserRepository userRepository) {
        this.hostelAttendanceRepository = hostelAttendanceRepository;
        this.userRepository = userRepository;
    }

    enum RegistrationCols {
        SlNo,
        RegNo,
        Date,
        AttendanceStatus;
    }

    public ResponseDto addHostelAttendance(String fileName) throws MessException {
        System.out.println("Entered to addHostelAttendance");
        try {
            if (currentWorkbook != null) {
                throw new MessException("File already in process");
            }
            System.out.println("FileName:" + fileName);
            readFile(fileName);

            countRows(1);

            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                processFile();
                currentWorkbook = null;
            });
            return currentResponse;
        } catch(MessException e) {
            String logdata = "Create Subject Registration :" + e.getMessage();
            ResponseDto dto = new ResponseDto(logdata);
            return dto;
        }
        catch (Exception ex) {
            String logdata = "Create Subject Registration :" + ex.getMessage();
            ResponseDto dto = new ResponseDto(logdata);
            return dto;
        }
    }

    @Async
    public ResponseDto processFile() {
        System.out.println("Entered to process file");
        ResponseDto dto = currentResponse;
        int rowNumber = 1;
        try {
            XSSFWorkbook workbook = currentWorkbook;
            XSSFSheet sheet = workbook.getSheetAt(0);   // Get first/desired sheet from the workbook
            System.out.println("Sheet:" + sheet);
            Iterator<Row> rowIterator = sheet.iterator();  // Iterate through each rows one by one
            rowIterator.next();                // Ignore first row it is headers
            String registrationNo = null;
            ZonedDateTime now = ZonedDateTime.now();

            Set<String> outputRegistrationList = new LinkedHashSet<>();

            while (rowIterator.hasNext()) {
                System.out.println("RowIterator");
                Row row = rowIterator.next();
                User user = null;
                rowNumber++;
                registrationNo = getString(row, RegistrationCols.RegNo.ordinal());
                System.out.println("Regno:" + registrationNo);
                if (registrationNo == null || registrationNo == "") {
                    dto.incrementTotalCount();
                    dto.incrementFailureCount();
                    continue;
                } else {
                    user = userRepository.findUserByUserName(registrationNo);
                    System.out.println("User:" + user);
                    if (user == null) {
                        outputRegistrationList.add(registrationNo);
                        dto.incrementTotalCount();
                        dto.incrementFailureCount();
                        continue;
                    }
                }

                String date = getString(row, RegistrationCols.Date.ordinal());
                System.out.println("Date:" + date);
                String status = getString(row, RegistrationCols.AttendanceStatus.ordinal());
                System.out.println("Status:" + status);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String arr[] = date.split("/");
                int y = Integer.parseInt(arr[0]);
                int m = Integer.parseInt(arr[1]);
                int d= Integer.parseInt(arr[2]);
                LocalDate localDate = LocalDate.of(y, m, d);
                System.out.println(localDate);
                HostelAttendance hostelAttendance = hostelAttendanceRepository.findByUserAndDate(user.getUserId(), localDate);
                System.out.println("HostelAttendance:" + hostelAttendance);
                if (hostelAttendance == null) {
                    HostelAttendance hostelAttendance1 = new HostelAttendance();
                    hostelAttendance1.setAttendanceStatus(AttendanceStatus.valueOf(status));
                    hostelAttendance1.setDate(LocalDate.now());
                    hostelAttendance1.setUser(user);
                    hostelAttendance = hostelAttendanceRepository.save(hostelAttendance1);
                    System.out.println("HostelAttendanceData:" + hostelAttendance1.getHostel_attendance_id());
                    dto.incrementSuccessCount();
                    dto.incrementTotalCount();
                    dto.incrementCreatedCount();

                }
                else {
                    hostelAttendance.setAttendanceStatus(AttendanceStatus.valueOf(status));
//                    hostelAttendance.setDate(LocalDate.now());
                    hostelAttendance = hostelAttendanceRepository.save(hostelAttendance);
                    System.out.println("HostelAttendanceUpdate:" + hostelAttendance.getHostel_attendance_id());
                    dto.incrementSuccessCount();
                    dto.incrementTotalCount();
                    dto.incrementUpdatedCount();
                }
            }

            if (outputRegistrationList.size() != 0) {
                this.currentResponse = new ResponseDto("These are the registration numbers not available: " + outputRegistrationList.toString());
                dto.setProcessingComplete(true);
            }
            return dto;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            dto.setProcessingComplete(true);
            return dto;
        }

    }
}