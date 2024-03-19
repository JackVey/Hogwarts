import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Student implements AccountManagement{
    private String name;
    private String username;
    private byte[] password = new byte[32];
    //TODO -> should hash it in Security class
    private UUID accountID;
    private String house;
    //TODO -> should set it in Security class
    private ArrayList<Course> studentCourse = new ArrayList<>();
    @Override
    public boolean validatePassword(String enteredPassword) {
        return Arrays.equals(password, Security.hashPassword(enteredPassword));
    }

    @Override
    public void changeUsername(String newUsername) {
        username = newUsername;
    }

    @Override
    public void changePassword(String newPassword) {
        password = Security.hashPassword(newPassword);
    }

    @Override
    public void displayDashboard() {

    }
    @Override
    public void displayProfile() {

    }
}
