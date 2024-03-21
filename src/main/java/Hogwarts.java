import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;

public class Hogwarts {
    public static void displayHomeMenu(){
        Menu.clearPage();
        System.out.println("Hogwarts central");
        System.out.println("[1] View all courses\n[2] View all teachers\n[3] View all students\n[4] Back");
        switch (Menu.getInput("Choose a function: ")){
            case "1":
                Hogwarts.viewAllCourses();
                break;
            case "2":
                Hogwarts.viewAllTeachers();
                break;
            case "3":
                Hogwarts.viewAllStudents();
                break;
            case "4":
                return;
            default:
                System.out.println("invalid input!");
                try{
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    Hogwarts.displayHomeMenu();
                }
                catch (Exception e){
                    Menu.clearPage();
                    Hogwarts.displayHomeMenu();
                }
                break;
        }
    }
    public static void viewAllTeachers() {
        Menu.clearPage();
        ArrayList<String> teachers = FileHandle.readListData("Teacher");
        for (String i : teachers){
            System.out.println(i);
        }
        String teacher = Menu.getInput("Enter a teacher to view and rate or BACK to return: ");
        if (!teacher.equals("BACK")){
            if (teachers.contains(teacher)){
                Hogwarts.viewTeacherAndRate(teacher);
                Menu.getInput("Press enter to continue...");
                Hogwarts.viewAllTeachers();
            } else{
                Menu.getInput("Invalid teacher name!\nPress enter to continue...");
                Hogwarts.viewAllTeachers();
            }
        }
        else {
            Hogwarts.displayHomeMenu();
        }
    }

    public static void viewAllStudents() {
        Menu.clearPage();
        ArrayList<String> students = FileHandle.readListData("Student");
        for (String i : students){
            System.out.println(i);
        }
        String student = Menu.getInput("Enter a student to view its profile or BACK to return: ");
        if (!student.equals("BACK")){
            if (students.contains(student)){
                viewStudentProfile(student);
                Menu.getInput("Press Enter to continue...");
                viewAllStudents();
            }
            else{
                Menu.getInput("Invalid student name!\nPress enter to continue...");
                Hogwarts.viewAllStudents();
            }
        }
        else{
            Hogwarts.displayHomeMenu();
        }
    }
    public static void viewAllCourses() {
        ArrayList<String> allCourses = FileHandle.readListData("Course");
        Menu.clearPage();
        for (String i : allCourses){
            System.out.println(i);
        }
        Menu.getInput("Press enter to continue...");
    }
    public static void viewStudentProfile(String username){
        Menu.clearPage();
        Student student = FileHandle.readStudentAccountData(username);
        System.out.println("Profile Menu");
        System.out.println("Name: " + student.getName());
        System.out.println("Username: " + student.getUsername());
        System.out.println("Account ID: " +student.getAccountID());
        System.out.println("House: " + student.getHouse());
    }
    public static void viewTeacherAndRate(String username){
        Menu.clearPage();
        System.out.println("[1] Show comments\n[2] Write comment");
        switch (Menu.getInput("Choose a function: ")){
            case "1": {
                Menu.clearPage();
                ArrayList<Comment> comments = FileHandle.readComment(username);
                if (!comments.isEmpty()){
                    for (Comment i : comments){
                        System.out.println("Rate: " + i.getRate());
                        System.out.println("Comment: " + i.getComment());
                    }
                }
                else{
                    Menu.getInput("There is no comment to show! Write the first comment!\nPress enter to continue...");
                    Hogwarts.viewTeacherAndRate(username);
                }
                break;
            }
            case "2": {
                Menu.clearPage();
                Comment comment = new Comment();
                float rate = Float.parseFloat(Menu.getInput("Rate this teacher between 0 to 10: "));
                if (rate >= 0 && rate <= 10){
                    String text = Menu.getInput("Enter your opinion: ");
                    comment.setRate(rate);
                    comment.setComment(text);
                    FileHandle.writeComment(comment, username);
                    Menu.getInput("Your comment has been saved!\nPress Enter to continue...");
                }
                else {
                    Menu.getInput("Invalid rate!\nPress enter to continue...");
                    Hogwarts.viewTeacherAndRate(username);
                }
                break;
            }
            default: {
                System.out.println("invalid input!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    Hogwarts.viewTeacherAndRate(username);
                } catch (Exception e) {
                    Menu.clearPage();
                    Hogwarts.viewTeacherAndRate(username);
                }
                break;
            }
        }
    }
}
