package Logic.Managers;

import java.util.*;

import Interfaces.CustomEventHandler;
import DB.UserRepo;
import Model.Entities.GroceryItem;
import Model.Entities.GroceryList;
import Model.Entities.User;
import Model.Reports.Factories.ReportFactory;
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
        picoContainer.addComponent(ReportFactory.class);

        UserRepo userRepo = new UserRepo(picoContainer);
        loginManager = new LoginManager(userRepo);
        listsManager = new ListsManager(userRepo, loginManager, picoContainer);

        CustomEventHandler handler = new ManageUser();
        loginManager.registerLoggedInEventHandler(handler);

        LoginForm loginForm = new LoginForm(loginManager);
        loginFrame = new JFrame("Login");
        loginFrame.setContentPane(loginForm.getMainPanel());
        loginFrame.pack();
        loginFrame.setVisible(true);

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