package com.gramtarang.mess.common;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImportBaseClass {

    Logger logger = LoggerFactory.getLogger(ImportBaseClass.class);

    protected ResponseDto currentResponse = null;
    protected XSSFWorkbook currentWorkbook = null;

    public ResponseDto getCurrentResponse() {
        ResponseDto dto = currentResponse;
        if (currentResponse.getProcessingComplete()) {
            currentResponse = null;
            currentWorkbook = null;
            logger.info("current work book set null by getcurrent response");
        }
        return dto;
    }

    static DataFormatter df = new DataFormatter();

    protected static String getString(Row row, int column) {
        String retval = "";
        try {
            retval = row.getCell(column).getStringCellValue();
        } catch (Exception ex) {
            retval = df.formatCellValue(row.getCell(column));
        }
        if (retval != null)
            retval = retval.trim();
        return retval;
    }

    protected void readFile(String fileName) throws IOException, InvalidFormatException {
        currentResponse = null;
        currentWorkbook = null;

        FileInputStream fis = new FileInputStream(fileName);
        byte[] data = fis.readAllBytes();
        String filename = fileName.split(".xlsx")[0];
        File tempFile = File.createTempFile(filename, ".xlsx");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(data);
        fos.flush();
        fos.close();
        fis.close();

        // Create Workbook instance holding reference to .xls file
        XSSFWorkbook workbook = new XSSFWorkbook(tempFile);
        this.currentWorkbook = workbook;
    }

    protected void countRows(int offset) {
        ResponseDto dto = new ResponseDto();
        currentResponse = dto;
        XSSFSheet sheet = currentWorkbook.getSheetAt(0);
        int numRows = sheet.getPhysicalNumberOfRows() - offset;
        dto.setNumRecords(numRows);
        dto.setProcessingComplete(false);
    }
}
