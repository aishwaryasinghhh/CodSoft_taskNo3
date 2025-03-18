/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentcourseregistration;

/**
 *
 * @author aishwaryasingh
 */
import java.util.*;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {  // **Fixed: Added this method**
        return title;
    }

    public boolean hasAvailableSlot() {
        return enrolledStudents < capacity;
    }

    public void registerStudent() {
        if (hasAvailableSlot()) {
            enrolledStudents++;
            System.out.println("Registered successfully for " + title);
        } else {
            System.out.println("Course " + title + " is full.");
        }
    }

    public void dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            System.out.println("Dropped from " + title);
        } else {
            System.out.println("No students enrolled in " + title);
        }
    }

    public void displayCourseDetails() {
        System.out.println(courseCode + " - " + title + " | " + description + " | Slots: " + (capacity - enrolledStudents) + "/" + capacity);
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.hasAvailableSlot()) {
            registeredCourses.add(course);
            course.registerStudent();
        } else {
            System.out.println("Already registered or course is full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
        } else {
            System.out.println("Not registered for this course.");
        }
    }

    public void showRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            System.out.println("Registered Courses:");
            for (Course c : registeredCourses) {
                System.out.println("- " + c.getCourseCode() + ": " + c.getTitle());
            }
        }
    }
}

class CourseRegistrationSystem {
    private List<Course> courses;
    private Map<String, Student> students;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
        this.students = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void addCourse(String code, String title, String desc, int capacity) {
        courses.add(new Course(code, title, desc, capacity));
    }

    public void addStudent(String id, String name) {
        students.put(id, new Student(id, name));
    }

    public Course findCourse(String code) {
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    public Student findStudent(String id) {
        return students.get(id);
    }

    public void showAvailableCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course c : courses) {
            c.displayCourseDetails();
        }
    }

    public void start() {
        while (true) {
            System.out.println("\nCourse Registration System");
            System.out.println("1. Register Course");
            System.out.println("2. Drop Course");
            System.out.println("3. View Registered Courses");
            System.out.println("4. View Available Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentID = scanner.nextLine();
                    Student student = findStudent(studentID);
                    if (student != null) {
                        showAvailableCourses();
                        System.out.print("Enter Course Code to Register: ");
                        String courseCode = scanner.nextLine();
                        Course course = findCourse(courseCode);
                        if (course != null) {
                            student.registerCourse(course);
                        } else {
                            System.out.println("Invalid course code.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextLine();
                    student = findStudent(studentID);
                    if (student != null) {
                        student.showRegisteredCourses();
                        System.out.print("Enter Course Code to Drop: ");
                        String dropCourseCode = scanner.nextLine();
                        Course dropCourse = findCourse(dropCourseCode);
                        if (dropCourse != null) {
                            student.dropCourse(dropCourse);
                        } else {
                            System.out.println("Invalid course code.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextLine();
                    student = findStudent(studentID);
                    if (student != null) {
                        student.showRegisteredCourses();
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    showAvailableCourses();
                    break;

                case 5:
                    System.out.println("Exiting System.");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

public class StudentCourseRegistration {
    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        
        system.addCourse("CS101", "Java Programming", "Learn Java from basics to advanced.", 3);
        system.addCourse("CS102", "Data Structures", "Explore fundamental data structures.", 2);
        system.addCourse("CS103", "Web Development", "Introduction to HTML, CSS, and JavaScript.", 3);

        system.addStudent("S001", "Aishwarya Singh");
        system.addStudent("S002", "Rahul Sharma");

        system.start();
    }
}
