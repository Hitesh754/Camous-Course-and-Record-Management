package edu.ccrm.domain;

import java.util.Objects;

public class Course {
    private final String code;     
    private String title;
    private int credits;
    private String instructor;
    private String department;
    private String semester;
    private boolean active;

    public Course(String code, String title, int credits, String instructor, String department, String semester) {
        if (code == null || code.isEmpty()) throw new IllegalArgumentException("Course code cannot be null/empty");
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Course title cannot be null/empty");
        if (credits <= 0) throw new IllegalArgumentException("Credits must be positive");
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor != null ? instructor : "TBD";
        this.department = department != null ? department : "General";
        this.semester = semester != null ? semester : "Unknown";
        this.active = true; 
    }

    
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public String getDepartment() { return department; }
    public String getSemester() { return semester; }
    public boolean isActive() { return active; }

    
    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setDepartment(String department) { this.department = department; }
    public void setSemester(String semester) { this.semester = semester; }

    
    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }

    @Override
    public String toString() {
        return String.format("%s - %s (%dcr) [%s] Dept: %s | Sem: %s | Active: %b",
                code, title, credits, instructor, department, semester, active);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return code.equals(course.code); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
