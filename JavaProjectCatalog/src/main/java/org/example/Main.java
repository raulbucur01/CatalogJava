package org.example;

import java.io.*;
import java.util.*;

public class Main {
    static ArrayList<Student> students;
    static ArrayList<Professor> professors;
    static ArrayList<String> allSubjects;

    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.US);
            students = readStudentsFromFile();
            professors = readProfessorsFromFile();
            initializeSubjects();
            //showStudents();
            //showProfessors();
            //showAllSubjects();
            showLoginMenu();
            writeProfessorsToFile(professors);
            writeStudentsToFile(students);
        } catch (Exception e){
            System.out.println("Ati introdus o valoare invalida! Introduceti o valoare potrivita contextului!");
        }
    }

    private static void showAllSubjects() {
        System.out.println("Toate materiile: ");
        for (String sub:
             allSubjects) {
            System.out.print(sub + " ");
        }
    }

    private static void initializeSubjects() {
        List<String> initialList = Arrays.asList("LFC", "IA", "AF", "SD", "AG", "MIP");
        allSubjects = new ArrayList<>(initialList);
    }

    static void showStudents(){
        for (Student student:
             students) {
            System.out.println(student);
        }
    }

    static void showProfessors(){
        for (Professor professor:
                professors) {
            System.out.println(professor);
        }
    }

    static void showLoginMenu(){
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("\nIntroduceti username (x pt a iesi din aplicatie): ");
            String username = scan.next();
            int role = getRole(username);
            if (username.equals("x")){
                break;
            } else if (role == -1) {
                System.out.println("\nUsername-ul nu exista!\n\n");
            } else if (role == 1) {
                System.out.println("\nBine ai venit, Student: " + username);
                showStudentMenu(username);
            } else if (role == 2) {
                System.out.println("\nBine ai venit, Profesor: " + username);
                showProfessorMenu(username);
            }
        }
    }

    private static void showProfessorMenu(String username) {
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("1. Vezi care sunt materiile pe care le predai");
            System.out.println("2. Adauga o nota");
            System.out.println("3. Vezi notele unui elev");
            System.out.println("4. Modifica o nota");
            System.out.println("5. Sterge o nota");
            System.out.println("6. Vezi toate disciplinele existente");
            System.out.println("7. Calculeaza media notelor la o materie pt un student");
            System.out.println("8. Vezi lista disciplinelor sortata alfabetic");
            System.out.println("9. Vezi lista studentilor (si datele lor) sortata alfabetic");
            System.out.println("10. Vezi lista studentilor (numele lor) sortata alfabetic");
            System.out.println("11. Vezi notele unui elev la o materie sortate dupa cea mai recenta data");
            System.out.println("0. EXIT");
            System.out.println("\nAlege o optiune: ");

            int opt = scan.nextInt();
            if (opt == 0)
                break;
            else if (opt == 1) {
                handleShowProfessorSubjects(username);
            } else if (opt == 2) {
                handleAddGrade(scan, username);
            } else if (opt == 3) {
                handleShowGrades(scan);
            } else if (opt == 4) {
                handleUpdateGrade(scan, username);
            } else if (opt == 5) {
                handleDeleteGrade(scan, username);
            } else if (opt == 6) {
                System.out.println("Toate materiile existente: " + allSubjects + "\n");
            } else if (opt == 7) {
                handleShowAverage(scan, username);
            } else if (opt == 8) {
                handleShowSubjectsAlphabetically();
            } else if (opt == 9) {
                handleShowStudentsAlphabetically();
            } else if (opt == 10) {
                handleShowStudentNamesAlphabetically();
            } else if (opt == 11) {
                handleShowGradesSortedByDate(scan, username);
            }

        }
    }

    private static void handleShowGradesSortedByDate(Scanner scan, String username) {
        System.out.println("Alege studentul la care vrei sa ii sortezi notele: ");
        String studentName = scan.next();
        boolean exists = false;
        for (Student stud:
                students) {
            if (studentExists(studentName)) {
                exists = true;
                break;
            }
        }

        if (!exists)
        {
            System.out.println("Studentul ales nu exista!");
        }
        else{
            System.out.println("Alege materia la care vrei sa ii vezi notele sortate dupa data: ");
            String subject = scan.next();

            if (!allSubjects.contains(subject))
                System.out.println("\nMateria aleasa nu exista!\n\n");
            else if (!profTeaches(subject, username))
                System.out.println("\nNu predai materia aceasta!\n\n");
            else if(!studentHasSubject(subject, studentName)){
                System.out.println("\nStudentul nu este inscris la materia aceasta!\n\n");
            }
            else{
                for (Student stud:
                     students) {
                    if (stud.getUsername().equals(studentName)) {
                        ArrayList<Grade> sortedGrades = stud.getGradesSortedByDate(subject);
                        System.out.println(sortedGrades);
                        break;
                    }
                }
            }
        }
    }

    private static void handleShowStudentNamesAlphabetically() {
        ArrayList<String> studentNames = new ArrayList<>();
        for (Student stud:
             students) {
            studentNames.add(stud.username);
        }

        studentNames.sort(null);
        System.out.println(studentNames);
    }

    private static void handleShowStudentsAlphabetically() {
        students.sort(Comparator.comparing(Student::getUsername));
        System.out.println(students);
    }

    private static void handleShowSubjectsAlphabetically() {
        allSubjects.sort(null);
        System.out.println(allSubjects);
    }

    private static void handleShowAverage(Scanner scan, String username) {
        System.out.println("Alege studentul la care vrei sa calculezi media: ");
        String studentName = scan.next();
        boolean exists = false;
        for (Student stud:
                students) {
            if (studentExists(studentName)) {
                exists = true;
                break;
            }
        }

        if (!exists)
        {
            System.out.println("Studentul ales nu exista!");
        }
        else{
            System.out.println("Alege materia la care vrei sa calculezi media: ");
            String subject = scan.next();

            if (!allSubjects.contains(subject))
                System.out.println("\nMateria aleasa nu exista!\n\n");
            else if (!profTeaches(subject, username))
                System.out.println("\nNu predai materia aceasta!\n\n");
            else if(!studentHasSubject(subject, studentName)){
                System.out.println("\nStudentul nu este inscris la materia aceasta!\n\n");
            }
            else{
                showAverage(studentName, subject);
            }
        }
    }

    private static void showAverage(String studentName, String subject) {
        for (Student stud:
             students) {
            if (stud.getUsername().equals(studentName))
            {
                String formattedValue = String.format("%.2f", stud.getAverage(subject));
                System.out.println("Media este: " + formattedValue + "\n\n");
                break;
            }
        }
    }

    private static void handleDeleteGrade(Scanner scan, String username) {
        System.out.println("Alege studentul la care vrei sa stergi o nota: ");
        String studentName = scan.next();
        boolean exists = false;
        for (Student stud:
                students) {
            if (studentExists(studentName)) {
                exists = true;
                break;
            }
        }

        if (!exists)
        {
            System.out.println("Studentul ales nu exista!");
        }
        else{
            System.out.println("Alege materia la care vrei sa stergi nota: ");
            String subject = scan.next();

            if (!allSubjects.contains(subject))
                System.out.println("\nMateria aleasa nu exista!\n\n");
            else if (!profTeaches(subject, username))
                System.out.println("\nNu predai materia aceasta!\n\n");
            else if(!studentHasSubject(subject, studentName)){
                System.out.println("\nStudentul nu este inscris la materia aceasta!\n\n");
            }
            else{
                System.out.println("Scrie indexul notei pe care vrei sa o stergi: ");
                int index = scan.nextInt();

                deleteGrade(studentName, subject, index);
            }
        }
    }

    private static void deleteGrade(String studentName, String subject, int index) {
        for (Student stud:
                students) {
            if (stud.getUsername().equals(studentName)){
                stud.deleteGrade(subject, index);
                break;
            }
        }
    }

    private static void handleUpdateGrade(Scanner scan, String username) {
        System.out.println("Alege studentul la care vrei sa modifici nota: ");
        String studentName = scan.next();
        boolean exists = false;
        for (Student stud:
                students) {
            if (studentExists(studentName)) {
                exists = true;
                break;
            }
        }

        if (!exists)
        {
            System.out.println("Studentul ales nu exista!");
        }
        else{
            System.out.println("Alege materia la care vrei sa modifici nota: ");
            String subject = scan.next();

            if (!allSubjects.contains(subject))
                System.out.println("\nMateria aleasa nu exista!\n\n");
            else if (!profTeaches(subject, username))
                System.out.println("\nNu predai materia aceasta!\n\n");
            else if(!studentHasSubject(subject, studentName)){
                System.out.println("\nStudentul nu este inscris la materia aceasta!\n\n");
            }
            else{
                System.out.println("Scrie indexul notei pe care vrei sa o modifici: ");
                int index = scan.nextInt();
                System.out.println("Scrie noua nota: ");
                Double grade = scan.nextDouble();
                Date date = new Date();
                Grade new_g = new Grade(grade, date);

                updateGrade(studentName, subject, new_g, index);
            }
        }
    }

    private static void updateGrade(String studentName, String subject, Grade g, int index) {
        for (Student stud:
                students) {
            if (stud.getUsername().equals(studentName)){
                stud.updateGrade(subject, g, index);
                break;
            }
        }
    }

    private static void handleShowGrades(Scanner scan) {
        System.out.println("Alege elevul: ");
        String studentName = scan.next();

        for (Student stud:
             students) {
            if (stud.getUsername().equals(studentName))
            {
                HashMap<String, ArrayList<Grade>> grades = stud.getSubjectsAndGrades();
                System.out.println("Notele: \n" + grades);
            }
        }
    }

    private static void handleAddGrade(Scanner scan, String username) {
        System.out.println("Alege studentul la care vrei sa adaugi nota: ");
        String studentName = scan.next();
        boolean exists = false;
        for (Student stud:
             students) {
            if (studentExists(studentName)) {
                exists = true;
                break;
            }
        }

        if (!exists)
        {
            System.out.println("Studentul ales nu exista!");
        }
        else{
            System.out.println("Alege materia la care vrei sa adaugi nota: ");
            String subject = scan.next();

            if (!allSubjects.contains(subject))
                System.out.println("\nMateria aleasa nu exista!\n\n");
            else if (!profTeaches(subject, username))
                System.out.println("\nNu predai materia aceasta!\n\n");
            else if(!studentHasSubject(subject, studentName)){
                System.out.println("\nStudentul nu este inscris la materia aceasta!\n\n");
            }
            else{
                System.out.println("Scrie nota: ");
                Double grade = scan.nextDouble();

                Date date = new Date();
                Grade g = new Grade(grade, date);

                addGrade(studentName, subject, g);
            }
        }

    }

    private static void addGrade(String studentName, String subject, Grade g) {
        for (Student stud:
             students) {
            if (stud.getUsername().equals(studentName)){
                stud.addGrade(subject, g);
                break;
            }
        }
    }

    private static boolean studentHasSubject(String subject, String studentName) {
        for (Student stud:
             students) {
            if (stud.getUsername().equals(studentName)){
                ArrayList<String> assocSubs = stud.getAssocSubjects();
                if (!assocSubs.contains(subject))
                    return false;
            }
        }

        return true;
    }

    private static boolean profTeaches(String subject, String username) {
        for (Professor prof:
             professors) {
            if (prof.getUsername().equals(username))
            {
                ArrayList<String> associatedSubjects = prof.getAssociatedSubjects();
                if (associatedSubjects.contains(subject))
                    return true;
            }
        }

        return false;
    }

    private static boolean studentExists(String studentName) {
        for (Student stud:
             students) {
            if (stud.getUsername().equals(studentName))
                return true;
        }

        return false;
    }

    private static void handleShowProfessorSubjects(String username) {
        System.out.println("\n Materiile pe care le predai sunt: ");
        for (Professor prof:
             professors) {
            if (prof.getUsername().equals(username))
                System.out.println(prof.associatedSubjects + "\n\n");
        }
    }

    static void showStudentMenu(String username){
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("1. Vezi note la o materie specificata");
            System.out.println("2. Vezi care sunt materiile tale");
            System.out.println("0. EXIT");
            System.out.println("\nAlege o optiune: ");
            
            int opt = scan.nextInt();
            if (opt == 0)
                break;
            else if (opt == 1) {
                handleShowGradesForSubject(scan, username);
            } else if (opt == 2) {
                handleShowStudentSubjects(username);
            }

        }
    }

    private static void handleShowStudentSubjects(String username) {
        System.out.println("\nMateriile tale sunt: ");

        for (Student student:
                students) {
            if (student.getUsername().equals(username)) {
                ArrayList<String> subs = student.getAssocSubjects();
                System.out.print(subs + "\n\n");
                break;
            }
        }
    }

    private static void handleShowGradesForSubject(Scanner scan, String username) {
        System.out.println("La ce materie vrei sa vezi notele? ");
        String subjectName = scan.next();
        if (!allSubjects.contains(subjectName))
            System.out.println("\nAceasta materie nu exista!\n\n");
        else {
            for (Student student:
                 students) {
                if (student.getUsername().equals(username)) {
                    if (student.hasSubject(subjectName)) {
                        ArrayList<Grade> grades = student.getGradesForSubject(subjectName);
                        System.out.println("\nNotele tale la materia aleasa: \n" + grades + "\n");
                    }
                    else {
                        System.out.println("\nNu esti inscris la aceasta materie!\n\n");
                    }
                    break;
                }
            }
        }
    }

    // Student = 1     Professor = 2
    static int getRole(String username){
        for (Student student:
             students) {
            if (student.getUsername().equals(username))
                return 1;
        }

        for (Professor prof:
             professors) {
            if (prof.getUsername().equals(username))
                return 2;
        }

        return -1;
    }

    static ArrayList<Professor> readProfessorsFromFile() throws FileNotFoundException {
        File file = new File("C:/MY_CODE/MY Projects/Java Projects/JavaProjectCatalog/src/main/java/org" +
                "/example/Professors.txt");
        Scanner scan = new Scanner(file);
        ArrayList<Professor> professorList = new ArrayList<>();
        while (scan.hasNext()){
            int noProf = scan.nextInt();
            for (int i = 0; i < noProf; i++) {
                String username = scan.next();

                ArrayList<String> associatedSubjects = new ArrayList<>();
                int noSubjects = scan.nextInt();
                for (int j = 0; j < noSubjects; j++) {
                    String subjectName = scan.next();

                    associatedSubjects.add(subjectName);
                }

                professorList.add(new Professor(username, associatedSubjects));
            }
        }
        scan.close();

        return professorList;
    }

    static void writeProfessorsToFile(ArrayList<Professor> professors) throws IOException {
        File file = new File("C:/MY_CODE/MY Projects/Java Projects/JavaProjectCatalog/src/main/java/org" +
                "/example/Professors.txt");

        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println(professors.size());
        for (Professor professor : professors) {
            writer.println(professor.getUsername());
            writer.println(professor.getAssociatedSubjects().size());

            for (String subject : professor.getAssociatedSubjects()) {
                writer.println(subject);
            }
        }

        writer.close();
    }
    static ArrayList<Student> readStudentsFromFile() throws FileNotFoundException {
        File file = new File("C:/MY_CODE/MY Projects/Java Projects/JavaProjectCatalog/src/main/java/org" +
                "/example/Students.txt");
        Scanner scan = new Scanner(file);

        ArrayList<Student> studentList = new ArrayList<>();
        while (scan.hasNext()){
            int noStud = scan.nextInt();
            for (int i = 0; i < noStud; i++) {
                String username = scan.next();
                int age = scan.nextInt();

                HashMap<String, ArrayList<Grade>> subjectsAndGrades = new HashMap<>();
                int noSubjects = scan.nextInt();
                for (int j = 0; j < noSubjects; j++) {
                    String subjectName = scan.next();

                    ArrayList<Grade> grades = new ArrayList<>();
                    int noGrades = scan.nextInt();
                    for (int k = 0; k < noGrades; k++) {
                        Double grade = scan.nextDouble();
                        Date crtDate = new Date();

                        Grade g = new Grade(grade, crtDate);
                        grades.add(g);
                    }

                    subjectsAndGrades.put(subjectName, grades);
                }

                studentList.add(new Student(username, age, subjectsAndGrades));
            }
        }
        scan.close();

        return studentList;
    }

    static void writeStudentsToFile(ArrayList<Student> students) throws IOException {
        File file = new File("C:/MY_CODE/MY Projects/Java Projects/JavaProjectCatalog/src/main/java/org" +
                "/example/Students.txt");

        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println(students.size());
        for (Student student : students) {
            writer.println(student.getUsername());
            writer.println(student.getAge());
            writer.println(student.getSubjectsAndGrades().size());

            for (HashMap.Entry<String, ArrayList<Grade>> entry : student.getSubjectsAndGrades().entrySet()) {
                String subjectName = entry.getKey();
                ArrayList<Grade> grades = entry.getValue();

                writer.println(subjectName);
                writer.println(grades.size());

                for (Grade grade : grades) {
                    writer.println(grade.getGrade());
                }
            }
        }

        writer.close();
    }
}