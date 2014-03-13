package com.pinpointgrowth.excel;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pinpointgrowth.beans.AddCourseExcelDisplayBean;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.log.Logger;

public class ParseExcel extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -814381020916085483L;
    private static final UUID uUID = UUID.randomUUID();
    private static String fullFileName;
    private static String userName;
    private static AddCourseExcelDisplayBean addCourseExcelDisplayBean;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        LoginBean loginBean = (LoginBean) request.getSession().getAttribute(
                "loginInfo");
        userName = loginBean.getUsername();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                @SuppressWarnings("rawtypes")
                List items = upload.parseRequest(request);
                @SuppressWarnings("rawtypes")
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField()) {
                        String fileName = uUID.toString();

                        String root = getServletContext().getRealPath("/");
                        File path = new File(root + "/uploads");
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }

                        fullFileName = path + "/" + fileName + ".xlsx";

                        item.write(new File(fullFileName));
                    }
                }

            } catch (FileUploadException e) {
                Logger.logStackTrace(e.getStackTrace(), getServletContext());
            } catch (Exception e) {
                Logger.logStackTrace(e.getStackTrace(), getServletContext());
            }

            try {
                parseAndSaveExcel();
                deleteFile();
            } catch (Exception e) {
                Logger.logStackTrace(e.getStackTrace(), getServletContext());
            }

            request.setAttribute("addCourseExcelDisplayBean",
                    addCourseExcelDisplayBean);

            String nextJSP = "/excelSuccess.jsp";
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher(nextJSP);
            dispatcher.forward(request, response);
        }
    }

    private void deleteFile() {
        File file = new File(fullFileName);
        file.delete();

    }

    private void parseAndSaveExcel() throws Exception {
        ExcelInputParser excelInputParser = new ExcelInputParser(fullFileName);
        excelInputParser.parseExcel();
        List<StudentDataRecord> studentList = excelInputParser.getStudentList();

        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        Statement courseIDStatement = con.createStatement();

        int t_id = getTeacherID(statement);
        statement.executeUpdate(Constants.COURSE_ADD_SQL(t_id,
                excelInputParser.getCourseTerm(),
                excelInputParser.getCourseName(),
                excelInputParser.getCourseRoom()));
        ResultSet courseIDResult = courseIDStatement.executeQuery(Constants
                .GET_COURSE_ID_SQL(t_id, excelInputParser.getCourseTerm(),
                        excelInputParser.getCourseName(),
                        excelInputParser.getCourseRoom()));
        courseIDResult.last();
        int coulmnForCID = courseIDResult.findColumn("C_ID");
        int courseID = courseIDResult.getInt(coulmnForCID);
        addCourseExcelDisplayBean = new AddCourseExcelDisplayBean();
        addCourseExcelDisplayBean.setCourseName(excelInputParser
                .getCourseName());
        addCourseExcelDisplayBean.setCourseTerm(excelInputParser
                .getCourseTerm());
        addCourseExcelDisplayBean.setCourseRoom(excelInputParser
                .getCourseRoom());
        addCourseExcelDisplayBean.setStudentList(studentList);

        for (StudentDataRecord record : studentList) {
            Statement addStudentStatement = con.createStatement();
            addStudentStatement.executeUpdate(Constants.STUDENT_ADD_SQL(
                    record.getStudentFirstName(), record.getStudentLastName(),
                    record.getStudentGrade()));
            Statement studentIDStatement = con.createStatement();
            ResultSet result = studentIDStatement.executeQuery(Constants
                    .GET_STUDENT_ID_SQL(record.getStudentFirstName(),
                            record.getStudentLastName(),
                            record.getStudentGrade()));
            result.last();
            int studentIDColumn = result.findColumn("S_ID");
            int studentID = result.getInt(studentIDColumn);
            Statement addTakingStatement = con.createStatement();
            addTakingStatement.executeUpdate(Constants.TAKING_ADD_SQL(
                    studentID, courseID));
        }
    }

    private int getTeacherID(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(Constants
                .TEACHER_ID_QUERY(userName));
        resultSet.first();
        int column = resultSet.findColumn("T_ID");
        int teacherID = resultSet.getInt(column);
        return teacherID;
    }
}
