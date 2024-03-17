import java.io.Console;
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

    }
    static void displaySingInMenu(){
        clearPage();
        //should pass these arguments to a method to being validated
        System.out.println("Enter required infos or type BACK to return");
        getInput("Enter username: ");
        getInput("Enter password: ");
        getInput("Enter your roll(Student, Teacher, Admin): ");
    }
    static void displaySingUpMenu(){
        clearPage();
        ////should pass these arguments to a method to being validated
        System.out.println("Enter required infos or type BACK to return");
        getInput("Enter your name: ");
        getInput("Enter your roll: ");
        System.out.println("Your account will be created soon");
        getInput("Press Enter to continue...");
        displayMainMenu();
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
