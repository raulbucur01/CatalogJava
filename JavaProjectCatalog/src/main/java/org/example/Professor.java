package org.example;

import java.util.ArrayList;
import java.util.List;

public class Professor {

    String username;
    ArrayList<String> associatedSubjects;

    public String getUsername() {
        return username;
    }

    public Professor(String username, ArrayList<String> associatedSubjects) {
        this.username = username;
        this.associatedSubjects = associatedSubjects;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getAssociatedSubjects() {
        return associatedSubjects;
    }


    public void setAssociatedSubjects(ArrayList<String> associatedSubjects) {
        this.associatedSubjects = associatedSubjects;
    }

    @Override
    public String toString() {
        return "\nUsername: " + username +
                " \nAssociatedSubjects: \n" + associatedSubjects;
    }

    public void addSubject(String subjectName){
        associatedSubjects.add(subjectName);
    }
}
