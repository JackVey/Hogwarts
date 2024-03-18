import java.util.UUID;

public class Admin implements AccountManagement{
    private String name;
    private String username;
    private String password;
    private UUID accountID;
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
    public void createNewAdmin(){

    }
}
