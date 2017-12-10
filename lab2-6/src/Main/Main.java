package Main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import LabJDBC.StudentsDB;
import LabXmlJson.Course;
import LabXmlJson.Course.State;
import LabXmlJson.Student;

import LabXmlJson.FastJSON;
import LabXmlJson.JAXB;
import LabXmlJson.XmlJson;

public class Main {

    public static void main(String[] args) {

        Course course1 = new Course.Builder().createCourse();
        course1.setTitle("Physics");
        course1.setStartDate(LocalDate.of(2017, 4, 12));
        course1.setEndDate(LocalDate.of(2017, 6, 1));
        course1.setState(State.PASSED);

        Course course2 = new Course.Builder().
                setTitle("Math").
                setStartDate(LocalDate.of(2016, 3, 20)).
                setEndDate(LocalDate.of(2017, 5, 19)).
                setState(State.FAILED).createCourse();

        Course course3 = new Course.Builder().
                setTitle("Chemistry").
                setStartDate(LocalDate.of(2019, 7, 16)).
                setEndDate(LocalDate.of(2019, 10, 18)).
                setState(State.NEW).createCourse();

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        Student st = new Student();
        st.setBookNumber("65673647");
        st.setCourses(courses);

        //System.out.println(st);

        //System.out.println(st.getCoursesOfState(State.PASSED));
        //System.out.println("\n" + st.getCourseDuration("Chemistry"));
        //System.out.println("\n" + st.sortByStartDate());
        //System.out.println("\n" + st.getDaysToEnd("Chemistry"));

        /*
        File fileXml = new File("student.xml");
        try {
            fileXml.createNewFile();
        } catch (IOException e){
            System.out.println("Cannot create file.");
        }

        XmlJson xml = new JAXB();
        xml.serialize(st, fileXml);

        Student stud1 = new Student();
        stud1 = (Student) xml.deserialize(stud1.getClass(), fileXml);
        System.out.println(stud1);
        */

        //--------------------------------------------

//        File fileJson = new File("student.json");
//        try {
//            fileJson.createNewFile();
//        } catch (IOException e){
//            System.out.println("Cannot create file.");
//        }
//
//        XmlJson json = new FastJSON();
//        json.serialize(st, fileJson);
//
//        Student stud2 = new Student();
//        stud2 = (Student) json.deserialize(stud2.getClass(), fileJson);
//        System.out.println(stud2);

        StudentsDB.initialize();
//        StudentsDB.createCourseTable();
//        StudentsDB.createStudentTable();
//        StudentsDB.createCourseStudentTable();
//
//        for(Course c: st.getCourses()) {
//            StudentsDB.addCourse(c);
//        }
//
//        StudentsDB.addStudent(st);
//        StudentsDB.addCourseStudent(st);

        ArrayList<Course> cs = StudentsDB.sortByStartDate();
            for (Course c : cs) {
                System.out.println(c);
            }


    }
}
