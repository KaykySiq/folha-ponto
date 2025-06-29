package com.ibametro.folha_ponto_api.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.ibametro.folha_ponto_api.domain.enums.Mes;

@Service
public class FormService {
    private final HolidayService holidayService;

    private static final int[] dayCells = { 1, 2, 3, 4, 5, 6, 7 };

    public FormService(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    public void saveChanges(File file, Workbook workbook,
            String name, String employeeId,
            String month, int year) {

        if (file == null || workbook == null) {
            throw new IllegalArgumentException("O arquivo ou a planilha não podem ser nulos.");
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            Sheet sheet = workbook.getSheetAt(0);

            fillHeader(sheet, name, employeeId, month);
            clearContent(sheet);
            updateDates(sheet, month, year);

            workbook.write(fos);
            System.out.println("Alterações salvas com sucesso no arquivo: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar as alterações no arquivo: " + e.getMessage(), e);
        }
    }

    private void fillHeader(Sheet sheet, String name, String employeeId, String month) {
        Row headerRow = sheet.getRow(7);
        Row workloadRow = sheet.getRow(10);

        Cell nameCell = headerRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        nameCell.setCellValue(name);

        Cell employeeIdCell = headerRow.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        employeeIdCell.setCellValue(employeeId);

        Cell workloadCell = workloadRow.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        workloadCell.setCellValue("ESTAGIARIO".equalsIgnoreCase(employeeId) ? "30H" : "40H");

        Cell monthCell = workloadRow.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        monthCell.setCellValue(month);
    }

    private void clearContent(Sheet sheet) {
        int startRow = 15;

        for (int i = 0; i < 31; i++) {
            Row row = sheet.getRow(startRow + i);
            if (row != null) {
                for (int col : dayCells) {
                    Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setBlank();
                }
            }
        }
    }

    private void updateDates(Sheet sheet, String month, int year) {
        int refMonth = Mes.getMesIndex(month);
        YearMonth yearMonth = YearMonth.of(year, refMonth);
        int daysInMonth = yearMonth.lengthOfMonth();

        List<Integer> holidays = holidayService.getHolidays(year, month);

        int startRow = 15;
        for (int day = 1; day <= daysInMonth; day++) {
            Row row = sheet.getRow(startRow + day - 1);
            if (row == null) {
                row = sheet.createRow(startRow + day - 1);
            }

            LocalDate date = LocalDate.of(year, refMonth, day);
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase();

            String days = null;
            if (dayOfWeek.equals("SÁBADO")) {
                days = "SÁBADO";
            } else if (dayOfWeek.equals("DOMINGO")) {
                days = "DOMINGO";
            } else if (holidays.contains(day)) {
                days = "FERIADO";
            }

            Cell weekDayCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (days != null) {
                weekDayCell.setCellValue(days);
                int[] fill = {1, 2, 4, 5, 7};
                for (int col : fill) {
                    Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue(days);
                }
            } else {
                weekDayCell.setBlank();
            }

            Cell dayCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            dayCell.setCellValue(day);
        }
    }
}
