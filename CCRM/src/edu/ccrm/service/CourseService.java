package edu.ccrm.service;

import edu.ccrm.domain.Course;
import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private final Map<String, Course> coursesByCode = new HashMap<>();

    public void addCourse(Course c) {
        coursesByCode.put(c.getCode(), c);
    }

    public void listCourses() {
        if (coursesByCode.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            coursesByCode.values().forEach(System.out::println);
        }
    }

    public Optional<Course> findByCode(String code) {
        return Optional.ofNullable(coursesByCode.get(code));
    }
    
    public List<Course> searchByInstructor(String instructorName) {
        return coursesByCode.values().stream()
                .filter(c -> c.getInstructor().equalsIgnoreCase(instructorName))
                .collect(Collectors.toList());
    }

    public List<Course> filterByDepartment(String department) {
        return coursesByCode.values().stream()
                .filter(c -> {
                    try {
                        return c.getClass().getMethod("getDepartment").invoke(c)
                                .toString().equalsIgnoreCase(department);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }


    public List<Course> filterBySemester(String semester) {
        return coursesByCode.values().stream()
                .filter(c -> {
                    try {
                        return c.getClass().getMethod("getSemester").invoke(c)
                                .toString().equalsIgnoreCase(semester);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(coursesByCode.values());
    }
}
