package Logic;

import Callbacks.CustomEventHandler;
import DB.UserRepo;
import Model.User;

import java.util.List;

public class LoginManager {

    private int loggedInUserId = 0;
    private UserRepo userRepo;
    private CustomEventHandler eventHandler;

    public LoginManager(UserRepo repo) {
        userRepo = repo;
    }

    public void registerLoggedInEventHandler(CustomEventHandler handler){
        this.eventHandler = handler;
    }

    public String attemptLogInUser(String fname, String lname, String password){
        List users = userRepo.getAllUsers();

        if(!ValidatorUtil.isNameValid(fname)
                || !ValidatorUtil.isNameValid(lname)){
            return "Invalid input";
        }

        for (Object o : users){
            User u = (User) o;
            if(u.getFirstName().equals(fname) && u.getLastName().equals(lname)){
                // user exists in DB
                if(!ValidatorUtil.isPasswordValid(password)){
                    return "Password invalid";
                }
                if(u.getPassword().equals(password)){
                    // the password is correct
                    loggedInUserId = u.getId();
                    eventHandler.handleLoggedInSuccess();
                    return "User " + fname + " " + lname + " logged in successfully.";
                } else {
                    return "User "  + fname + " " + lname +  " wrong password!";
                }
            }
        }
        if(ValidatorUtil.isPasswordValid(password)){
            loggedInUserId = userRepo.addUserToDB(fname, lname, password, null);
            eventHandler.handleLoggedInSuccess();
            return "User "  + fname + " " + lname +  " registered successfully, ID = " + loggedInUserId;

        } else {
            return "Password invalid";
        }

    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }
}
