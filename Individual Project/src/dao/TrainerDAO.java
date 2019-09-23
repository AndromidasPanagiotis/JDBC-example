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
import model.AssignmentsPerStudentPerCourse;
import utils.DBUtils;

/**
 *
 * @author Panos
 */
public class TrainerDAO {

    public static void getTrainerCourses(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT c.id as courseID, c.title as title " +
                     "FROM users u INNER JOIN trainers_per_course tpc on u.id = tpc.userid " +
                     "INNER JOIN courses c ON tpc.courseid = c.id WHERE tpc.courseid = (SELECT courseid FROM trainers_per_course WHERE userid = " + id + ")";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                System.out.println("Course ID: " + rs.getInt(1) + ", Course title: " + rs.getString(2));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void getStudentsPerCourse(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT c.id as courseID, c.title as title, u.id as student, u.firstname, u.lastname " +
                     "FROM users u INNER JOIN students_per_course spc on u.id = spc.userid " +
                     "INNER JOIN courses c ON spc.courseid = c.id WHERE spc.courseid = " + id + "";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                System.out.println("Course ID: " + rs.getInt(1) + ", Course title: " + rs.getString(2) + 
                        ", Student ID: " + rs.getInt(3) + ", Student Name: " + rs.getString(4) + " " + rs.getString(5));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void getAssignmentsPerStudentPerCourse(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT * FROM assignments_per_student_per_course WHERE courseid = " + id + " ORDER BY userid";
        String submitted;
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                submitted = rs.getInt(8)==1?"Yes":"No";
                System.out.println("Student ID: " + rs.getInt(2) + ", Assignment ID: " + rs.getInt(1) + 
                        ", Submission date: " + rs.getDate(5) + ", Oral mark: " + rs.getDouble(6) + ", Total mark: " + rs.getDouble(7) + ", Submitted: " + submitted);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void markAssignmentsPerStudentPerCourse(AssignmentsPerStudentPerCourse assignment) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE assignments_per_student_per_course SET oral_mark = ?, total_mark = ? WHERE assignmentid = ? && userid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setDouble(1, assignment.getOralMark());
            pstm.setDouble(2, assignment.getTotalMark());
            pstm.setInt(3, assignment.getId());
            pstm.setInt(4, assignment.getStudentId());            
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Marks successfully submitted !");
        }
    }
    
}
