import java.io.Console;

public class Menu {
    static void displayMainMenu(){
        System.out.println("Welcome to \"Hogwarts school of witchcraft and wizardry\" management system!");
        System.out.println("[1] Sing in");
        System.out.println("[2] Request for account");
        System.out.println("[3] Exit");
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

    }
    static void displaySingUpMenu(){

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
        System.out.println(message);
        Console console = System.console();
        return console.readLine();
    }
}
