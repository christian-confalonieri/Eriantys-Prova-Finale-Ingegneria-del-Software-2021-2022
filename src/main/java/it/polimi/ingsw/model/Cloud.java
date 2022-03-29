package it.polimi.ingsw.model;

import java.util.List;

public class Cloud{

    private List<Student> students;

    public void addStudent(Student student) {
        // The student received as input is added to the list of students.
        this.students.add(student);
    }

    /**
     * Every student on the cloud is moved to another location.
     *
     * @param destination the destination of the students
     *
     */
    public List<Student> pickAllStudents() {

    }

}
