package Logic;

import java.sql.Date;
import java.util.*;

import Callbacks.CustomEventHandler;
import DB.UserRepo;
import Model.GroceryItem;
import Model.GroceryList;
import Model.User;
import UI.LoginForm;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import javax.swing.*;

public class ManageUser implements CustomEventHandler {

    private static JFrame loginFrame;
    private static JFrame listsFrame;

    public static void main(String[] args) {

        MutablePicoContainer picoContainer = new DefaultPicoContainer();
        picoContainer.addComponent(User.class);
        picoContainer.addComponent(ArrayList.class);
        picoContainer.addComponent(HashSet.class);
        picoContainer.addComponent(GroceryItem.class);
        picoContainer.addComponent(GroceryList.class);

        UserRepo userRepo = new UserRepo(picoContainer);
        Login login = new Login(userRepo);

        LoginForm loginForm = new LoginForm(login);
        loginFrame = new JFrame("Login");
        loginFrame.setContentPane(loginForm.getMainPanel());
        loginFrame.pack();
        loginFrame.setVisible(true);

        CustomEventHandler handler = new ManageUser();
        login.registerLoggedInEventHandler(handler);

        HashSet groceryItems = picoContainer.getComponent(HashSet.class);
        Calendar c = Calendar.getInstance();
        Date d = new Date(c.getTimeInMillis());
        GroceryItem item1 = new GroceryItem("Bread", 1, 10, d, d, d);
        GroceryItem item2 = new GroceryItem("Butter", 5, 20, d, d, d);
        groceryItems.add(item1);
        groceryItems.add(item2);

        /* Let us have a set of grocery lists for the first user  */
        HashSet groceryLists = picoContainer.getComponent(HashSet.class);
        GroceryList g1 = new GroceryList("Eat this");
        g1.setGroceryItems(groceryItems);

        groceryLists.add(g1);

        //userRepo.addUserToDB("Soy", "Boy", "pass", groceryLists);
        //userRepo.updateUser(5, "password1");
        System.out.println(userRepo.getAllUsers());

    }

    @Override
    public void handleLoggedInSuccess() {
        loginFrame.setVisible(false);
        listsFrame.setVisible(true);
    }
}