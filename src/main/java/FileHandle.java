import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

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
    static ArrayList<String> readSingInData(String roll){
        String address = "";
        ArrayList<String> accountList = new ArrayList<>();
        switch (roll){
            case "Student":
                address = "C:\\Users\\varin\\Documents\\Intellij\\Hogwarts\\src\\main\\java\\Files\\Accounts\\StudentsList.txt";
                break;
            case "Teacher":
                address = "C:\\Users\\varin\\Documents\\Intellij\\Hogwarts\\src\\main\\java\\Files\\Accounts\\TeachersList.txt";
                break;
            case "Admin":
                address = "C:\\Users\\varin\\Documents\\Intellij\\Hogwarts\\src\\main\\java\\Files\\Accounts\\AdminsList.txt";
                break;
        }
        try {
            Scanner scanner = new Scanner(new File(address));
            while (scanner.hasNextLine()) {
                accountList.add(scanner.nextLine());
            }
        }
        catch (Exception e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
        return accountList;
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
    static Admin readAdminAccountData(String username){
        Admin admin = new Admin();
        byte[] bytes = new byte[32];
        try {
            File myFile = new File("C:\\Users\\varin\\Documents\\Intellij" +
                    "\\Hogwarts\\src\\main\\java\\Files\\Accounts\\Admins\\" + username + ".txt");
            Scanner scanner = new Scanner(myFile);
            admin.setUsername(scanner.nextLine());
            admin.setName(scanner.nextLine());
            admin.setAccountID(UUID.fromString(scanner.nextLine()));
            for (int i = 0 ; i < 32 ; i++){
                bytes[i] = Byte.parseByte(scanner.nextLine());
            }
            admin.setPassword(bytes);
            return admin;
        }
        catch (Exception e){
            System.out.println("Couldn't find the file!");
        }
        return null;
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
