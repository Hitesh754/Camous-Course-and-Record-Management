package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;
import java.util.*;

public class StudentService {
    private final Map<String, Student> studentsById = new HashMap<>();

    public void addStudent(Student s) {
        studentsById.put(s.getId(), s);
    }

    public Optional<Student> findById(String id) {
        return Optional.ofNullable(studentsById.get(id));
    }

    public void listStudents() {
        studentsById.values().forEach(System.out::println);
    }

    // ---------- printTranscript (fixed) ----------
    public void printTranscript(String studentId) {
        Optional<Student> optStudent = findById(studentId);
        if (optStudent.isEmpty()) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }

        Student student = optStudent.get();
        System.out.println("Transcript for " + student.getFullName() + " (RegNo: " + student.getRegNo() + ")");
        List<Enrollment> enrollments = student.getEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No courses enrolled yet.");
        } else {
            enrollments.forEach(enrollment -> {
                String courseTitle = enrollment.getCourse().getTitle();
                String grade = enrollment.getGrade() != null ? enrollment.getGrade().toString() : "N/A";
                System.out.println(courseTitle + " - Grade: " + grade);
            });
        }
        System.out.println("-----------------------------");
    }

    // ---------- Getter for Export / MainApp ----------
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentsById.values());
    }
}
