import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

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
            File file = new File(address);
            Scanner scanner = new Scanner(file);
            JSONArray singUpData = new JSONArray(scanner.nextLine());
            scanner.close();
            singUpData.put(name);
            FileWriter writer = new FileWriter(file);
            writer.write(singUpData.toString());
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Menu.getInput("Something went wrong!\nPress enter to continue");
        }
    }
    static JSONArray readSingUpData(String roll){
        String address = "";
        switch (roll){
            case "Student":
                address = "Files\\Queued\\Students.txt";
                break;
            case "Teacher":
                address = "Files\\Queued\\Teachers.txt";
                break;
        }
        try {
            File file = new File(address);
            Scanner scanner = new Scanner(file);
            JSONArray requestList = new JSONArray(scanner.nextLine());
            scanner.close();
            return requestList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    static ArrayList<String> readListData(String roll){
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
            case "Course":
                address = "Files\\Courses\\CoursesList.txt";
        }
        try {
            Scanner scanner = new Scanner(new File(address));
            JSONArray list = new JSONArray(scanner.nextLine());
            scanner.close();
            for (int i = 0; i < list.length(); i++) {
                accountList.add(list.get(i).toString());
            }
        }
        catch (Exception e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
        return accountList;
    }
    static void writeStudentAccountData(Student student, String oldUsername){
        File file = new File("Files\\Accounts\\Students\\" + oldUsername + ".txt");
        File rename = new File("Files\\Accounts\\Students\\" + student.getUsername() + ".txt");
        file.renameTo(rename);
        try {
            Scanner scanner = new Scanner(rename);
            JSONObject studentJson = new JSONObject(scanner.nextLine());
            scanner.close();
            ArrayList<String> courseList = new ArrayList<>();
            for (int i = 0; i < student.getStudentCourse().size(); i++) {
                courseList.add(student.getStudentCourse().get(i).getName());
            }
            studentJson.put("username", student.getUsername());
            studentJson.put("name", student.getName());
            studentJson.put("house", student.getHouse());
            studentJson.put("password", student.getPassword());
            studentJson.put("courses", courseList .toArray());
            studentJson.put("scores", student.getScores());
            FileWriter writer = new FileWriter(rename);
            writer.write(studentJson.toString());
            writer.close();
            ArrayList<String> studentList = readListData("Student");
            studentList.set(studentList.indexOf(oldUsername) ,student.getUsername());
            JSONArray studentListJson = new JSONArray(studentList.toArray());
            FileWriter writer1 = new FileWriter("Files\\Accounts\\StudentsList.txt");
            writer1.write(studentListJson.toString());
            writer1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    static void writeNewStudentAccountData(Student student){
        File file = new File("Files\\Accounts\\Students\\" + student.getUsername() + ".txt");
        File queued = new File("Files\\Queued\\Students.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                JSONObject studentJson = new JSONObject();
                studentJson.put("username", student.getUsername());
                studentJson.put("name", student.getName());
                studentJson.put("house", student.getHouse());
                studentJson.put("password", student.getPassword());
                studentJson.put("courses", student.getStudentCourse().toArray());
                studentJson.put("scores", student.getScores());
                FileWriter writer = new FileWriter(file);
                writer.write(studentJson.toString());
                writer.close();
                ArrayList<String> studentList = readListData("Student");
                studentList.add(student.getUsername());
                FileWriter writer1 = new FileWriter("Files\\Accounts\\StudentsList.txt");
                writer1.write(new JSONArray(studentList.toString()).toString());
                writer1.close();
                Scanner scanner = new Scanner(queued);
                JSONArray queuedArray = new JSONArray(scanner.nextLine());
                scanner.close();
                for (int i = 0 ; i < queuedArray.length() ; i++){
                    if (queuedArray.get(i).toString().equals(student.getName())){
                        queuedArray.remove(i);
                    }
                }
                Menu.getInput(queuedArray.toString());
                FileWriter writer2 = new FileWriter(queued);
                writer2.close();
                Menu.getInput("New account has been created!\nPress enter to continue...");
            }
            else{
                Menu.getInput("This user already exist!\nPress enter to continue...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }
    static Student readStudentAccountData(String username){
        byte[] passBytes = new byte[32];
        Student student = new Student();
        try {
            File myFile = new File("Files\\Accounts\\Students\\" + username + ".txt");
            ArrayList<Course> studentsCourses = new ArrayList<>();
            Scanner scanner = new Scanner(myFile);
            JSONObject studentJson = new JSONObject(scanner.nextLine());
            scanner.close();
            student.setUsername(studentJson.getString("username"));
            student.setName(studentJson.getString("name"));
            student.setAccountID(Security.setUUID(student.getName()));
            student.setHouse(studentJson.getString("house"));
            JSONArray array = studentJson.getJSONArray("password");
            for (int i = 0 ; i < 32 ; i++){
                passBytes[i] = Byte.parseByte(array.get(i).toString());
            }
            student.setPassword(passBytes);
            array = studentJson.getJSONArray("courses");
            for (int i = 0 ; i < array.length() ; i++){
                studentsCourses.add(readCourseData(array.getString(i)));
            }
            student.setStudentCourse(studentsCourses);
            student.setScores(studentJson.getJSONArray("scores"));
            return student;
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Couldn't find the file!");
        }
        return null;
    }
    static void writeTeacherAccountData(Teacher teacher, String oldUsername){
        File file = new File("Files\\Accounts\\Teachers\\" + oldUsername + ".txt");
        File rename = new File("Files\\Accounts\\Teachers\\" + teacher.getUsername() + ".txt");
        File commentFile = new File("Files\\Comments\\" + oldUsername + ".txt");
        File commentRename = new File("Files\\Comments\\" + teacher.getUsername() + ".txt");
        file.renameTo(rename);
        commentFile.renameTo(commentRename);
        try {
            Scanner scanner = new Scanner(rename);
            JSONObject teacherJson = new JSONObject(scanner.nextLine());
            scanner.close();
            ArrayList<String> courseList = new ArrayList<>();
            for (int i = 0; i < teacher.getTakenCourse().size(); i++) {
                courseList.add(teacher.getTakenCourse().get(i).getName());
            }
            teacherJson.put("courses", courseList.toArray());
            teacherJson.put("password", teacher.getPassword());
            teacherJson.put("name", teacher.getName());
            teacherJson.put("username", teacher.getUsername());
            teacherJson.put("score", teacher.getScore());
            FileWriter writer = new FileWriter(rename);
            writer.write(teacherJson.toString());
            writer.close();
            ArrayList<String> teacherList = readListData("Teacher");
            teacherList.set(teacherList.indexOf(oldUsername) ,teacher.getUsername());
            JSONArray studentListJson = new JSONArray(teacherList.toArray());
            FileWriter writer1 = new FileWriter("Files\\Accounts\\TeachersList.txt");
            writer1.write(studentListJson.toString());
            writer1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    static void writeNewTeacherAccountData(Teacher teacher){
        File file = new File("Files\\Accounts\\Teachers\\" + teacher.getUsername() + ".txt");
        File commentFile = new File("Files\\Comments\\" + teacher.getUsername() + ".txt");
        File queued = new File("Files\\Queued\\Teachers.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                commentFile.createNewFile();
                JSONObject teacherJson = new JSONObject();
                teacherJson.put("username", teacher.getUsername());
                teacherJson.put("name", teacher.getName());
                teacherJson.put("password", teacher.getPassword());
                teacherJson.put("score", teacher.getScore());
                teacherJson.put("courses", teacher.getTakenCourse());
                FileWriter writer = new FileWriter(file);
                writer.write(teacherJson.toString());
                writer.close();
                ArrayList<String> teacherList = readListData("Teacher");
                teacherList.add(teacher.getUsername());
                JSONArray teacherListJson = new JSONArray(teacherList.toArray());
                FileWriter writer1 = new FileWriter("Files\\Accounts\\TeachersList.txt");
                writer1.write(teacherListJson.toString());
                writer1.close();
                Scanner scanner = new Scanner(queued);
                JSONArray queuedArray = new JSONArray(scanner.nextLine());
                scanner.close();
                for (int i = 0 ; i < queuedArray.length() ; i++){
                    if (queuedArray.get(i).toString().equals(teacher.getName())){
                        queuedArray.remove(i);
                    }
                }
                FileWriter writer2 = new FileWriter(queued);
                writer2.write(queuedArray.toString());
                writer2.close();
                Menu.getInput("New account has been created!\nPress enter to continue...");
            } else {
                Menu.getInput("This user already exist!\nPress enter to continue...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }
    static Teacher readTeacherAccountData(String username){
        Teacher teacher = new Teacher();
        byte[] passBytes = new byte[32];
        try {
            File myFile = new File("Files\\Accounts\\Teachers\\" + username + ".txt");
            ArrayList<Course> courseList = new ArrayList<>();
            Scanner scanner = new Scanner(myFile);
            JSONObject teacherJson = new JSONObject(scanner.nextLine());
            scanner.close();
            teacher.setUsername(teacherJson.getString("username"));
            teacher.setName(teacherJson.getString("name"));
            teacher.setAccountID(Security.setUUID(teacher.getName()));
            teacher.setScore(teacherJson.getFloat("score"));
            JSONArray array = teacherJson.getJSONArray("password");
            for (int i = 0 ; i < 32 ; i++){
                passBytes[i] = Byte.parseByte(array.get(i).toString());
            }
            teacher.setPassword(passBytes);
            array = teacherJson.getJSONArray("courses");
            for (int i = 0 ; i < array.length() ; i++){
                courseList.add(readCourseData(array.getString(i)));
            }
            teacher.setTakenCourse(courseList);
            return teacher;
        }
        catch (Exception e){
            System.out.println("Couldn't find the file!");
        }
        return null;
    }
    static void writeAdminAccountData(Admin admin, String username){
        File file = new File("Files\\Accounts\\Admins\\" + username + ".txt");
        File rename = new File("Files\\Accounts\\Admins\\" + admin.getUsername() + ".txt");
        file.renameTo(rename);
        try {
            Scanner scanner = new Scanner(rename);
            JSONObject adminJson = new JSONObject(scanner.nextLine());
            adminJson.put("username", admin.getUsername());
            adminJson.put("name", admin.getName());
            adminJson.put("password", admin.getPassword());
            FileWriter writer = new FileWriter(rename);
            writer.write(adminJson.toString());
            writer.close();
            ArrayList<String> adminList = readListData("Admin");
            adminList.set(adminList.indexOf(username) ,admin.getUsername());
            JSONArray adminsList = new JSONArray(adminList.toArray());
            FileWriter writer1 = new FileWriter("Files\\Accounts\\AdminsList.txt");
            writer1.write(adminsList.toString());
            writer1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    static void writeNewAdminAccountData(Admin newAdmin){
        try {
            JSONObject newAdminJson = new JSONObject();
            newAdminJson.put("username", newAdmin.getName());
            newAdminJson.put("name", newAdmin.getName());
            newAdminJson.put("password", newAdmin.getPassword());
            File file = new File("Files\\Accounts\\Admins\\" + newAdmin.getUsername() + ".txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(newAdminJson.toString());
            writer.close();
            ArrayList<String> adminList = readListData("Admin");
            JSONArray adminsList = new JSONArray(adminList.toArray());
            adminsList.put(newAdmin.getUsername());
            FileWriter writer1 = new FileWriter("Files\\Accounts\\AdminsList.txt");
            writer1.write(adminsList.toString());
            writer1.close();
        }
        catch (Exception e){
            System.out.println("Couldn't creat file or it already exist!");
        }
    }
    static Admin readAdminAccountData(String username){
        Admin admin = new Admin();
        byte[] password = new byte[32];
        try {
            File myFile = new File("Files\\Accounts\\Admins\\" + username + ".txt");
            Scanner scanner = new Scanner(myFile);
            JSONObject json = new JSONObject(scanner.nextLine());
            admin.setUsername(json.getString("username"));
            admin.setName(json.getString("name"));
            admin.setAccountID(Security.setUUID(admin.getName()));
            JSONArray pass = json.getJSONArray("password");
            for (int i = 0; i < 32 ; i++) {
            password[i] = Byte.parseByte(pass.get(i).toString());
            }
            admin.setPassword(password);
            scanner.close();
            return admin;
        }
        catch (Exception e){
            System.out.println("Couldn't find the file!");
        }
        return null;
    }
    static void writeCourseData(Course course){
        File file = new File("Files\\Courses\\" + course.getName() + ".txt");
        try {
            Scanner scanner = new Scanner(file);
            JSONObject courseJson = new JSONObject(scanner.nextLine());
            scanner.close();
            courseJson.put("students", course.getEnrolledStudents().toArray());
            courseJson.put("teachers", course.getTeachers().toArray());
            FileWriter writer = new FileWriter(file);
            writer.write(courseJson.toString());
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }
    static void writeNewCourseData(Course newCourse){
        File file = new File("Files\\Courses\\" + newCourse.getName() + ".txt");
        File courseListFile = new File("Files\\Courses\\CoursesList.txt");
        try {
            if (file.createNewFile()){
                JSONObject course = new JSONObject();
                course.put("students", new ArrayList<>().toArray());
                course.put("teachers", new ArrayList<>().toArray());
                FileWriter writer = new FileWriter(file);
                writer.write(course.toString());
                writer.close();
                Scanner scanner = new Scanner(courseListFile);
                JSONArray courseArray = new JSONArray(scanner.nextLine());
                scanner.close();
                courseArray.put(newCourse.getName());
                FileWriter writer1 = new FileWriter(courseListFile);
                writer1.write(courseArray.toString());
                writer1.close();
                Menu.getInput("Course has been created!\nPress enter to continue...");
            }
            else{
                Menu.getInput("Course already exist!\nPress enter to continue...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    static Course readCourseData(String courseName){
        try {
            Course course = new Course();
            ArrayList<String> studentsList = new ArrayList<>();
            ArrayList<String> teachersList = new ArrayList<>();
            course.setName(courseName);
            File students = new File("Files\\Courses\\" + courseName.replaceAll(" ", "") + ".txt");
            Scanner scanner = new Scanner(students);
            JSONObject courseData = new JSONObject(scanner.nextLine());
            scanner.close();
            JSONArray array = courseData.getJSONArray("students");
            for (int i = 0; i < array.length(); i++) {
                studentsList.add(array.getString(i));
            }
            array = courseData.getJSONArray("teachers");
            for (int i = 0; i < array.length(); i++) {
                teachersList.add(array.getString(i));
            }
            course.setEnrolledStudents(studentsList);
            course.setTeachers(teachersList);
            return course;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    static void writeComment(Comment comment, String username){
        File file = new File("Files\\Comments\\" + username + ".txt");
        try {
            JSONArray comments;
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()){
                comments = new JSONArray(scanner.nextLine());
                scanner.close();
            } else {
                comments = new JSONArray();
            }
            JSONObject newComment = new JSONObject();
            newComment.put("rate", comment.getRate());
            newComment.put("comment", comment.getComment());
            comments.put(newComment);
            FileWriter writer = new FileWriter(file);
            writer.write(comments.toString());
            writer.close();
            updateRate(username, comment.getRate());
        } catch (Exception e){
          Menu.getInput("Something went wrong!\nPress enter to continue...");
        }
    }
    static ArrayList<Comment> readComment(String username){
        File file = new File("Files\\Comments\\" + username + ".txt");
        try {
            ArrayList<Comment> comments = new ArrayList<>();
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()){
                JSONArray commentsJson = new JSONArray(scanner.nextLine());
                for (int i = 0 ; i < commentsJson.length() ; i++){
                    JSONObject co = commentsJson.getJSONObject(i);
                    Comment com = new Comment();
                    com.setRate(co.getFloat("rate"));
                    com.setComment(co.getString("comment"));
                    comments.add(com);
                }
                return comments;
            }
            else {
                return new ArrayList<>();
            }
        }
        catch (Exception e){

        }
        return null;
    }
    static void deleteUser(String username){
        File student = new File("Files\\Accounts\\Students\\" + username + ".txt");
        File teacher = new File("Files\\Accounts\\Teachers\\" + username + ".txt");
        if (student.exists()){
            if (student.delete()){
                ArrayList<String> studentList = readListData("Student");
                studentList.remove(studentList.indexOf(username));
                try {
                    Scanner scanner = new Scanner(student);
                    JSONArray array = new JSONArray(scanner.nextLine());
                    scanner.close();
                    for (int i = 0; i < array.length() ; i++){
                        if (array.get(i).equals(username)){
                            array.remove(i);
                        }
                    }
                    FileWriter writer = new FileWriter("Files\\StudentsList.txt");
                    writer.write(array.toString());
                    writer.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Menu.getInput("Couldn't delete account!\nPress enter to continue...");
            }
        }
        else if (teacher.exists()){
            if (teacher.delete()){
                ArrayList<String> teacherList = readListData("Teacher");
                teacherList.remove(teacherList.indexOf(username));
                try {
                    Scanner scanner = new Scanner(teacher);
                    JSONArray array = new JSONArray(scanner.nextLine());
                    scanner.close();
                    for (int i = 0; i < array.length() ; i++){
                        if (array.getString(i).equals(username)){
                            array.remove(i);
                            break;
                        }
                    }
                    FileWriter writer = new FileWriter("Files\\TeachersList.txt");
                    writer.write(array.toString());
                    writer.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Menu.getInput("Couldn't delete account!\nPress enter to continue...");
            }
        }
    }
    static void updateRate(String username, float rate){
        Teacher teacher = readTeacherAccountData(username);
        teacher.setScore(teacher.getScore() + rate);
        writeTeacherAccountData(teacher, teacher.getUsername());
    }
}
