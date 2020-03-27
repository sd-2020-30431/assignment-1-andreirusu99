package Logic;

import java.sql.Date;
import java.util.*;

import Callbacks.CustomEventHandler;
import DB.UserRepo;
import Model.GroceryItem;
import Model.GroceryList;
import Model.User;
import UI.ListsForm;
import UI.LoginForm;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import javax.swing.*;

public class ManageUser implements CustomEventHandler {

    private static JFrame loginFrame;
    private static JFrame listsFrame;

    private static LoginManager loginManager;
    private static ListsManager listsManager;

    public static void main(String[] args) {

        MutablePicoContainer picoContainer = new DefaultPicoContainer();
        picoContainer.addComponent(User.class);
        picoContainer.addComponent(ArrayList.class);
        picoContainer.addComponent(GroceryItem.class);
        picoContainer.addComponent(GroceryList.class);

        UserRepo userRepo = new UserRepo(picoContainer);
        loginManager = new LoginManager(userRepo);
        listsManager = new ListsManager(userRepo, loginManager, picoContainer);

        CustomEventHandler handler = new ManageUser();
        loginManager.registerLoggedInEventHandler(handler);
        listsManager.registerEventHandler(handler);

        LoginForm loginForm = new LoginForm(loginManager);
        loginFrame = new JFrame("Login");
        loginFrame.setContentPane(loginForm.getMainPanel());
        loginFrame.pack();
        loginFrame.setVisible(true);

        //userRepo.addUserToDB("Soy", "Boy", "pass", groceryLists);
        //userRepo.updateUser(5, "password1");
        System.out.println(userRepo.getAllUsers());

    }

    @Override
    public void handleLoggedInSuccess() {
        loginFrame.setVisible(false);

        ListsForm listsForm = new ListsForm(listsManager);
        listsFrame = new JFrame("Lists View");
        listsFrame.setContentPane(listsForm.getMainPanel());
        listsFrame.pack();
        listsFrame.setVisible(true);
    }
}