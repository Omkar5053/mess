package com.gramtarang.mess.service;

import com.gramtarang.mess.entity.HostelAttendance;
import com.gramtarang.mess.entity.User;
import com.gramtarang.mess.enums.AttendanceStatus;
import com.gramtarang.mess.repository.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MyExcelHelper {


    @Autowired
    private  UserRepository userRepository;

    public MyExcelHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    //check that file is of excel type or not
    public  boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    //convert excel to list of products

    public  List<HostelAttendance> convertExcelToListOfProduct(InputStream is) {
        List<HostelAttendance> list = new ArrayList<>();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("data");
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                HostelAttendance p = new HostelAttendance();

                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 0:
                            p.setHostel_attendance_id((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            System.out.println(cid);
                            System.out.println(rowNumber);
//                            String date = (String)cell.getStringCellValue();
//                            System.out.println(cell.getStringCellValue());
//                            p.setDate(LocalDate.parse(date));
                            p.setDate(LocalDate.now());
                            break;
                        case 2:
                            String userName = (String) cell.getStringCellValue();
                            System.out.println(userName);
                            User user = userRepository.findUserByUserName(userName);
                            System.out.println(user);
                            p.setUser(user);
                            break;
                        case 3:
                            String attendance = (String)cell.getStringCellValue();
                            System.out.println(attendance);
                            if(attendance.equalsIgnoreCase("ABSENT"))
                            {
                                p.setAttendanceStatus(AttendanceStatus.ABSENT);
                            } else{
                                p.setAttendanceStatus(AttendanceStatus.PRESENT);
                            }
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
