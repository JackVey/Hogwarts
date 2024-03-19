import java.io.Console;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Menu {
    static void displayMainMenu(){
        clearPage();
        System.out.println("Welcome to \"Hogwarts school of witchcraft and wizardry\" management system!");
        System.out.println("[1] Sing in");
        System.out.println("[2] Request for account");
        System.out.println("[3] Exit");
        switch (getInput("Please choose a function by its number: ")){
            case "1":
                displaySingInMenu();
                break;
            case "2":
                displaySingUpMenu();
                break;
            case "3":
                clearPage();
                System.exit(0);
                break;
            default:
                System.out.println("invalid input!");
                try{
                    TimeUnit.SECONDS.sleep(3);
                    clearPage();
                    displayMainMenu();
                }
                catch (Exception e){
                    clearPage();
                    displayMainMenu();
                }
                break;
        }
    }
    static void displayHomeMenu(){

    }
    static void displayStudentDashboard(){

    }
    static void displayTeacherDashboard(){

    }
    static void displayAdminDashboard(){
        getInput("Press enter continue...");
    }
    static void displaySingInMenu(){
        clearPage();
        ArrayList<String> accountList = new ArrayList<>();
        System.out.println("Enter required infos or type BACK to return");
        String username = getInput("Enter username: ");
        if (username.equals("BACK")) {
            displayMainMenu();
        }
        String password = getInput("Enter password: ");
        if (password.equals("BACK")) {
            displayMainMenu();
        }
        String roll = getInput("Enter your roll(Student, Teacher, Admin): ");
        if (roll.equals("BACK")) {
            displayMainMenu();
        }
        if (roll.equals("Student") || roll.equals("Admin") || roll.equals("Teacher")){
            accountList = FileHandle.readSingInData(roll);
        }
        else{
            System.out.println("Wrong roll!");
            try {
                TimeUnit.SECONDS.sleep(3);
                clearPage();
                displaySingInMenu();
            } catch (Exception e) {
                clearPage();
                displaySingInMenu();
            }
        }
        try {
            if (accountList.contains(username)) {
                switch (roll){
                    case "Admin":
                        Admin admin = FileHandle.readAdminAccountData(username);
                        if (admin.validatePassword(password)){
                            displayAdminDashboard();
                        }
                        break;
                    case "Student":
                        
                        break;
                    case "Teacher":
                        break;
                }

            }
        }
        catch (Exception e) {
                System.out.println("username or password is wrong!");
                getInput("Press enter to continue...");
                displaySingInMenu();
            }
    }
    static void displaySingUpMenu(){
        clearPage();
        System.out.println("Enter required infos or type BACK to return");
        String name = getInput("Enter your name: ");
        if (name.equals("BACK"))
            displayMainMenu();
        String roll = getInput("Enter your roll(Student, Teacher): ");
        if (name.equals("BACK"))
            displayMainMenu();
        //TODO -> should pass these arguments to a method to being validated
        if (roll.equals("Student") || roll.equals("Teacher")) {
            FileHandle.writeSingUpData(name, roll);
            System.out.println("Your account will be created soon");
            getInput("Press Enter to continue...");
            displayMainMenu();
        }
        else{
            System.out.println("Wrong roll!");
            try{
                TimeUnit.SECONDS.sleep(3);
                clearPage();
                displaySingUpMenu();
            }
            catch (Exception e){
                clearPage();
                displaySingUpMenu();
            }
        }
    }
    static void displayCourses(){

    }
    static void displayTeachers(){

    }
    static void displayStudents(){

    }
    static void displayAdmins(){

    }
    static void displaySearchMenu(){

    }
    static void displayCommentMenu(){

    }
    static void displayRatingMenu(){

    }
    static void clearPage(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    static String getInput(String message){
        System.out.print(message);
        Console console = System.console();
        return console.readLine();
    }
}
