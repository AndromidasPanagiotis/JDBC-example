/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.AssignmentsPerStudentPerCourse;
import utils.DBUtils;

/**
 *
 * @author Panos
 */
public class StudentDAO {
    
    public static void getSchedulePerCourse(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT c.title as course, sc.subject, sc.date FROM courses c INNER JOIN schedule_per_course sc ON c.id = sc.courseid "
                + "WHERE c.id = (SELECT courseid FROM students_per_course WHERE userid = " + id + ")";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                System.out.println("Course: " + rs.getString(1) + ", Subject: " + rs.getString(2) + ", Date: " + rs.getDate(3));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void getAssignmentsPerCourseSubmissionDate(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT a.title as Assignment, apspc.assignmentid as AssignmentID, apspc.submission_date_time, apspc.submitted FROM assignments a "
                + "INNER JOIN assignments_per_student_per_course apspc ON a.id = apspc.assignmentid "
                + "WHERE apspc.assignmentid IN (SELECT apspc.assignmentid FROM assignments_per_course WHERE userid = " + id + ") " + "ORDER BY apspc.assignmentid";
        String submitted;
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                submitted = rs.getInt(4)==1?"Yes":"No";
                System.out.println("Assignment: " + rs.getString(1) + ", Assignment ID: " + rs.getInt(2) + ", Due date: " + rs.getDate(3) + ", Submitted: " + submitted);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void submitAssignment(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();
        AssignmentsPerStudentPerCourse assignment = new AssignmentsPerStudentPerCourse();
        String sql = "UPDATE assignments_per_student_per_course SET submitted = ? WHERE assignmentid = ? && userid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            assignment.setSubmitted(true);
            pstm.setBoolean(1, assignment.isSubmitted());
            pstm.setInt(2, ids.get(0));
            pstm.setInt(3, ids.get(1));            
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Assignment successfully submitted !");
        }
    }
}
