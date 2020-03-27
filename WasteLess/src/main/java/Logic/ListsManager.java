package Logic;

import Callbacks.CustomEventHandler;
import DB.UserRepo;
import Model.GroceryList;
import Model.User;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.io.ObjectInputStream;
import java.util.List;
import java.util.Set;

public class ListsManager {


    private UserRepo userRepo;
    private CustomEventHandler eventHandler;
    private LoginManager login;
    private MutablePicoContainer picoContainer;

    public ListsManager(UserRepo repo, LoginManager login, MutablePicoContainer picoContainer){
        this.userRepo = repo;
        this.login = login;
        this.picoContainer = picoContainer;
    }

    public void registerEventHandler(CustomEventHandler handler){
        eventHandler = handler;
    }

    public boolean addNewListToUser(String newListName){
        int userId = login.getLoggedInUserId();

        GroceryList newList = picoContainer.getComponent(GroceryList.class);
        newList.setName(newListName);

        return (userRepo.addUserListInDB(userId, newList));
    }

    public boolean addNewItemToList(String name,
                                    String quantity,
                                    String calories,
                                    String purchaseDate,
                                    String consumptionDate,
                                    String expirationDate){
        return false;
    }

    public User getCurrentUser(){
        return userRepo.getUser(login.getLoggedInUserId());
    }

    public Set getCurrentUserLists(){
        return userRepo.getUsersLists(login.getLoggedInUserId());
    }

    /**
     * Method to return a list of the user by name
     * @param listName the name of the list to be retrieved
     * @return the grocery list required or null it it does not exist
     */
    public GroceryList getGroceryList(String listName){
        User u = getCurrentUser();
        for(Object o : u.getGroceryLists()){
            GroceryList g = (GroceryList) o;
            if(g.getName().equals(listName)){
                return g;
            }
        }
        return null;
    }

}
