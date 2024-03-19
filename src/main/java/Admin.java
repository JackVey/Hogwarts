import java.io.Console;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Admin implements AccountManagement{
    private String name;
    private String username;
    private byte[] password = new byte[32];
    private UUID accountID;
    @Override
    public boolean validatePassword(String enteredPassword) {
        return Arrays.equals(password, Security.hashPassword(enteredPassword));
    }
    @Override
    public void changeUsername(String newUsername) {
        String oldUsername = username;
        username = newUsername;
        FileHandle.writeAdminAccountData(this, oldUsername);
        System.out.println("Username has been changed");
        Menu.getInput("Press enter to continue...");
        this.displayProfile();
    }
    @Override
    public void changePassword(String newPassword) {
        password = Security.hashPassword(newPassword).clone();
        FileHandle.writeAdminAccountData(this, username);
        System.out.println("Password has been changed");
        Menu.getInput("Press enter to continue...");
        this.displayProfile();
    }
    @Override
    public void displayDashboard(){
        Menu.clearPage();
        System.out.println("Welcome, " + username);
        System.out.println("[1] Profile");
        System.out.println("[2] Create new Admin");
        System.out.println("[3] Manage users");
        System.out.println("[4] Manage courses");
        System.out.println("[5] Logout");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                this.displayProfile();
                break;
            case "2":
                this.createNewAdmin();
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                Menu.displayMainMenu();
                break;
            default:
                System.out.println("invalid input!");
                try{
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    displayDashboard();
                }
                catch (Exception e){
                    Menu.clearPage();
                    displayDashboard();
                }
                break;
        }
    }
    @Override
    public void displayProfile() {
        Menu.clearPage();
        System.out.println("Profile Menu");
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Account ID: " + accountID);
        System.out.println("Actions\n[1] Change username\n[2] Change password\n[3] Back");
        switch (Menu.getInput("Please choose a function by its number: ")){
            case "1":
                Menu.clearPage();
                String input = Menu.getInput("Type new username or BACK to return: ");
                if (!input.equals("BACK")) {
                    changeUsername(input);
                }
                else{
                    displayProfile();
                }
                break;
            case "2":
                Menu.clearPage();
                String input2 = Menu.getInput("Enter your old password or BACK to return: ");
                if (!input2.equals("BACK")) {
                    if (Arrays.equals(password, Security.hashPassword(input2))){
                        changePassword(Menu.getInput("Enter new password: "));
                    }
                }
                else{
                    displayProfile();
                }
                break;
            case "3":
                displayDashboard();
                break;
            default:
                System.out.println("invalid input!");
                try{
                    TimeUnit.SECONDS.sleep(3);
                    Menu.clearPage();
                    displayProfile();
                }
                catch (Exception e){
                    Menu.clearPage();
                    displayProfile();
                }
                break;
        }
    }
    public void createNewAdmin(){
        Menu.clearPage();
        Admin newAdmin = new Admin();
        newAdmin.setName(Menu.getInput("Enter new admin name or BACK to return: "));
        if (newAdmin.getName().equals("BACK")){this.displayDashboard();}
        newAdmin.setUsername(Menu.getInput("Enter new admin username without space(if space is included, it will be deleted automaticly) or BACK to return: ").replaceAll(" ", ""));
        if (newAdmin.getUsername().equals("BACK")){this.displayDashboard();}
        else if (FileHandle.readSingInData("Admin").contains(newAdmin.getUsername())){
            System.out.println("This username already exist, choose another username");
            Menu.getInput("Press enter to continue");
            this.createNewAdmin();
        }
        newAdmin.setPassword(Security.hashPassword(Menu.getInput("Enter new admin password or BACK to return: ")));
        if (Arrays.equals(newAdmin.getPassword(), Security.hashPassword("BACK"))){this.displayDashboard();}
        FileHandle.writeNewAdminAccountData(newAdmin);
        System.out.println("New admin account has been created!");
        Menu.getInput("Press enter to continue...");
        this.displayDashboard();
    }
    public void setPassword(byte[] password) {
        this.password = password;
    }
    public byte[] getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setAccountID(UUID accountID) {
        this.accountID = accountID;
    }
    public UUID getAccountID() {
        return accountID;
    }
}
