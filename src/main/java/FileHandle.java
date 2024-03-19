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
                address = "Files\\Queued\\Students.txt";
                break;
            case "Teacher":
                address = "Files\\Queued\\Teachers.txt";
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
                address = "Files\\Accounts\\StudentsList.txt";
                break;
            case "Teacher":
                address = "Files\\Accounts\\TeachersList.txt";
                break;
            case "Admin":
                address = "Files\\Accounts\\AdminsList.txt";
                break;
        }
        try {
            Scanner scanner = new Scanner(new File(address));
            while (scanner.hasNextLine()) {
                accountList.add(scanner.nextLine());
            }
            scanner.close();
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
    static void writeAdminAccountData(Admin admin, String username){
        File file = new File("Files\\Accounts\\Admins\\" + username + ".txt");
        File rename = new File("Files\\Accounts\\Admins\\" + admin.getUsername() + ".txt");
        file.renameTo(rename);
        try {
            FileWriter clear = new FileWriter("Files\\Accounts\\Admins\\" + admin.getUsername() + ".txt");
            clear.write("");
            clear.close();
            FileWriter writer = new FileWriter("Files\\Accounts\\Admins\\" + admin.getUsername() + ".txt", true);
            writer.write(admin.getUsername() + "\n");
            writer.write(admin.getName() + "\n");
            byte[] password = admin.getPassword().clone();
            for (byte i : password){
                writer.write(i + "\n");
            }
            writer.close();
            if (file.exists()){
                file.delete();
            }
            ArrayList<String> adminList = readSingInData("Admin");
            adminList.set(adminList.indexOf(username) ,admin.getUsername());
            FileWriter clear2 = new FileWriter("Files\\Accounts\\AdminsList.txt");
            clear2.write("");
            clear2.close();
            FileWriter writer2 = new FileWriter("Files\\Accounts\\AdminsList.txt", true);
            for (String i : adminList){
                writer2.append(i + "\n");
            }
            writer2.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    static void writeNewAdminAccountData(Admin newAdmin){
        try {
            File file = new File("Files\\Accounts\\Admins\\" + newAdmin.getUsername() + ".txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(newAdmin.getUsername() + "\n");
            writer.write(newAdmin.getName() + "\n");
            byte[] password = newAdmin.getPassword().clone();
            for (byte i : password){
                writer.write(i + "\n");
            }
            writer.close();
            FileWriter writer2 = new FileWriter("Files\\Accounts\\AdminsList.txt", true);
            writer2.write("\n" + newAdmin.getUsername());
            writer2.close();
        }
        catch (Exception e){
            System.out.println("Couldn't creat file or it already exist!");
        }
    }
    static Admin readAdminAccountData(String username){
        Admin admin = new Admin();
        byte[] bytes = new byte[32];
        try {
            File myFile = new File("Files\\Accounts\\Admins\\" + username + ".txt");
            Scanner scanner = new Scanner(myFile);
            admin.setUsername(scanner.nextLine());
            admin.setName(scanner.nextLine());
            admin.setAccountID(Security.setUUID(admin.getName()));
            for (int i = 0 ; i < 32 ; i++){
                bytes[i] = Byte.parseByte(scanner.nextLine());
            }
            admin.setPassword(bytes);
            scanner.close();
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
