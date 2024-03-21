import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Student implements AccountManagement{
    private String name;
    private String username;
    private byte[] password = new byte[32];
    //TODO -> should hash it in Security class
    private UUID accountID;
    private String house;
    //TODO -> should set it in Security class
    private ArrayList<Course> studentCourse;
    private JSONArray scores;
    @Override
    public boolean validatePassword(String enteredPassword) {
        return Arrays.equals(password, Security.hashPassword(enteredPassword));
    }
    @Override
    public void changeUsername(String newUsername) {
        String oldUsername = username;
        username = newUsername;
        FileHandle.writeStudentAccountData(this, oldUsername);
        System.out.println("Username has been changed");
        Menu.getInput("Press enter to continue...");
        this.displayProfile();
    }
    @Override
    public void changePassword(String newPassword) {
        password = Security.hashPassword(newPassword).clone();
        FileHandle.writeStudentAccountData(this, username);
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
        System.out.println("[3] Sorting quiz");
        System.out.println("[4] Hogwarts central");
        System.out.println("[5] Logout");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                this.displayProfile();
                break;
            case "2":
                this.manageCoursesPanel();
                break;
            case "3":
                if (getHouse().isEmpty() || getHouse().isBlank()){
                    sortingQuiz();
                }
                else{
                    Menu.getInput("You already have a house!\nPress enter to continue...");
                    displayDashboard();
                }
                break;
            case "4":
                Hogwarts.displayHomeMenu();
                this.displayDashboard();
                break;
            case "5":
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
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Account ID: " + accountID);
        System.out.println("House: " + house);
        System.out.println("Actions\n[1] Change username\n[2] Change password\n[3] Back");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                Menu.clearPage();
                String input = Menu.getInput("Enter new username or BACK to return: ");
                if (!input.equals("BACK")) {
                    this.changeUsername(input);
                }
                else{
                    displayProfile();
                }
                break;
            case "2":
                Menu.clearPage();
                String input2 = Menu.getInput("Enter your old password or BACK to return: ");
                if (!input2.equals("BACK")) {
                    if (Arrays.equals(password, Security.hashPassword(input2))){
                        changePassword(Menu.getInput("Enter new password: "));
                    }
                }
                else{
                    displayProfile();
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
                    this.displayProfile();
                }
                catch (Exception e){
                    Menu.clearPage();
                    this.displayProfile();
                }
                break;
        }
    }
    public void manageCoursesPanel(){
        Menu.clearPage();
        System.out.println("Manage courses menu");
        System.out.println("[1] View and take course\n[2] View all courses teachers\n[3] Back");
        switch (Menu.getInput("Choose a function by its number: ")){
            case "1":
                Menu.clearPage();
                System.out.println("Taken courses: ");
                if (!studentCourse.isEmpty()) {
                    for (int i = 0; i < this.studentCourse.size(); i++) {
                        System.out.println(i + 1 + "- " + this.studentCourse.get(i).getName());
                        for (int j = 0; j < scores.length(); j++) {
                            if (scores.getJSONObject(i).has(this.studentCourse.get(i).getName())) {
                                System.out.println(" ,Score: " + scores.getJSONObject(i).get(this.studentCourse.get(i).getName()));
                                break;
                            }
                        }
                    }
                }
                else{
                    System.out.println("You don't have any course yet!");
                }
                System.out.println("Courses you haven't taken");
                ArrayList<String> allCourses = FileHandle.readListData("Course");
                if (!studentCourse.isEmpty()) {
                    int i = 0;
                    for (String k : allCourses) {
                        for (int j = 0; j < studentCourse.size(); j++) {
                            if (!this.studentCourse.get(j).getName().equals(k.replaceAll(" ", ""))) {
                                System.out.println(i + 1 + "- " + k);
                                i++;
                                break;
                            }
                        }
                    }
                }else{
                    for (int i = 0; i < allCourses.size(); i++) {
                        System.out.println(i + 1 + "- " + allCourses.get(i));
                    }
                }
                String input = Menu.getInput("Enter a course name to take or BACK to return: ");
                if (!input.equals("BACK")){
                    try {
                        studentCourse.add(FileHandle.readCourseData(input.replaceAll(" ", "")));
                        JSONArray newScores = getScores();
                        newScores.put(new JSONObject().put(input.replaceAll(" ", ""), 0));
                        this.setScores(newScores);
                        Course course = FileHandle.readCourseData(input.replaceAll(" ", ""));
                        ArrayList<String> enrolledStudents = course.getEnrolledStudents();
                        enrolledStudents.add(this.username);
                        course.setEnrolledStudents(enrolledStudents);
                        FileHandle.writeCourseData(course);
                        FileHandle.writeStudentAccountData(this, this.username);
                    }
                    catch (Exception e){
                        Menu.getInput("Invalid input!\nPress enter to continue...");
                        this.manageCoursesPanel();
                    }
                }
                manageCoursesPanel();
                break;
            case "2":
                for (Course course : studentCourse) {
                    for (String i : course.getTeachers())
                        System.out.println(i);
                }
                Menu.getInput("Press enter to continue...");
                manageCoursesPanel();
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
    public void setHouse(String house) {
        this.house = house;
    }
    public String getHouse() {
        return house;
    }
    public ArrayList<Course> getStudentCourse() {
        return studentCourse;
    }
    public void setStudentCourse(ArrayList<Course> studentCourse) {
        this.studentCourse = studentCourse;
    }
    public void setScores(JSONArray scores) {
        this.scores = scores;
    }
    public JSONArray getScores() {
        return scores;
    }
    public void sortingQuiz(){
        Menu.clearPage();
        System.out.println("Sorting quiz: ");
        System.out.println("Which house do you want to be in?");
        System.out.println("[1] Gryffindor\n[2] Hufflepuff\n[3] Ravenclaw\n[4] Slytherin");
        switch (Menu.getInput("Select a house: ")){
            case "1":
                setHouse("Gryffindor");
                FileHandle.writeStudentAccountData(this, username);
                this.displayDashboard();
                break;
            case "2":
                setHouse("Hufflepuff");
                FileHandle.writeStudentAccountData(this, username);
                this.displayDashboard();
                break;
            case "3":
                setHouse("Ravenclaw");
                FileHandle.writeStudentAccountData(this, username);
                this.displayDashboard();
                break;
            case "4":
                setHouse("Slytherin");
                FileHandle.writeStudentAccountData(this, username);
                this.displayDashboard();
                break;
            default:
                System.out.println("invalid input!");
                try{
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.sortingQuiz();
                }
                catch (Exception e){
                    Menu.clearPage();
                    this.sortingQuiz();
                }
                break;
        }
    }
}