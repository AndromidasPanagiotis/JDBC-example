/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Assignments;
import model.Courses;
import model.SchedulePerCourse;
import model.Users;
import utils.DBUtils;

/**
 *
 * @author Panos
 */
public class HeadMasterDAO {
    
    public static void insertCourse(Courses course) {
        Connection con = DBUtils.getConnection();       
        String sql = "INSERT INTO courses (title,stream,type,start_date,end_date) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, course.getTitle());
            pstm.setString(2, course.getStream());
            pstm.setString(3, course.getType());
            pstm.setDate(4, Date.valueOf(course.getStartDate()));
            pstm.setDate(5, Date.valueOf(course.getEndDate()));
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Course successfully added !");
        }
    }
    
    public static void getCourses() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT * FROM courses";
        ArrayList <Courses> courses = new ArrayList();  
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                Courses course = new Courses();
                course.setId(rs.getInt(1));
                course.setTitle(rs.getString(2));
                course.setStream(rs.getString(3));
                course.setType(rs.getString(4));
                course.setStartDate(rs.getDate(5).toLocalDate());
                course.setEndDate(rs.getDate(6).toLocalDate());              
                courses.add(course);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            for (Courses course : courses) {
            System.out.println(course.toString());
            }
        }
    }
    
    public static void updateCourse(Courses course) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE courses SET title = ?, stream = ?, type = ?, start_date = ?, end_date = ? WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);          
            pstm.setString(1, course.getTitle());
            pstm.setString(2, course.getStream());
            pstm.setString(3, course.getType());
            pstm.setDate(4, Date.valueOf(course.getStartDate()));
            pstm.setDate(5, Date.valueOf(course.getEndDate()));
            pstm.setInt(6, course.getId());
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Course successfully updated !");
        }
    }
    
    public static void deleteCourse(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM courses WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Course successfully deleted !");
        }
    }
    
    public static void insertAssignment(Assignments assignment) {
        Connection con = DBUtils.getConnection();
        String sql = "INSERT INTO assignments (title,description) VALUES (?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, assignment.getTitle());
            pstm.setString(2, assignment.getDescription());
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Assignment successfully added !");
        }
    }
    
    public static void getAssignments() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT * FROM assignments";
        ArrayList <Assignments> assignments = new ArrayList();  
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                Assignments assignment = new Assignments();
                assignment.setId(rs.getInt(1));
                assignment.setTitle(rs.getString(2));
                assignment.setDescription(rs.getString(3));            
                assignments.add(assignment);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            for (Assignments assignment : assignments) {
            System.out.println(assignment.toString());
            }
        }
    }
    
    public static void updateAssignment(Assignments assignment) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE assignments SET title = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, assignment.getTitle());
            pstm.setString(2, assignment.getDescription());
            pstm.setInt(3, assignment.getId());
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Assignment successfully updated !");
        }
    }
    
    public static void deleteAssignment(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM assignments WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Assignment successfully deleted !");
        }
    }
    
    public static void insertStudent(Users student) {
        Connection con = DBUtils.getConnection();       
        String sql = "INSERT INTO users (firstname,lastname,birthdate,username,password,roleid) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, student.getFirstName());
            pstm.setString(2, student.getLastName());
            pstm.setDate(3, Date.valueOf(student.getBirthDate()));
            pstm.setString(4, student.getUsername());
            pstm.setString(5, student.getPassword());
            pstm.setInt(6, 2); // roleID = 2 because user is a student
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Student successfully added !");
        }
    }
    
    public static void getStudents() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT * FROM users WHERE roleid = 2"; // roleID = 2 because user is a student
        ArrayList <Users> students = new ArrayList();  
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                Users student = new Users();
                student.setId(rs.getInt(1));
                student.setFirstName(rs.getString(2));
                student.setLastName(rs.getString(3)); 
                student.setBirthDate(rs.getDate(4).toLocalDate()); 
                student.setUsername(rs.getString(5)); 
                student.setPassword(rs.getString(6)); 
                student.setRoleId(rs.getInt(7));
                students.add(student);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            for (Users student : students) {
            System.out.println(student.toString());
            }
        }
    }
    
    public static void updateStudent(Users student) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE users SET firstname = ?, lastname = ?, birthdate = ?, username = ?, password = ? WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, student.getFirstName());
            pstm.setString(2, student.getLastName());
            pstm.setDate(3, Date.valueOf(student.getBirthDate()));
            pstm.setString(4, student.getUsername());
            pstm.setString(5, student.getPassword());
            pstm.setInt(6, student.getId());
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Student successfully updated !");
        }
    }
    
    public static void deleteStudent(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Student successfully deleted !");
        }
    }
    
    public static void insertTrainer(Users trainer) {
        Connection con = DBUtils.getConnection();
        String sql = "INSERT INTO users (firstname,lastname,birthdate,username,password,roleid) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, trainer.getFirstName());
            pstm.setString(2, trainer.getLastName());
            pstm.setDate(3, Date.valueOf(trainer.getBirthDate()));
            pstm.setString(4, trainer.getUsername());
            pstm.setString(5, trainer.getPassword());
            pstm.setInt(6, 3); // roleID = 3 because user is a trainer
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Trainer successfully added !");
        }
    }
    
    public static void getTrainers() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT * FROM users WHERE roleid = 3"; // roleID = 3 because user is a trainer
        ArrayList <Users> trainers = new ArrayList();  
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                Users trainer = new Users();
                trainer.setId(rs.getInt(1));
                trainer.setFirstName(rs.getString(2));
                trainer.setLastName(rs.getString(3)); 
                trainer.setBirthDate(rs.getDate(4).toLocalDate()); 
                trainer.setUsername(rs.getString(5)); 
                trainer.setPassword(rs.getString(6)); 
                trainer.setRoleId(rs.getInt(7));
                trainers.add(trainer);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            for (Users trainer : trainers) {
            System.out.println(trainer.toString());
            }
        }
    }
    
    public static void updateTrainer(Users trainer) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE users SET firstname = ?, lastname = ?, birthdate = ?, username = ?, password = ? WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, trainer.getFirstName());
            pstm.setString(2, trainer.getLastName());
            pstm.setDate(3, Date.valueOf(trainer.getBirthDate()));
            pstm.setString(4, trainer.getUsername());
            pstm.setString(5, trainer.getPassword());
            pstm.setInt(6, trainer.getId());
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Trainer successfully updated !");
        }
    }
    
    public static void deleteTrainer(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Trainer successfully deleted !");
        }
    }
    
    public static void insertStudentPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();       
        String sql = "INSERT INTO students_per_course (userid,roleid,courseid) VALUES (?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(0));
            pstm.setInt(2, 2);  // roleid = 2 for student          
            pstm.setInt(3, ids.get(1));
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Student per course successfully added !");
        }
    }
    
    public static void insertTrainerPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();       
        String sql = "INSERT INTO trainers_per_course (userid,roleid,courseid) VALUES (?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(0));
            pstm.setInt(2, 3);  // roleid = 3 for trainer          
            pstm.setInt(3, ids.get(1));
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Trainer per course successfully added !");
        }
    }
    
    public static void insertAssignmentPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();       
        String sql = "INSERT INTO assignments_per_course (assignmentid,courseid) VALUES (?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(0));            
            pstm.setInt(2, ids.get(1));
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Assignment per course successfully added !");
        }
    }
    
    public static void insertSchedulePerCourse(SchedulePerCourse spc) {
        Connection con = DBUtils.getConnection();       
        String sql = "INSERT INTO schedule_per_course (subject,date,courseid) VALUES (?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, spc.getSubject());
            pstm.setDate(2, Date.valueOf(spc.getDate()));            
            pstm.setInt(3, spc.getCourseId());
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Schedule per course successfully added !");
        }
    }

    public static void getStudentsPerCourse() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT c.id as courseID, c.title as title, u.id as student, u.firstname, u.lastname " +
                     "FROM users u INNER JOIN students_per_course spc on u.id = spc.userid " +
                     "INNER JOIN courses c ON spc.courseid = c.id WHERE spc.roleid = 2 ORDER BY c.id;";
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

    public static void getTrainersPerCourse() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT c.id as courseID, c.title as title, u.id as trainer, u.firstname, u.lastname " +
                     "FROM users u INNER JOIN trainers_per_course tpc on u.id = tpc.userid " +
                     "INNER JOIN courses c ON tpc.courseid = c.id WHERE tpc.roleid = 3 ORDER BY c.id;";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                System.out.println("Course ID: " + rs.getInt(1) + ", Course title: " + rs.getString(2) + 
                        ", Trainer ID: " + rs.getInt(3) + ", Trainer Name: " + rs.getString(4) + " " + rs.getString(5));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void getAssignmentsPerCourse() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT c.id as courseID, c.title as title, a.id as assignment, a.title " +
                     "FROM assignments a INNER JOIN assignments_per_course apc on a.id = apc.assignmentid " +
                     "INNER JOIN courses c ON apc.courseid = c.id ORDER BY c.id;";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                System.out.println("Course ID: " + rs.getInt(1) + ", Course title: " + rs.getString(2) + 
                        ", Assignment ID: " + rs.getInt(3) + ", Assignment Title: " + rs.getString(4));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void getSchedulePerCourse() {
        Connection con = DBUtils.getConnection();
        String sql = "SELECT c.id as course, sc.id, sc.subject, sc.date FROM courses c INNER JOIN schedule_per_course sc ON c.id = sc.courseid ORDER BY c.id;";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()){
                System.out.println("Course ID: " + rs.getInt(1) + ", Subject ID: " + rs.getInt(2) + ", Subject: " + rs.getString(3) + ", Date: " + rs.getDate(4));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
        }
    }

    public static void updateStudentPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE students_per_course SET courseid = ? WHERE courseid = ? && userid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(2));          
            pstm.setInt(2, ids.get(1));
            pstm.setInt(3, ids.get(0));
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Student per course successfully updated !");
        }
    }

    public static void updateTrainerPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE trainers_per_course SET courseid = ? WHERE courseid = ? && userid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(2));            
            pstm.setInt(2, ids.get(1));
            pstm.setInt(3, ids.get(0));
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Trainer per course successfully updated !");
        }
    }

    public static void updateAssignmentPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE assignments_per_course SET courseid = ? WHERE courseid = ? && assignmentid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(2));
            pstm.setInt(2, ids.get(1));
            pstm.setInt(3, ids.get(0));
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Assignment per course successfully updated !");
        }
    }

    public static void updateSchedulePerCourse(SchedulePerCourse spc) {
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE schedule_per_course SET subject = ?, date = ? WHERE courseid = ? && id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setString(1, spc.getSubject());
            pstm.setDate(2, Date.valueOf(spc.getDate()));            
            pstm.setInt(3, spc.getCourseId());
            pstm.setInt(4, spc.getId());
            pstm.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Schedule per course successfully updated !");
        }
    }

    public static void deleteStudentPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM students_per_course WHERE userid = ? && courseid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(0));
            pstm.setInt(1, ids.get(1));
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Student per course successfully deleted !");
        }
    }

    public static void deleteTrainerPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM trainers_per_course WHERE userid = ? && courseid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(0));
            pstm.setInt(1, ids.get(1));
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Trainer per course successfully deleted !");
        }
    }

    public static void deleteAssignmentPerCourse(ArrayList <Integer> ids) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM assignments_per_course WHERE assignmentid = ? && courseid = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, ids.get(0));
            pstm.setInt(1, ids.get(1));
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Assignment per course successfully deleted !");
        }
    }

    public static void deleteSchedulePerCourse(int id) {
        Connection con = DBUtils.getConnection();
        String sql = "DELETE FROM schedule_per_course WHERE id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement (sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtils.closeConnection(con);
            System.out.println("Schedule per course successfully deleted !");
        }
    }
}
