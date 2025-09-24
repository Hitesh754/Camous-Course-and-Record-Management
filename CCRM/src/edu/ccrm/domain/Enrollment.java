package edu.ccrm.domain;

public class Enrollment {
    private Student student;
    private Course course;
    private Grade grade;  
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Grade getGrade() { return grade; }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return student.getFullName() + " enrolled in " + course.getTitle() +
               (grade != null ? " | Grade: " + grade : " | Grade: Not Assigned");
    }
}
