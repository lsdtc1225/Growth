package com.pinpointgrowth.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelInputParser {
    private Sheet sheet;
    private String courseName;
    private String courseTerm;
    private String courseRoom;

    private List<StudentDataRecord> studentList;

    public List<StudentDataRecord> getStudentList() {
        return studentList;
    }

    public ExcelInputParser(String filename) throws Exception {
        courseName = "";
        courseTerm = "";
        courseRoom = "";
        studentList = new ArrayList<StudentDataRecord>();
        InputStream inputstream = new FileInputStream(filename);
        Workbook workbook = WorkbookFactory.create(inputstream);
        sheet = workbook.getSheetAt(0);
    }

    public void parseExcel() {
        setupCourseName();
        setupCourseTerm();
        setupCourseRoom();
        setUpStudentArray();
    }

    public void setupCourseName() {
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        courseName = cell.getStringCellValue().replace(";", "")
                .replace("'", "").replace(("" + '"'), "");
    }

    public void setupCourseTerm() {
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(1);
        courseTerm = cell.getStringCellValue().replace(";", "")
                .replace("'", "").replace(("" + '"'), "");
    }

    public void setupCourseRoom() {
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(1);
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            courseRoom = cell.getStringCellValue().replace(";", "")
                    .replace("'", "").replace(("" + '"'), "");
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            courseRoom = ((int) cell.getNumericCellValue()) + "";
        }
    }

    public void setUpStudentArray() {
        for (int i = 7; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            Cell firstNameCell = row.getCell(0);
            Cell lastNameCell = row.getCell(1);
            Cell gradeCell = row.getCell(2);

            String firstName = "";
            String lastName = "";
            String grade = "";
            int gradeAsNum = -50;
            firstName = firstNameCell.getStringCellValue();
            lastName = lastNameCell.getStringCellValue();
            if (gradeCell.getCellType() == Cell.CELL_TYPE_STRING) {
                grade = gradeCell.getStringCellValue();
            } else if (gradeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                gradeAsNum = (int) gradeCell.getNumericCellValue();
            }
            if ((!firstName.isEmpty()) && (!lastName.isEmpty())
                    && ((!grade.isEmpty()) || (gradeAsNum != -50))) {
                StudentDataRecord studentDataRecord = new StudentDataRecord();
                studentDataRecord.setStudentFirstName(firstName);
                studentDataRecord.setStudentLastName(lastName);
                if (gradeAsNum != -50) {
                    studentDataRecord.setStudentGrade(gradeAsNum);
                } else {
                    studentDataRecord.setStudentGrade(0);
                }
                studentList.add(studentDataRecord);
            }
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public String getCourseRoom() {
        return courseRoom;
    }
}
