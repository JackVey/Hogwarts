import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Admin implements AccountManagement{
    private String name;
    private String username;
    private byte[] password = new byte[32];
    private UUID accountID;
    @Override
    public boolean validatePassword(String enteredPassword) {
        return Arrays.equals(password, Security.hashPassword(enteredPassword));
    }
    @Override
    public void changeUsername(String newUsername) {
        String oldUsername = username;
        username = newUsername;
        FileHandle.writeAdminAccountData(this, oldUsername);
        System.out.println("Username has been changed");
        Menu.getInput("Press enter to continue...");
        this.displayProfile();
    }
    @Override
    public void changePassword(String newPassword) {
        password = Security.hashPassword(newPassword).clone();
        FileHandle.writeAdminAccountData(this, username);
        System.out.println("Password has been changed");
        Menu.getInput("Press enter to continue...");
        this.displayProfile();
    }
    @Override
    public void displayDashboard(){
        Menu.clearPage();
        System.out.println("Welcome, " + username);
        System.out.println("[1] Profile");
        System.out.println("[2] Create new Admin");
        System.out.println("[3] Manage users");
        System.out.println("[4] Manage courses");
        System.out.println("[5] Logout");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                this.displayProfile();
                break;
            case "2":
                this.createNewAdmin();
                break;
            case "3":
                break;
            case "4":
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
        System.out.println("Actions\n[1] Change username\n[2] Change password\n[3] Back");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                Menu.clearPage();
                String input = Menu.getInput("Type new username or BACK to return: ");
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
    public void manageUsers(){
        Menu.clearPage();
        System.out.println("Manage users menu");
        System.out.println("[1] View users\n[2] View requests\n[3]");
        switch (Menu.getInput("Please choose a function by its number: ")) {
            case "1":
                this.viewAndManageUsers();
                break;
            case "2":

                break;
            case "3":
                this.displayDashboard();
                break;
            default:
                System.out.println("invalid input!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.manageUsers();
                } catch (Exception e) {
                    Menu.clearPage();
                    this.manageUsers();
                }
                break;
        }
    }
    public void viewAndManageUsers(){
        Menu.clearPage();
        System.out.println("View users menu");
        System.out.println("[1] View students\n[2] View teachers\n[3] Back");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                ArrayList<String> studentsList = FileHandle.readListData("Student");
                for (int i = 0 ; i < studentsList.size() ; i++){
                    System.out.println((i + 1) + "- " + studentsList.get(i));
                }
                //TODO -> should get an input and make a Student variable to store its data
                try{
                int input = Integer.parseInt((Menu.getInput("Choose a student: ")));
                if (input >= 1 && input <= studentsList.size()){
                    viewAndEditStudent(FileHandle.readStudentAccountData(studentsList.get(input)));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    this.viewAndManageUsers();
                }
                break;
            case "2":
                ArrayList<String> teachersList = FileHandle.readListData("Teacher");
                for (int i = 0 ; i < teachersList.size() ; i++){
                    System.out.println((i + 1) + "- " + teachersList.get(i));
                }
                //TODO -> should get an input and make a Teacher variable to store its data
                try{
                    int input = Integer.parseInt((Menu.getInput("Choose a student: ")));
                    if (input >= 1 && input <= teachersList.size()){
                        viewAndEditTeacher(FileHandle.readTeacherAccountData(teachersList.get(input)));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    this.viewAndManageUsers();
                }
                break;
            case "3":
                this.manageUsers();
                break;
            default:
                System.out.println("invalid input!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.viewAndManageUsers();
                } catch (Exception e) {
                    Menu.clearPage();
                    this.viewAndManageUsers();
                }
                break;
        }
    }
    public void viewAndEditStudent(Student student){
        Menu.clearPage();
        System.out.println("Student edit panel");
        System.out.println("Student's username: " + student.getUsername());
        System.out.println("Student's name: " + student.getName());
        System.out.println("Student's account ID: " + student.getAccountID());
        System.out.println("Actions: \n[1] Change username\n[2] Change password\n[3] Delete user\n[4]Back");
        switch (Menu.getInput("Choose a function by its number: ")){
            case "1":
                Menu.clearPage();
                String input = Menu.getInput("Enter new username or BACK to return: ");
                if (!input.equals("BACK")) {
                    student.changeUsername(input);
                }
                else{
                    this.viewAndEditStudent(student);
                }
                break;
            case "2":
                String input2 = Menu.getInput("Enter the new password or BACK to return: ");
                if (!input2.equals("BACK")) {
                        student.changePassword(input2);
                }
                else{
                    this.viewAndEditStudent(student);
                }
                break;
            case "3":
                String input3 = Menu.getInput("Are you sure you want to delete user " + student.getUsername() + " ?(YES/NO)");
                if (input3.equals("YES")){
                    FileHandle.deleteUser(student.getUsername());
                    this.viewAndManageUsers();
                }
                else {
                    this.viewAndEditStudent(student);
                }
                break;
            case "4":
                this.viewAndManageUsers();
                break;
            default:
                System.out.println("invalid input!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.viewAndManageUsers();
                } catch (Exception e) {
                    Menu.clearPage();
                    this.viewAndManageUsers();
                }
                break;
        }
    }
    public void viewAndEditTeacher(Teacher teacher){
        System.out.println("Teacher edit panel");
        System.out.println("Teacher's username: " + teacher.getUsername());
        System.out.println("Teacher's name: " + teacher.getName());
        System.out.println("Teacher's account ID: " + teacher.getAccountID());
        System.out.println("Actions: \n[1] Change username\n[2] Change password\n[3] Delete this user\n[4]Back");
        switch (Menu.getInput("Choose a function by its number: ")){
            case "1":
                Menu.clearPage();
                String input = Menu.getInput("Enter new username or BACK to return: ");
                if (!input.equals("BACK")) {
                    teacher.changeUsername(input);
                }
                else{
                    this.viewAndEditTeacher(teacher);
                }
                break;
            case "2":
                String input2 = Menu.getInput("Enter the new password or BACK to return: ");
                if (!input2.equals("BACK")) {
                    teacher.changePassword(input2);
                }
                else{
                    this.viewAndEditTeacher(teacher);
                }
                break;
            case "3":
                String input3 = Menu.getInput("Are you sure you want to delete user " + teacher.getUsername() + " ?(YES/NO)");
                if (input3.equals("YES")){
                    FileHandle.deleteUser(teacher.getUsername());
                    System.out.println("User " + teacher.getUsername() + " has been deleted");
                    this.viewAndManageUsers();
                }
                else {
                    this.viewAndEditTeacher(teacher);
                }
                break;
            case "4":
                this.viewAndManageUsers();
                break;
            default:
                System.out.println("invalid input!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.viewAndManageUsers();
                } catch (Exception e) {
                    Menu.clearPage();
                    this.viewAndManageUsers();
                }
                break;
        }
    }
    public void viewAndManageUserRequests(){
        System.out.println("View user requests menu");
        System.out.println("[1] Students\n[2] Teachers\n[3] Back");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                break;
            case "2":
                break;
            case "3":
                this.viewAndManageUsers();
                break;
            default:
                System.out.println("invalid input!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    this.viewAndManageUserRequests();
                } catch (Exception e) {
                    Menu.clearPage();
                    this.viewAndManageUserRequests();
                }
                break;
        }
    }
    public void createNewAdmin(){
        Menu.clearPage();
        Admin newAdmin = new Admin();
        newAdmin.setName(Menu.getInput("Enter new admin name or BACK to return: "));
        if (newAdmin.getName().equals("BACK")){this.displayDashboard();}
        newAdmin.setUsername(Menu.getInput("Enter new admin username without space(if space is included, it will be deleted automaticly) or BACK to return: ").replaceAll(" ", ""));
        if (newAdmin.getUsername().equals("BACK")){this.displayDashboard();}
        else if (FileHandle.readListData("Admin").contains(newAdmin.getUsername())){
            System.out.println("This username already exist, choose another username");
            Menu.getInput("Press enter to continue");
            this.createNewAdmin();
        }
        newAdmin.setPassword(Security.hashPassword(Menu.getInput("Enter new admin password or BACK to return: ")));
        if (Arrays.equals(newAdmin.getPassword(), Security.hashPassword("BACK"))){this.displayDashboard();}
        FileHandle.writeNewAdminAccountData(newAdmin);
        System.out.println("New admin account has been created!");
        Menu.getInput("Press enter to continue...");
        this.displayDashboard();
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
}
