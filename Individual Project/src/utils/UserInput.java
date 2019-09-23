/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.HeadMasterDAO;
import dao.StudentDAO;
import dao.TrainerDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import model.Assignments;
import model.AssignmentsPerStudentPerCourse;
import model.Courses;
import model.SchedulePerCourse;
import model.Users;

/**
 *
 * @author Panos
 */
public class UserInput {

    public static Boolean run = true;
    public static int userChoice;
    public static Base64.Encoder encoder = Base64.getEncoder();
    public static Base64.Decoder decoder = Base64.getDecoder();
    public static final String[] HEADMASTEROPTIONS = {"1: Create", "2: Read", "3: Update", "4: Delete", "5: End Program"};
    public static final String[] TABLES = {"1: Course(s)", "2: Student(s)", "3: Assignment(s)", "4: Trainer(s)",
        "5: Student(s) per Course", "6: Trainer(s) per Course", "7: Assignment(s) per Course", "8: Schedule per Course", "9: Go to Menu 1"};
    public static final String[] STUDENTOPTIONS = {"1: View your schedule per course", "2: View the dates of submission of the assignments per course", "3: Submit assignments", "4: End Program"};
    public static final String[] TRAINEROPTIONS = {"1: View your courses", "2: View students per course", "3: View assignments per student per course",
        "4: Mark assignments per student per course", "5: End Program"};

    public static void validateUser(ArrayList<String> userInfo) {
        // Check if passwords match
        while (!(userInfo.get(1).equals(userInfo.get(2)))) {
            System.out.println("Passwords do not match, try again...");
            userInfo = userLogin();
        }
        // Check if user exists in the database
        boolean userExists = false;
        ArrayList<Users> users = DBUtils.getUsersLoginInfo();
        do {
            for (Users user : users) {
                String decodedPassword = new String(decoder.decode(user.getPassword().getBytes()));
                if (user.getUsername().equals(userInfo.get(0)) && decodedPassword.equals(userInfo.get(1))) {
                    userExists = true;
                    // Show user specific menu
                    switch (user.getRoleId()) {
                        case 1:
                            showHeadMasterMenu();
                            break;
                        case 2:
                            System.out.println("Your user ID is: " + user.getId() + "!");
                            showStudentMenu();
                            break;
                        case 3:
                            System.out.println("Your user ID is: " + user.getId() + "!");
                            showTrainerMenu();
                            break;
                    }
                }
                if(userExists) {
                    run = false;
                    break;
                }
            }
            if (!userExists) {
                System.out.println("User does not exist in the database.");
                run = false;
            }
        } while (run);
    }

    public static ArrayList<String> userLogin() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> userInfo = new ArrayList();
        System.out.println("Hello, user! Log in to access your menu...");
        System.out.println("Give your username: ");
        userInfo.add(sc.nextLine());
        System.out.println("Now give your password: ");
        userInfo.add(sc.nextLine());
        System.out.println("Retype your password: ");
        userInfo.add(sc.nextLine());
        return userInfo;
    }

    public static void showHeadMasterMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Head Master Menu:\nType the specific number for each action...");
        do {
            System.out.println("-------- Menu 1 -------- Choose action: ");
            for (String action : HEADMASTEROPTIONS) {
                System.out.println(action + " ");
            }
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 5:
                    run = false;
                    break;
                case 1:
                    showHeadMasterCreateMenu("-------- Menu 2 -------- Choose what to create: ");
                    break;
                case 2:
                    showHeadMasterSelectMenu("-------- Menu 2 -------- Choose what to read: ");
                    break;
                case 3:
                    showHeadMasterUpdateMenu("-------- Menu 2 -------- Choose what to update: ");
                    break;
                case 4:
                    showHeadMasterDeleteMenu("-------- Menu 2 -------- Choose what to delete: ");
                    break;
                default:
                    System.out.println("Selected number not in valid range. Please retry.");
            }
        } while (run);
    }

    public static void showHeadMasterCreateMenu(String message) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(message);
            for (String table : TABLES) {
                System.out.println(table);
            }
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 9:
                    showHeadMasterMenu();
                    break;
                case 1:
                    HeadMasterDAO.insertCourse(getInsertCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 2:
                    HeadMasterDAO.insertStudent(getInsertStudentInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 3:
                    HeadMasterDAO.insertAssignment(getInsertAssignmentInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 4:
                    HeadMasterDAO.insertTrainer(getInsertTrainerInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 5:
                    HeadMasterDAO.insertStudentPerCourse(getInsertStudentPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 6:
                    HeadMasterDAO.insertTrainerPerCourse(getInsertTrainerPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 7:
                    HeadMasterDAO.insertAssignmentPerCourse(getInsertAssignmentPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 8:
                    HeadMasterDAO.insertSchedulePerCourse(getInsertSchedulePerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                default:
                    System.out.println("Selected number not in valid range. Please retry.");
            }
        } while (run);
    }

    public static void showHeadMasterSelectMenu(String message) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(message);
            for (String table : TABLES) {
                System.out.println(table);
            }
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 9:
                    showHeadMasterMenu();
                    break;
                case 1:
                    HeadMasterDAO.getCourses();
                    showHeadMasterMenu();
                    break;
                case 2:
                    HeadMasterDAO.getStudents();
                    showHeadMasterMenu();
                    break;
                case 3:
                    HeadMasterDAO.getAssignments();
                    showHeadMasterMenu();
                    break;
                case 4:
                    HeadMasterDAO.getTrainers();
                    showHeadMasterMenu();
                    break;
                case 5:
                    HeadMasterDAO.getStudentsPerCourse();
                    showHeadMasterMenu();
                    break;
                case 6:
                    HeadMasterDAO.getTrainersPerCourse();
                    showHeadMasterMenu();
                    break;
                case 7:
                    HeadMasterDAO.getAssignmentsPerCourse();
                    showHeadMasterMenu();
                    break;
                case 8:
                    HeadMasterDAO.getSchedulePerCourse();
                    showHeadMasterMenu();
                    break;
                default:
                    System.out.println("Selected number not in valid range. Please retry.");
            }
        } while (run);
    }

    public static void showHeadMasterUpdateMenu(String message) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(message);
            for (String table : TABLES) {
                System.out.println(table);
            }
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 9:
                    showHeadMasterMenu();
                    break;
                case 1:
                    HeadMasterDAO.updateCourse(getUpdateCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 2:
                    HeadMasterDAO.updateStudent(getUpdateStudentInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 3:
                    HeadMasterDAO.updateAssignment(getUpdateAssignmentInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 4:
                    HeadMasterDAO.updateTrainer(getUpdateTrainerInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 5:
                    HeadMasterDAO.updateStudentPerCourse(getUpdateStudentPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 6:
                    HeadMasterDAO.updateTrainerPerCourse(getUpdateTrainerPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 7:
                    HeadMasterDAO.updateAssignmentPerCourse(getUpdateAssignmentPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 8:
                    HeadMasterDAO.updateSchedulePerCourse(getUpdateSchedulePerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                default:
                    System.out.println("Selected number not in valid range. Please retry.");
            }
        } while (run);
    }

    public static void showHeadMasterDeleteMenu(String message) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(message);
            for (String table : TABLES) {
                System.out.println(table);
            }
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 9:
                    showHeadMasterMenu();
                    break;
                case 1:
                    HeadMasterDAO.deleteCourse(getDeleteCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 2:
                    HeadMasterDAO.deleteStudent(getDeleteStudentInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 3:
                    HeadMasterDAO.deleteAssignment(getDeleteAssignmentInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 4:
                    HeadMasterDAO.deleteTrainer(getDeleteTrainerInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 5:
                    HeadMasterDAO.deleteStudentPerCourse(getDeleteStudentPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 6:
                    HeadMasterDAO.deleteTrainerPerCourse(getDeleteTrainerPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 7:
                    HeadMasterDAO.deleteAssignmentPerCourse(getDeleteAssignmentPerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                case 8:
                    HeadMasterDAO.deleteSchedulePerCourse(getDeleteSchedulePerCourseInfoFromInput());
                    showHeadMasterMenu();
                    break;
                default:
                    System.out.println("Selected number not in valid range. Please retry.");
            }
        } while (run);
    }

    public static void showStudentMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Student menu:\nType the specific number for each action...");
        do {
            System.out.println("-------- Choose action --------");
            for (String action : STUDENTOPTIONS) {
                System.out.println(action + " ");
            }
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 4:
                    run = false;
                    break;
                case 1:
                    StudentDAO.getSchedulePerCourse(getStudentSchedulePerCourseIdFromInput());
                    break;
                case 2:
                    StudentDAO.getAssignmentsPerCourseSubmissionDate(getAssignmentsPerCourseSubmissionDateIdFromInput());
                    break;
                case 3:
                    StudentDAO.submitAssignment(getSubmitAssignmentInfoFromInput());
                    break;
                default:
                    System.out.println("Selected number not in valid range. Please retry.");
            }
        } while (run);
    }

    public static void showTrainerMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Trainer menu:\nType the specific number for each action...");
        do {
            System.out.println("-------- Choose action --------");
            for (String action : TRAINEROPTIONS) {
                System.out.println(action + " ");
            }
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 5:
                    run = false;
                    break;
                case 1:
                    TrainerDAO.getTrainerCourses(getTrainerCoursesIdFromInput());
                    break;
                case 2:
                    TrainerDAO.getStudentsPerCourse(getStudentsPerCourseIdFromInput());
                    break;
                case 3:
                    TrainerDAO.getAssignmentsPerStudentPerCourse(getAssignmentsPerStudentPerCourseIdFromInput());
                    break;
                case 4:
                    TrainerDAO.markAssignmentsPerStudentPerCourse(getMarkAssignmentsPerStudentPerCourseInfoFromInput());
                    break;
                default:
                    System.out.println("Selected number not in valid range. Please retry.");
            }
        } while (run);
    }

    public static Courses getInsertCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        String title, stream, type;
        LocalDate startDate, endDate;
        System.out.println("Give Course's Title:");
        title = sc.nextLine();
        System.out.println("Give Course's Stream:");
        stream = sc.nextLine();
        System.out.println("Give Course's Type:");
        type = sc.nextLine();
        System.out.println("Give Course's Start Date in this format: yyyy-MM-dd");
        startDate = LocalDate.parse(sc.nextLine());
        System.out.println("Give Course's End Date in this format: yyyy-MM-dd");
        endDate = LocalDate.parse(sc.nextLine());
        Courses course = new Courses(title, stream, type, startDate, endDate);
        return course;
    }

    public static Users getInsertStudentInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        String firstName, lastName, username, password;
        LocalDate birthDate;
        System.out.println("Give student's First Name:");
        firstName = sc.nextLine();
        System.out.println("Give student's Last Name:");
        lastName = sc.nextLine();
        System.out.println("Give student's Birth Date in this format: yyyy-MM-dd:");
        birthDate = LocalDate.parse(sc.nextLine());
        System.out.println("Give student's Username:");
        username = sc.nextLine();
        System.out.println("Give student's Password:");
        password = new String(encoder.encode(sc.nextLine().getBytes()));
        Users student = new Users(firstName, lastName, birthDate, username, password);
        return student;
    }

    private static Assignments getInsertAssignmentInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        String title, description;
        System.out.println("Give assignment's Title:");
        title = sc.nextLine();
        System.out.println("Give assignment's Description:");
        description = sc.nextLine();
        Assignments assignment = new Assignments(title, description);
        return assignment;
    }

    private static Users getInsertTrainerInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        String firstName, lastName, username, password;
        LocalDate birthDate;
        System.out.println("Give trainer's First Name:");
        firstName = sc.nextLine();
        System.out.println("Give trainer's Last Name:");
        lastName = sc.nextLine();
        System.out.println("Give trainer's Birth Date in this format: yyyy-MM-dd:");
        birthDate = LocalDate.parse(sc.nextLine());
        System.out.println("Give trainer's Username:");
        username = sc.nextLine();
        System.out.println("Give trainer's Password:");
        password = new String(encoder.encode(sc.nextLine().getBytes()));
        Users trainer = new Users(firstName, lastName, birthDate, username, password);
        return trainer;
    }

    private static Courses getUpdateCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        Courses course = new Courses();
        System.out.println("Give Course's Title:");
        course.setTitle(sc.nextLine());
        System.out.println("Give Course's Stream:");
        course.setStream(sc.nextLine());
        System.out.println("Give Course's Type:");
        course.setType(sc.nextLine());
        System.out.println("Give Course's Start Date in this format: yyyy-MM-dd");
        course.setStartDate(LocalDate.parse(sc.nextLine()));
        System.out.println("Give Course's End Date in this format: yyyy-MM-dd");
        course.setEndDate(LocalDate.parse(sc.nextLine()));
        System.out.println("Give Course's ID:");
        course.setId(sc.nextInt());
        return course;
    }

    private static Users getUpdateStudentInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        Users student = new Users();
        System.out.println("Give student's First Name:");
        student.setFirstName(sc.nextLine());
        System.out.println("Give student's Last Name:");
        student.setLastName(sc.nextLine());
        System.out.println("Give student's Birth Date in this format: yyyy-MM-dd:");
        student.setBirthDate(LocalDate.parse(sc.nextLine()));
        System.out.println("Give student's Username:");
        student.setUsername(sc.nextLine());
        System.out.println("Give student's Password:");
        student.setPassword(new String(encoder.encode(sc.nextLine().getBytes())));
        System.out.println("Give student's ID:");
        student.setId(sc.nextInt());
        return student;
    }

    private static Assignments getUpdateAssignmentInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        Assignments assignment = new Assignments();
        System.out.println("Give assignment's Title:");
        assignment.setTitle(sc.nextLine());
        System.out.println("Give assignment's Description:");
        assignment.setDescription(sc.nextLine());
        System.out.println("Give assignment's ID:");
        assignment.setId(sc.nextInt());
        return assignment;
    }

    private static Users getUpdateTrainerInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        Users trainer = new Users();
        System.out.println("Give trainer's First Name:");
        trainer.setFirstName(sc.nextLine());
        System.out.println("Give trainer's Last Name:");
        trainer.setLastName(sc.nextLine());
        System.out.println("Give trainer's Birth Date in this format: yyyy-MM-dd:");
        trainer.setBirthDate(LocalDate.parse(sc.nextLine()));
        System.out.println("Give trainer's Username:");
        trainer.setUsername(sc.nextLine());
        System.out.println("Give trainer's Password:");
        trainer.setPassword(new String(encoder.encode(sc.nextLine().getBytes())));
        System.out.println("Give trainer's ID:");
        trainer.setId(sc.nextInt());
        return trainer;
    }

    private static int getDeleteCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Give course's ID:");
        id = sc.nextInt();
        return id;
    }

    private static int getDeleteStudentInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Give student's ID:");
        id = sc.nextInt();
        return id;
    }

    private static int getDeleteAssignmentInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Give assignment's ID:");
        id = sc.nextInt();
        return id;
    }

    private static int getDeleteTrainerInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Give trainer's ID:");
        id = sc.nextInt();
        return id;
    }

    private static ArrayList<Integer> getInsertStudentPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose student to be assigned to course. Give student id:");
        ids.add(sc.nextInt());
        System.out.println("Choose course to be assigned to student. Give course id: ");
        ids.add(sc.nextInt());
        return ids;
    }

    private static ArrayList<Integer> getInsertTrainerPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose trainer to be assigned to course. Give trainer id:");
        ids.add(sc.nextInt());
        System.out.println("Choose course to be assigned to trainer. Give course id: ");
        ids.add(sc.nextInt());
        return ids;
    }

    private static ArrayList<Integer> getInsertAssignmentPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose assignment to be assigned to course. Give assignment id:");
        ids.add(sc.nextInt());
        System.out.println("Choose course to be assigned to assignment. Give course id: ");
        ids.add(sc.nextInt());
        return ids;
    }

    private static SchedulePerCourse getInsertSchedulePerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        SchedulePerCourse spc = new SchedulePerCourse();
        System.out.println("Enter subject to be assigned to course:");
        spc.setSubject(sc.nextLine());
        System.out.println("Enter subject's date in this format: yyyy-MM-dd:");
        spc.setDate(LocalDate.parse(sc.nextLine()));
        System.out.println("Choose course to be assigned to schedule. Give course id: ");
        spc.setCourseId(sc.nextInt());
        return spc;
    }

    private static ArrayList<Integer> getUpdateStudentPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose student to be updated. Give student's id:");
        ids.add(sc.nextInt());
        System.out.println("Give course's old id:");
        ids.add(sc.nextInt());
        System.out.println("Give course's new id:");
        ids.add(sc.nextInt());
        return ids;
    }

    private static ArrayList<Integer> getUpdateTrainerPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose trainer to be updated. Give trainer's id:");
        ids.add(sc.nextInt());
        System.out.println("Give course's old id:");
        ids.add(sc.nextInt());
        System.out.println("Give course's new id:");
        ids.add(sc.nextInt());
        return ids;
    }

    private static ArrayList<Integer> getUpdateAssignmentPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose assignment to be updated. Give assignment's id:");
        ids.add(sc.nextInt());
        System.out.println("Give course's old id:");
        ids.add(sc.nextInt());
        System.out.println("Give course's new id:");
        ids.add(sc.nextInt());
        return ids;
    }

    private static SchedulePerCourse getUpdateSchedulePerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        SchedulePerCourse spc = new SchedulePerCourse();
        System.out.println("Enter updated subject's title:");
        spc.setSubject(sc.nextLine());
        System.out.println("Enter updated subject's date in this format: yyyy-MM-dd:");
        spc.setDate(LocalDate.parse(sc.nextLine()));
        System.out.println("Choose course to be updated. Give course's id: ");
        spc.setCourseId(sc.nextInt());
        System.out.println("Choose subject to be updated. Give subject's id: ");
        spc.setId(sc.nextInt());
        return spc;
    }

    private static ArrayList<Integer> getDeleteStudentPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose student to be deleted. Give student's id:");
        ids.add(sc.nextInt());
        System.out.println("Choose course assigned to student. Give course's id: ");
        ids.add(sc.nextInt());
        return ids;
    }

    private static ArrayList<Integer> getDeleteTrainerPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose trainer to be deleted. Give trainer's id:");
        ids.add(sc.nextInt());
        System.out.println("Choose course assigned to trainer. Give course's id: ");
        ids.add(sc.nextInt());
        return ids;
    }

    private static ArrayList<Integer> getDeleteAssignmentPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Choose assignment to be deleted. Give assignment's id:");
        ids.add(sc.nextInt());
        System.out.println("Choose course assigned to assignment. Give course's id: ");
        ids.add(sc.nextInt());
        return ids;
    }

    private static int getDeleteSchedulePerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Choose schedule to be deleted. Give schedule's id:");
        id = sc.nextInt();
        return id;
    }

    private static int getStudentSchedulePerCourseIdFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Type your ID (printed above) to see your schedule. Give user id:");
        id = sc.nextInt();
        return id;
    }

    private static int getAssignmentsPerCourseSubmissionDateIdFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Type your ID (printed above) to see your assignments' submission date. Give user id:");
        id = sc.nextInt();
        return id;
    }

    private static ArrayList<Integer> getSubmitAssignmentInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList();
        System.out.println("Type assignment ID to be submitted: ");
        ids.add(sc.nextInt());
        System.out.println("Type your ID to submit the assignment. Give user id: ");
        ids.add(sc.nextInt());
        return ids;
    }

    private static int getTrainerCoursesIdFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Type your ID (printed above) to see your enrolled courses. Give user id:");
        id = sc.nextInt();
        return id;
    }

    private static int getStudentsPerCourseIdFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Type course ID to see enrolled students. Give course id:");
        id = sc.nextInt();
        return id;
    }

    private static AssignmentsPerStudentPerCourse getMarkAssignmentsPerStudentPerCourseInfoFromInput() {
        Scanner sc = new Scanner(System.in);
        AssignmentsPerStudentPerCourse assignment = new AssignmentsPerStudentPerCourse();
        System.out.println("Give assignment's id to be marked:");
        assignment.setId(sc.nextInt());
        System.out.println("Give student's id:");
        assignment.setStudentId(sc.nextInt());
        System.out.println("Give assignment's oral mark:");
        assignment.setOralMark(sc.nextDouble());
        System.out.println("Give assignment's total mark:");
        assignment.setTotalMark(sc.nextDouble());
        return assignment;
    }

    private static int getAssignmentsPerStudentPerCourseIdFromInput() {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Type course ID to see assignments per student. Give course id:");
        id = sc.nextInt();
        return id;
    }
}
