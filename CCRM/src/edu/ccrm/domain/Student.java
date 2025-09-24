package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private final String regNo;
    private boolean active; 
    private final LocalDate enrollmentDate;
    private final List<Course> enrolledCourses = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();

    public Student(String id, String fullName, String email, String regNo) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.active = true; 
        this.enrollmentDate = LocalDate.now();
    }

    public String getRegNo() { return regNo; }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    public void activate() { active = true; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public List<Course> getEnrolledCourses() { return enrolledCourses; }

    public void enrollCourse(Course c) { enrolledCourses.add(c); }

    public void addEnrollment(Enrollment e) { enrollments.add(e); }
    public List<Enrollment> getEnrollments() { return new ArrayList<>(enrollments); }

    @Override
    public void printProfile() {
        System.out.println("Student: " + fullName + " | RegNo: " + regNo + " | Active: " + active);
    }

    @Override
    public String toString() {
        return "Student{id='" + id + "', regNo='" + regNo + "', name='" + fullName + "', active=" + active + "}";
    }
}
