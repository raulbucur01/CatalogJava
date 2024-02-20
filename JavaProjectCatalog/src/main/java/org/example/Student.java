package org.example;
import java.util.*;

import org.example.Grade;

public class Student {
    String username;
    int age;
    HashMap<String, ArrayList<Grade>> subjectsAndGrades;

    public ArrayList<Grade> getGradesSortedByDate(String subject){
        ArrayList<Grade> grades = subjectsAndGrades.get(subject);

        grades.sort(Comparator.comparing(Grade::getDate).reversed());
        return grades;
    }
    public Double getAverage(String subject){
        ArrayList<Grade> grades = subjectsAndGrades.get(subject);

        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        OptionalDouble average = grades.stream().mapToDouble(Grade::getGrade).average();
        return average.orElse(0.0);
    }
    public void addGrade(String subject, Grade grade){
        ArrayList<Grade> grades = subjectsAndGrades.get(subject);
        grades.add(grade);
        subjectsAndGrades.put(subject, grades);
    }

    public void updateGrade(String subject, Grade new_grade, int index){
        ArrayList<Grade> grades = subjectsAndGrades.get(subject);
        if (index < grades.size() && index >= 0)
        {
            grades.set(index, new_grade);
        }
        subjectsAndGrades.put(subject, grades);
    }

    public void deleteGrade(String subject, int index) {
        ArrayList<Grade> grades = subjectsAndGrades.get(subject);
        if (index < grades.size() && index >= 0)
        {
            grades.remove(index);
        }
        subjectsAndGrades.put(subject,grades);
    }

    public ArrayList<Grade> getGradesForSubject(String subjectName){
        for (Map.Entry<String, ArrayList<Grade>> entry:
             subjectsAndGrades.entrySet()) {
            if (entry.getKey().equals(subjectName))
                return entry.getValue();
        }

        return null;
    }

    public ArrayList<String> getAssocSubjects(){
        ArrayList<String> assocSub = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Grade>> entry:
                subjectsAndGrades.entrySet()){
            assocSub.add(entry.getKey());
        }

        return assocSub;
    }

    public boolean hasSubject(String subjectName){
        return getAssocSubjects().contains(subjectName);
    }

    @Override
    public String toString() {
        return "\nFirstname: " + username +
                " \nAge: " + age +
                " \nSubjectsAndGrades: \n" + subjectsAndGrades;
    }

    public Student(String username, int age, HashMap<String, ArrayList<Grade>> subjectsAndGrades) {
        this.username = username;
        this.age = age;
        this.subjectsAndGrades = subjectsAndGrades;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public HashMap<String, ArrayList<Grade>> getSubjectsAndGrades() {
        return subjectsAndGrades;
    }

    public void setSubjectsAndGrades(HashMap<String, ArrayList<Grade>> subjectsAndGrades) {
        this.subjectsAndGrades = subjectsAndGrades;
    }
}
