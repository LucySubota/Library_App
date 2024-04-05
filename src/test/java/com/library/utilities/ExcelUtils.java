package com.library.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;

public class ExcelUtils {

    private Sheet workSheet;
    private Workbook workbook;
    private String path;

    public ExcelUtils(String path, String sheetName){
        this.path = path;
        try{

            FileInputStream excelFile = new FileInputStream(path);
            workbook = WorkbookFactory.create(excelFile);
            workSheet = workbook.getSheet(sheetName);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public String getCellData(int row, int column){
        Cell cell;
        try{
            cell = workSheet.getRow(row).getCell(column);
            String cellData = cell.toString();
            return cellData;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
