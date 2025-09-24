package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;

public class EnrollmentService {
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final int MAX_CREDITS_PER_SEMESTER = 18;

    // Enroll student in a course
    public void enroll(Student student, Course course) {
        // Already enrolled?
        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getCourse().equals(course)) {
                System.out.println("Already enrolled!");
                return;
            }
        }

        // Max credits check
        int currentCredits = enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            System.out.println("Cannot enroll! Max credits per semester exceeded.");
            return;
        }

        Enrollment newEnrollment = new Enrollment(student, course);
        enrollments.add(newEnrollment);
        student.addEnrollment(newEnrollment);
        student.enrollCourse(course);

        System.out.println("Enrollment successful!");
    }

    // ---------- Unenroll feature ----------
    public void unenroll(Student student, Course course) {
        Optional<Enrollment> opt = enrollments.stream()
                .filter(e -> e.getStudent().equals(student) && e.getCourse().equals(course))
                .findFirst();

        if (opt.isPresent()) {
            enrollments.remove(opt.get());
            student.getEnrollments().remove(opt.get());
            student.getEnrolledCourses().remove(course);
            System.out.println("Unenrolled successfully from " + course.getTitle());
        } else {
            System.out.println("Enrollment not found!");
        }
    }

    // Assign grade
    public void assignGrade(Student student, Course course, Grade grade) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getCourse().equals(course)) {
                e.setGrade(grade);
                System.out.println("Grade assigned!");
                return;
            }
        }
        System.out.println("Enrollment not found!");
    }

    // List all enrollments
    public void listEnrollments() {
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments yet.");
        } else {
            enrollments.forEach(System.out::println);
        }
    }

    // Calculate GPA
    public double calculateGPA(Student student) {
        int totalCredits = 0;
        int weightedPoints = 0;

        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getGrade() != null) {
                totalCredits += e.getCourse().getCredits();
                weightedPoints += e.getCourse().getCredits() * e.getGrade().getPoints();
            }
        }

        if (totalCredits == 0) return 0.0;
        return (double) weightedPoints / totalCredits;
    }

    // Getter for export
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
}
