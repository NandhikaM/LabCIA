package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {
        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301", "Data Structures", 4, 30, true, null
        );

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectInactiveCourse() {
        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301", "Data Structures", 4, 30, false, null
        );

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {
        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401", "Advanced Algorithms", 4, 30, true, "CS301"
        );

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {
        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401", "Advanced Algorithms", 4, 30, true, "CS301"
        );

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {
        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301", "Data Structures", 4, 30, true, null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {
        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits.
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301", "Data Structures", 4, 30, true, null
        );

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
        assertEquals(20, student.getTotalCredits());
        assertEquals(0, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCourseIsFull() {
        Student firstStudent = new Student("S101", "Anita");
        Student secondStudent = new Student("S102", "Rahul");

        Course course = new Course(
                "CS301", "Data Structures", 4, 1, true, null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(firstStudent, course));
        assertFalse(service.register(secondStudent, course));

        assertFalse(secondStudent.isRegistered("CS301"));
        assertEquals(0, secondStudent.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
        assertEquals(4, firstStudent.getTotalCredits());
    }

    @Test
    void shouldRejectDuplicateRegistrationWithDifferentCase() {
        Student student = new Student("S103", "Priya");
        RegistrationService service = new RegistrationService();

        Course firstCourse = new Course(
                "CS301", "Data Structures", 4, 30, true, null
        );

        Course secondCourse = new Course(
                "cs301", "Data Structures", 4, 30, true, null
        );

        assertTrue(service.register(student, firstCourse));
        assertFalse(service.register(student, secondCourse));

        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, firstCourse.getCurrentEnrollment());
        assertEquals(0, secondCourse.getCurrentEnrollment());
    }

    @Test
    void shouldAllowRegistrationWhenExactlyOneSeatRemains() {
        Student student = new Student("S104", "Meera");

        Course course = new Course(
                "CS302", "Operating Systems", 4, 2, true, null
        );

        // One of the two available seats is already occupied.
        course.incrementEnrollment();

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS302"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(2, course.getCurrentEnrollment());
    }
}