package com.university;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Student {

    private final String studentId;
    private final String studentName;
    private final Set<String> completedPrerequisites;
    private final Set<String> registeredCourses;
    private int totalCredits;

    public Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.completedPrerequisites = new HashSet<>();
        this.registeredCourses = new HashSet<>();
        this.totalCredits = 0;
    }

    private String normalizeCourseCode(String courseCode) {
        return courseCode == null
                ? null
                : courseCode.toUpperCase(Locale.ROOT);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Set<String> getCompletedPrerequisites() {
        return completedPrerequisites;
    }

    public Set<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void addCompletedPrerequisite(String courseCode) {
        completedPrerequisites.add(normalizeCourseCode(courseCode));
    }

    public boolean hasCompletedPrerequisite(String courseCode) {
        return completedPrerequisites.contains(
                normalizeCourseCode(courseCode));
    }

    public boolean isRegistered(String courseCode) {
        return registeredCourses.contains(
                normalizeCourseCode(courseCode));
    }

    public void addRegistration(String courseCode, int credits) {
        registeredCourses.add(normalizeCourseCode(courseCode));
        totalCredits += credits;
    }
}