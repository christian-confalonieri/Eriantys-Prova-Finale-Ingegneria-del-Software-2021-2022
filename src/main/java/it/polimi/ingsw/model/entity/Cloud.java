package it.polimi.ingsw.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Confalonieri
 */
public class Cloud extends Entity {

    private final List<Student> students;


    public Cloud() {
        super();
        this.students = new ArrayList<>();
    }

    /**
     * Adds a student to the cloud.
     *
     * @param student the student to be added
     * @author Christian Confalonieri
     */
    public void addStudent(Student student) { students.add(student); }

    /**
     * Returns a list containing all students on the cloud and empties it.
     *
     * @return the list containing all students on the cloud
     * @author Christian Confalonieri
     */
    public List<Student> pickAllStudents() {
            List<Student> allStudents = new ArrayList<>();
            allStudents.addAll(students);
            students.clear();
            return allStudents;
    }

    public List<Student> getStudents() {
        return students;
    }

}
