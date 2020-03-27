package Logic;

import Callbacks.CustomEventHandler;
import DB.UserRepo;
import Model.GroceryItem;
import Model.GroceryList;
import Model.User;
import org.picocontainer.MutablePicoContainer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Set;

public class ListsManager {


    private UserRepo userRepo;
    private CustomEventHandler eventHandler;
    private LoginManager login;
    private MutablePicoContainer picoContainer;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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

        return (userRepo.addListToUser(userId, newList));
    }

    public boolean addNewItemToList(String listName,
                                    String name,
                                    String quantity,
                                    String calories,
                                    String purchaseDate,
                                    String consumptionDate,
                                    String expirationDate){

        GroceryList listToAddTo = getGroceryList(listName);
        GroceryItem newItem = picoContainer.getComponent(GroceryItem.class);
        newItem.setName(name);
        newItem.setQuantity(Integer.parseInt(quantity));
        newItem.setCalorieValue(Integer.parseInt(calories));
        try {
            newItem.setPurchaseDate(dateFormat.parse(purchaseDate));
            newItem.setConsumptionDate(dateFormat.parse(consumptionDate));
            newItem.setExpirationDate(dateFormat.parse(expirationDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int userId = getCurrentUser().getId();
        return userRepo.addItemToList(userId, listToAddTo,newItem);
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
