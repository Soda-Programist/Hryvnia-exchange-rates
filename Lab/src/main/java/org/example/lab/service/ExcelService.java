package org.example.lab.service;

import org.example.lab.model.HryvniaExchangeItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelService {

    public static byte[] createExcelFile(List<HryvniaExchangeItem> wishItems) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Курс гривні");
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 8000);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"№", "Код цифровий", "Код літерний", "Назва валюти", "Офіційний курс"};
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (HryvniaExchangeItem wishItem : wishItems) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(wishItem.ID);
            row.createCell(1).setCellValue(wishItem.DigitalCode);
            row.createCell(2).setCellValue(wishItem.LetterCode);
            row.createCell(3).setCellValue(wishItem.Name);
            row.createCell(4).setCellValue(wishItem.OfficialCourse);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
