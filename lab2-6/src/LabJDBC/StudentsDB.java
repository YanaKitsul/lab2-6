package LabJDBC;

import LabXmlJson.Course;
import LabXmlJson.Course.State;
import LabXmlJson.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class StudentsDB {

    private static MySqlManager sql;

    public static void initialize() {

        sql = new MySqlManager();
        sql.connectDatabase();
    }

    public static void createCourseTable() {

        String query = "CREATE TABLE course(" +
                "course_id INT AUTO_INCREMENT," +
                "title VARCHAR(20)," +
                "startDate DATE," +
                "endDate DATE," +
                "state VARCHAR(20)," +
                "PRIMARY KEY(course_id));";
        sql.executeUpdate(query);
    }

    public static void createStudentTable() {

        String query = "CREATE TABLE student(" +
                "st_id INT AUTO_INCREMENT," +
                "book_num VARCHAR(10)," +
                "PRIMARY KEY(st_id));";
        sql.executeUpdate(query);
    }

    public static void createCourseStudentTable() {

        String query = "CREATE TABLE CourseStudent(" +
                "id INT AUTO_INCREMENT," +
                "id_student INT," +
                "id_course INT," +
                "FOREIGN KEY(id_student) REFERENCES student(st_id)," +
                "FOREIGN KEY(id_course) REFERENCES course(course_id)," +
                "PRIMARY KEY(id));";

        sql.executeUpdate(query);
    }

    public static void addCourse(Course c) {

        String query = "INSERT INTO course(title, startDate, endDate, state) " +
                "VALUES (?,?,?,?);";
        PreparedStatement prepStmt = sql.createPreparedStatement(query);
        try {
            prepStmt.setString(1, c.getTitle());
            prepStmt.setDate(2, java.sql.Date.valueOf(c.getStartDate()));
            prepStmt.setDate(3,java.sql.Date.valueOf(c.getEndDate()));
            prepStmt.setString(4, c.getState().toString());
            prepStmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void addStudent(Student s) {

        String query = "INSERT INTO student(book_num) " +
                "VALUES (?);";
        PreparedStatement prepStmt = sql.createPreparedStatement(query);
        try {
            prepStmt.setString(1, s.getBookNumber());
            prepStmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void addCourseStudent(Student s) {

        String tmp ="SELECT st_id FROM student WHERE book_num='" + s.getBookNumber() + "';";
        ResultSet res = sql.executeQuery(tmp);
        int stId = 0;
        try{
            while (res.next()){
                stId = res.getInt("st_id");
            }
        } catch (SQLException e){ }


        int courseId = 0;
         for (Course c : s.getCourses()){
             ResultSet res1 = sql.executeQuery("SELECT course_id FROM course WHERE title='" + c.getTitle() + "';");

             try{
                 while (res1.next()){
                     courseId = res1.getInt("course_id");
                 }
             } catch (SQLException e){ }


             String query = "INSERT INTO coursestudent(id_student, id_course) " +
                     "VALUES (?,?);";
             PreparedStatement prepStmt = sql.createPreparedStatement(query);
             try {
                 prepStmt.setInt(1, stId);
                 prepStmt.setInt(2, courseId);
                 prepStmt.executeUpdate();
             } catch (SQLException e){
                 System.out.println(e);
             }

         }
    }

    public static ArrayList<Course> getCoursesOfState(LabStreams.Course.State state) {

        ArrayList<Course> res = new ArrayList<>();
        ResultSet res1 = sql.executeQuery("SELECT title, startDate, endDate, state FROM course WHERE state='" + state.toString() + "';");

        try{
            while (res1.next()){
                Course course = new Course.Builder().
                        setTitle(res1.getString("title")).
                        setStartDate(LocalDate.parse(res1.getString("startDate"))).
                        setEndDate(LocalDate.parse(res1.getString("endDate"))).
                        setState(State.valueOf( res1.getString("state"))).createCourse();
               res.add(course);
            }
        } catch (SQLException e){ }
        return res;
    }

    public static ArrayList<Course> sortByStartDate() {

        ArrayList<Course> res = new ArrayList<>();

        ResultSet res1 = sql.executeQuery("SELECT title, startDate, endDate, state FROM course ORDER BY startDate;");

        try{
            while (res1.next()){
                Course course = new Course.Builder().
                        setTitle(res1.getString("title")).
                        setStartDate(LocalDate.parse(res1.getString("startDate"))).
                        setEndDate(LocalDate.parse(res1.getString("endDate"))).
                        setState(State.valueOf( res1.getString("state"))).createCourse();
                res.add(course);
            }
        } catch (SQLException e){ }
        return res;
    }

    public static long getCourseDuration(String courseTitle) {

        String query = "SELECT DATEDIFF(endDate, startDate) AS duration " +
                "FROM course WHERE title = '" + courseTitle + "';";

        ResultSet res = sql.executeQuery(query);
        int duration = 0;
        try {
            while (res.next()) {

                duration = res.getInt("duration");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return duration;
        }
    }

    public static long getDaysToEnd(String courseTitle) {

        String query = "SELECT DATEDIFF(endDate, CURDATE()) AS days " +
                "FROM course WHERE title = '" + courseTitle + "';";

        ResultSet res = sql.executeQuery(query);
        int days = 0;
        try {
            while (res.next()) {

                days = res.getInt("days");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return days;
        }
    }
}
