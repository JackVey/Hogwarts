import java.util.UUID;

public class Account implements AccountManagement{
    private String username;
    // TODO: Passwords should hashed
    private String password;
    private UUID accountID;

    @Override
    public boolean validatePassword(String enteredPassword) {
        //TODO
        return false;
    }

    @Override
    public void changeUsername(String newUsername) {
        //TODO
    }

    @Override
    public void changePassword(String newPassword) {
        //TODO
    }
}
