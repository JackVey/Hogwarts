import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Teacher implements AccountManagement{
    private String name;
    private String username;
    private byte[] password = new byte[32];
    //TODO -> should hash it in Security class
    private UUID accountID;
    //TODO -> should set it in Security class
    private ArrayList<Course> takenCourse;
    private float score;
    @Override
    public boolean validatePassword(String enteredPassword) {
        return Arrays.equals(password, Security.hashPassword(enteredPassword));
    }
    @Override
    public void changeUsername(String newUsername) {
        String oldUsername = username;
        username = newUsername;
        FileHandle.writeTeacherAccountData(this, oldUsername);
        System.out.println("Username has been changed");
        Menu.getInput("Press enter to continue...");
        this.displayProfile();
    }
    @Override
    public void changePassword(String newPassword) {
        password = Security.hashPassword(newPassword).clone();
        FileHandle.writeTeacherAccountData(this, username);
        System.out.println("Password has been changed");
        Menu.getInput("Press enter to continue...");
        this.displayProfile();
    }
    @Override
    public void displayDashboard() {
        Menu.clearPage();
        System.out.println("Welcome, " + username);
        System.out.println("[1] Profile");
        System.out.println("[2] Manage courses");
        System.out.println("[3] Hogwarts central");
        System.out.println("[4] Logout");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                this.displayProfile();
                break;
            case "2":
                this.manageCoursesPanel();
                break;
            case "3":
                Hogwarts.displayHomeMenu();
                this.displayDashboard();
                break;
            case "4":
                Menu.displayMainMenu();
                break;
            default:
                System.out.println("invalid input!");
                try{
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.displayDashboard();
                }
                catch (Exception e){
                    Menu.clearPage();
                    this.displayDashboard();
                }
                break;
        }
    }
    @Override
    public void displayProfile() {
        Menu.clearPage();
        System.out.println("Profile Menu");
        System.out.println("Score: " + score);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Account ID: " + accountID);
        System.out.println("Actions\n[1] Change username\n[2] Change password\n[3] Back");
        switch (Menu.getInput("Please choose a function by its number: ")) {
            case "1":
                Menu.clearPage();
                String input = Menu.getInput("Enter new username or BACK to return: ");
                if (!input.equals("BACK")) {
                    this.changeUsername(input);
                } else {
                    displayProfile();
                }
                break;
            case "2":
                Menu.clearPage();
                String input2 = Menu.getInput("Enter your old password or BACK to return: ");
                if (!input2.equals("BACK")) {
                    if (Arrays.equals(password, Security.hashPassword(input2))) {
                        changePassword(Menu.getInput("Enter new password: "));
                    }
                } else {
                    displayProfile();
                }
                break;
            case "3":
                this.displayDashboard();
                break;
            default:
                System.out.println("invalid input!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.displayProfile();
                } catch (Exception e) {
                    Menu.clearPage();
                    this.displayProfile();
                }
                break;
        }
    }
    public void manageCoursesPanel(){
        Menu.clearPage();
        System.out.println("Manage courses menu");
        System.out.println("[1] Take course\n[2] View all taken courses\n[3] Back");
        switch (Menu.getInput("Choose a function by its number: ")){
            case "1":
                Menu.clearPage();
                System.out.println("Taken courses: ");
                if (!takenCourse.isEmpty()) {
                    for (int i = 0; i < this.takenCourse.size(); i++) {
                        System.out.println(i + 1 + "- " + this.takenCourse.get(i).getName());
                    }
                }
                else {
                    System.out.println("You don't have any course yet!");
                }
                System.out.println("Courses you haven't taken");
                ArrayList<String> allCourses = FileHandle.readListData("Course");
                if (!takenCourse.isEmpty()) {
                    int i = 0;
                    for (String k : allCourses) {
                        for (int j = 0; j < takenCourse.size(); j++) {
                            if (!this.takenCourse.get(j).getName().replaceAll(" ", "").equals(k.replaceAll(" ", ""))) {
                                System.out.println(i + 1 + "- " + k);
                                i++;
                                break;
                            }
                        }
                    }
                }
                else {
                    for (int i = 0; i < allCourses.size(); i++) {
                        System.out.println(i + 1 + "- " + allCourses.get(i));
                    }
                }
                String input = Menu.getInput("Enter a course name to take or BACK to return: ");
                if (!input.equals("BACK")){
                    try {
                        takenCourse.add(FileHandle.readCourseData(input.replaceAll(" ", "")));
                        Course course = FileHandle.readCourseData(input.replaceAll(" ", ""));
                        ArrayList<String> teachers = course.getTeachers();
                        teachers.add(this.username);
                        course.setTeachers(teachers);
                        FileHandle.writeCourseData(course);
                        FileHandle.writeTeacherAccountData(this, this.getUsername());
                        Menu.getInput("Course has been added!\nPress enter to continue...");
                    }
                    catch (Exception e){
                        Menu.getInput("Invalid input!\nPress enter to continue...");
                        this.manageCoursesPanel();
                    }
                }
                manageCoursesPanel();
                break;
            case "2":
                for (int i = 0; i < takenCourse.size(); i++) {
                    System.out.println("Course: " + takenCourse.get(i).getName());
                }
                String input1 = Menu.getInput("Choose a course to view its students or BACK to return: ");
                ArrayList<String> allCourse = new ArrayList<>();
                for (Course course : takenCourse){
                    allCourse.add(course.getName());
                }
                if (!input1.equals("BACK")) {
                    if (allCourse.contains(input1)) {
                        Course getCourse = FileHandle.readCourseData(input1);
                        for(String name : getCourse.getEnrolledStudents()){
                            System.out.println(name);
                        }
                        String studentInput = Menu.getInput("Choose a student to score or enter BACK to return: ");
                        if (!studentInput.equals("BACK")){
                            Student student = FileHandle.readStudentAccountData(studentInput);
                            JSONArray scores = student.getScores();
                            for (int k = 0 ; k < scores.length() ; k++){
                                if(scores.getJSONObject(k).has(input1.replaceAll(" ", ""))){
                                    Menu.clearPage();
                                    String score = Menu.getInput("Enter student score(between 0 to 100): ");
                                    if (Integer.parseInt(score) >= 0 && Integer.parseInt(score) <= 100){
                                        scores.getJSONObject(k).put(input1.replaceAll(" ", ""), score);
                                        student.setScores(scores);
                                        FileHandle.writeStudentAccountData(student, student.getUsername());
                                        Menu.getInput("Successfully scored student!\nPress enter to continue...");
                                        manageCoursesPanel();
                                    }
                                }
                            }
                        }
                        else {
                            manageCoursesPanel();
                        }
                    } else {
                        Menu.getInput("Invalid range!\nPress enter to continue...");
                        manageCoursesPanel();
                    }
                }
                else{
                    manageCoursesPanel();
                }
                break;
            case "3":
                this.displayDashboard();
                break;
            default:
                System.out.println("invalid input!");
                try{
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.manageCoursesPanel();
                }
                catch (Exception e){
                    Menu.clearPage();
                    this.manageCoursesPanel();
                }
                break;
        }
    }
    public void setPassword(byte[] password) {
        this.password = password;
    }
    public byte[] getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setAccountID(UUID accountID) {
        this.accountID = accountID;
    }
    public UUID getAccountID() {
        return accountID;
    }
    public void setTakenCourse(ArrayList<Course> takenCourse) {
        this.takenCourse = takenCourse;
    }
    public ArrayList<Course> getTakenCourse() {
        return takenCourse;
    }
    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }
}
