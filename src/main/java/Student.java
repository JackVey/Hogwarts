import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

public class Student implements AccountManagement{
    private String name;
    private String username;
    private String password;
    private String house;
    //TODO -> should hash it in Security class
    private UUID accountID;
    //TODO -> should set it in Security class
    private ArrayList<Course> studentCourse = new ArrayList<>();
    @Override
    public boolean validatePassword(String enteredPassword) {
        if (Security.hashPassword(enteredPassword).equals(password))
            return true;
        return false;
    }

    @Override
    public void changeUsername(String newUsername) {
        username = newUsername;
    }

    @Override
    public void changePassword(String newPassword) {
        password = Security.hashPassword(newPassword);
    }
}
