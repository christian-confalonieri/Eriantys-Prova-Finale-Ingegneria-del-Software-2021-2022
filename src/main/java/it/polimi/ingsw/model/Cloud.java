package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.EmptyCloudException;

import java.util.ArrayList;
import java.util.List;

public class Cloud{

    private List<Student> students;

    /**
     * Adds a student to the cloud.
     *
     * @param student the student to be added
     */
    public void addStudent(Student student) { students.add(student); }

    /**
     * Returns a list containing all students on the cloud and empties it.
     *
     * @return the list containing all students on the cloud
     *
     */
    public List<Student> pickAllStudents() throws EmptyCloudException {

        if(!students.isEmpty()) {
            List<Student> allStudents = new ArrayList<>();
            allStudents.addAll(students);
            students.clear();
            return allStudents;
        }
        else {
            throw new EmptyCloudException();
        }

    }

}
