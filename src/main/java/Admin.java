import java.util.Arrays;
import java.util.UUID;

public class Admin implements AccountManagement{
    private String name;
    private String username;
    private byte[] password = new byte[32];
    private UUID accountID;
    @Override
    public boolean validatePassword(String enteredPassword) {
        if (Arrays.equals(password, Security.hashPassword(enteredPassword)))
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
    public void createNewAdmin(){

    }
}
