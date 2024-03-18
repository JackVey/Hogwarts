import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
public class FileHandle {
    static void writeSingUpData(String name, String roll){
        String address = "";
        switch (roll){
            case "Student":
                address = "C:\\Users\\varin\\Documents\\Intellij\\Hogwarts\\src\\main\\java\\Files\\Queued\\Students.txt";
                break;
            case "Teacher":
                address = "C:\\Users\\varin\\Documents\\Intellij\\Hogwarts\\src\\main\\java\\Files\\Queued\\Teachers.txt";
                break;
            default:
                break;
        }
        try {
            FileWriter writer = new FileWriter(address);
            writer.append(name + "\n" + roll + "\n");
        }
        catch (Exception e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
    }
    static void readSingUpData(){

    }
    static void writeStudentAccountData(){

    }
    static void readStudentAccountData(){

    }
    static void writeTeacherAccountData(){

    }
    static void readTeacherAccountData(){

    }
    static void writeAdminAccountData(){

    }
    static void readAdminAccountData(){

    }
    static void writeCourseData(){

    }
    static void readCourseData(){

    }
    static void writeComment(){

    }
    static void readComment(){

    }
}
