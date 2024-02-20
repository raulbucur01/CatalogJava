package org.example;

import java.util.Date;

public class Grade {
    Double grade;
    Date date;

    public Grade(Double grade, Date date) {
        this.grade = grade;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "\nGrade: " + grade + "\nDate: " + date + "\n";
    }
}
