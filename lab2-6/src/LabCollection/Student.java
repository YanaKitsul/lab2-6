package LabCollection;

import sun.plugin.dom.core.CoreConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import static java.time.temporal.ChronoUnit.DAYS;

public class Student {

    // поля
    private String bookNumber;
    private ArrayList<Course> courses = new ArrayList<>();

    // сеттери
    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    // геттери
    public String getBookNumber() {
        return bookNumber;
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // конструктор
    public Student(){}

    // методи
    public void addCourse(Course c){
        courses.add(c);
    }
    public ArrayList<Course> getCoursesOfState(Course.State state) {

        ArrayList<Course> res = new ArrayList<>();
        for (Course course:  courses) {
            if(course.getState() == state)
                res.add(course);
        }
        return res;
    }
    public ArrayList<Course> sortByStartDate(){
        ArrayList<Course> res = new ArrayList<>();

        for (Course c: courses){
            res.add(c);
        }

        res.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        return res;
    }
    public long getCourseDuration(String courseTitle){
        long res = 0;
        for(Course c: courses){
            if (c.getTitle() == courseTitle) {
                res = DAYS.between(c.getStartDate(), c.getEndDate());
            }
        }
        return res;
    }
    public long getDaysToEnd(String courseTitle){
        long res = 0;
        for(Course c: courses){
            if (c.getTitle() == courseTitle) {
                res = DAYS.between(LocalDate.now(), c.getEndDate());
            }
        }
        return res;
    }

    // перевизначені методи
    @Override
    public String toString() {
        String res = "Номер заліковки: " + bookNumber +
                "\nКурси: " + "\n";
        for (Course c : courses){
            res += c.toString();
        }
        return res;
    }

    @Override
    public boolean equals(Object obj){
        return  (bookNumber.equals(((Student) obj).bookNumber));
    }
}





