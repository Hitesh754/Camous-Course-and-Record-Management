package edu.ccrm.io;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class ExportService {

    
    public void exportData(String fileName, List<Student> students, List<Course> courses, List<Enrollment> enrollments) throws IOException {
        Path path = Paths.get("exports/" + fileName);
        Files.createDirectories(path.getParent());

        StringBuilder sb = new StringBuilder();

        sb.append("=== Courses ===\n");
        for (Course c : courses) {
            sb.append(c.getCode()).append(",")
              .append(c.getTitle()).append(",")
              .append(c.getCredits()).append(",")
              .append(c.getInstructor()).append(",")
              .append(c.isActive()).append("\n");
        }

        sb.append("\n=== Students ===\n");
        for (Student s : students) {
            sb.append(s.getId()).append(",")
              .append(s.getFullName()).append(",")
              .append(s.getRegNo()).append(",")
              .append(s.isActive()).append("\n");
        }

        sb.append("\n=== Enrollments ===\n");
        for (Enrollment e : enrollments) {
            sb.append(e.getStudent().getId()).append(",")
              .append(e.getCourse().getCode()).append(",")
              .append(e.getGrade() != null ? e.getGrade().toString() : "N/A")
              .append("\n");
        }

        Files.write(path, sb.toString().getBytes());
        System.out.println("Data exported to " + path.toAbsolutePath());
    }

    
    public void importStudentsFromCSV(Path filePath, List<Student> students) throws IOException {
        if (!Files.exists(filePath)) {
            System.out.println("CSV file not found: " + filePath);
            return;
        }
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String id = parts[0].trim();
                String fullName = parts[1].trim();
                String regNo = parts[2].trim();
                students.add(new Student(id, fullName, id + "@school.com", regNo)); 
            }
        }
        System.out.println("Imported " + students.size() + " students from CSV.");
    }

    
    public void importCoursesFromCSV(Path filePath, List<Course> courses) throws IOException {
        if (!Files.exists(filePath)) {
            System.out.println("CSV file not found: " + filePath);
            return;
        }
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String code = parts[0].trim();
                String title = parts[1].trim();
                int credits = Integer.parseInt(parts[2].trim());
                String instructor = parts[3].trim();
                String department = parts.length >= 5 ? parts[4].trim() : "General";
                String semester   = parts.length >= 6 ? parts[5].trim() : "Fall";
                courses.add(new Course(code, title, credits, instructor, department, semester));
            }
        }
        System.out.println("Imported " + courses.size() + " courses from CSV.");
    }
}
