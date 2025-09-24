package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();
        ExportService exportService = new ExportService();
        BackupService backupService = new BackupService();
        ImportService importService = new ImportService();

        menuLoop:
        while (true) {
            System.out.println("\n===== CCRM MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Deactivate Student");
            System.out.println("4. Add Course");
            System.out.println("5. List Courses");
            System.out.println("6. Deactivate Course");
            System.out.println("7. Enroll Student in Course");
            System.out.println("8. Unenroll Student from Course");
            System.out.println("9. List Enrollments");
            System.out.println("10. Assign Grade");
            System.out.println("11. Show GPA");
            System.out.println("12. Print Transcript");
            System.out.println("13. Search/Filter Courses");
            System.out.println("14. Import CSV");
            System.out.println("15. Export Data");
            System.out.println("16. Backup Data");
            System.out.println("17. Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter ID: "); String id = sc.nextLine();
                    System.out.print("Enter Name: "); String name = sc.nextLine();
                    System.out.print("Enter Email: "); String email = sc.nextLine();
                    System.out.print("Enter RegNo: "); String regNo = sc.nextLine();
                    Student s = new Student(id, name, email, regNo);
                    studentService.addStudent(s);
                    System.out.println("Student added.");
                    continue menuLoop;
                }
                case "2" -> { studentService.listStudents(); continue menuLoop; }
                case "3" -> {
                    System.out.print("Enter Student ID to deactivate: ");
                    String sid = sc.nextLine();
                    studentService.findById(sid).ifPresentOrElse(
                        Student::deactivate,
                        () -> System.out.println("Student not found!")
                    );
                    continue menuLoop;
                }
                case "4" -> {
                    System.out.print("Enter Course Code: "); String code = sc.nextLine();
                    System.out.print("Enter Title: "); String title = sc.nextLine();
                    System.out.print("Enter Credits: "); int credits = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Instructor: "); String instructor = sc.nextLine();
                    System.out.print("Enter Department: "); String dept = sc.nextLine();
                    System.out.print("Enter Semester: "); String sem = sc.nextLine();
                    Course c = new Course(code, title, credits, instructor, dept, sem);
                    courseService.addCourse(c);
                    System.out.println("Course added.");
                    continue menuLoop;
                }
                case "5" -> { courseService.listCourses(); continue menuLoop; }
                case "6" -> {
                    System.out.print("Enter Course Code to deactivate: ");
                    String code = sc.nextLine();
                    courseService.findByCode(code).ifPresentOrElse(
                        Course::deactivate,
                        () -> System.out.println("Course not found!")
                    );
                    continue menuLoop;
                }
                case "7" -> {
                    System.out.print("Enter Student ID: "); String sid = sc.nextLine();
                    System.out.print("Enter Course Code: "); String code = sc.nextLine();
                    studentService.findById(sid).ifPresentOrElse(
                        student -> courseService.findByCode(code).ifPresentOrElse(
                            course -> enrollmentService.enroll(student, course),
                            () -> System.out.println("Course not found!")
                        ),
                        () -> System.out.println("Student not found!")
                    );
                    continue menuLoop;
                }
                case "8" -> {
                    System.out.print("Enter Student ID: "); String sid = sc.nextLine();
                    System.out.print("Enter Course Code: "); String code = sc.nextLine();
                    studentService.findById(sid).ifPresentOrElse(
                        student -> courseService.findByCode(code).ifPresentOrElse(
                            course -> enrollmentService.unenroll(student, course),
                            () -> System.out.println("Course not found!")
                        ),
                        () -> System.out.println("Student not found!")
                    );
                    continue menuLoop;
                }
                case "9" -> { enrollmentService.listEnrollments(); continue menuLoop; }
                case "10" -> {
                    System.out.print("Enter Student ID: "); String sid = sc.nextLine();
                    System.out.print("Enter Course Code: "); String code = sc.nextLine();
                    System.out.print("Enter Grade (S/A/B/C/D/F): "); String g = sc.nextLine();
                    studentService.findById(sid).ifPresentOrElse(
                        student -> courseService.findByCode(code).ifPresentOrElse(
                            course -> enrollmentService.assignGrade(student, course, Grade.valueOf(g)),
                            () -> System.out.println("Course not found!")
                        ),
                        () -> System.out.println("Student not found!")
                    );
                    continue menuLoop;
                }
                case "11" -> {
                    System.out.print("Enter Student ID: "); String sid = sc.nextLine();
                    studentService.findById(sid).ifPresentOrElse(
                        student -> System.out.println("GPA: " + enrollmentService.calculateGPA(student)),
                        () -> System.out.println("Student not found!")
                    );
                    continue menuLoop;
                }
                case "12" -> {
                    System.out.print("Enter Student ID: "); String sid = sc.nextLine();
                    studentService.printTranscript(sid);
                    continue menuLoop;
                }
                case "13" -> {
                    System.out.print("Enter instructor name to search: "); String instr = sc.nextLine();
                    var filtered = courseService.searchByInstructor(instr);
                    System.out.println("Courses found:");
                    filtered.forEach(System.out::println);
                    continue menuLoop;
                }
                case "14" -> {
                    try {
                        System.out.print("Enter Students CSV file path: "); String studentFile = sc.nextLine();
                        importService.importStudents(studentFile, studentService);
                        System.out.print("Enter Courses CSV file path: "); String courseFile = sc.nextLine();
                        importService.importCourses(courseFile, courseService);
                        System.out.print("Enter Enrollments CSV file path: "); String enrollFile = sc.nextLine();
                        importService.importEnrollments(enrollFile, studentService, courseService, enrollmentService);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    continue menuLoop;
                }
                case "15" -> {
                    try {
                        exportService.exportData(
                            "export.txt",
                            studentService.getAllStudents(),
                            courseService.getAllCourses(),
                            enrollmentService.getAllEnrollments()
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    continue menuLoop;
                }
                case "16" -> {
                    try { backupService.backup("exports"); } catch (Exception e) { e.printStackTrace(); }
                    continue menuLoop;
                }
                case "17" -> { System.out.println("Exiting..."); break menuLoop; }
                default -> { System.out.println("Invalid choice."); continue menuLoop; }
            }
        }

        sc.close();
    }
}
