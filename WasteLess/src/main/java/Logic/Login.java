package Logic;

import Callbacks.CustomEventHandler;
import DB.UserRepo;
import Model.User;

import java.util.List;

public class Login {

    private int loggedInUserId = 0;
    private UserRepo dbOperationManager;
    private CustomEventHandler loggedInEventHandler;

    public Login(UserRepo manager) {
        dbOperationManager = manager;
    }

    public void registerLoggedInEventHandler(CustomEventHandler handler){
        this.loggedInEventHandler = handler;
    }

    public String attemptLogInUser(String fname, String lname, String password){
        List users = dbOperationManager.getAllUsers();

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
                    loggedInEventHandler.handleLoggedInSuccess();
                    return "User " + fname + " " + lname + " logged in successfully.";
                } else {
                    return "User "  + fname + " " + lname +  " wrong password!";
                }
            }
        }

        loggedInUserId = dbOperationManager.addUserToDB(fname, lname, password, null);

        return "User "  + fname + " " + lname +  " registered successfully, ID = " + loggedInUserId;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }
}
