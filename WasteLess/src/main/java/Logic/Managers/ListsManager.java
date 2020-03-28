package Logic.Managers;

import DB.UserRepo;
import Interfaces.Report;
import Logic.Validators.ValidatorUtil;
import Model.Entities.GroceryItem;
import Model.Entities.GroceryList;
import Model.Entities.User;
import Model.Reports.Factories.ReportFactory;
import Model.Reports.ReportEntities.MonthlyReport;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.MutablePicoContainer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class ListsManager {

    private UserRepo userRepo;
    private LoginManager login;
    private MutablePicoContainer picoContainer;
    private ReportFactory reportFactory;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ListsManager(UserRepo repo, LoginManager login, @NotNull MutablePicoContainer picoContainer){
        this.userRepo = repo;
        this.login = login;
        this.picoContainer = picoContainer;
        this.reportFactory = picoContainer.getComponent(ReportFactory.class);
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

        if(!ValidatorUtil.isNameValid(name)
                || !ValidatorUtil.isNumberValid(quantity)
                || !ValidatorUtil.isNumberValid(calories)
                || !ValidatorUtil.isDateValid(purchaseDate)
                || !ValidatorUtil.isDateValid(consumptionDate)
                || !ValidatorUtil.isDateValid(expirationDate)
        ){ return false;}

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

    public boolean setUserCalorieIntake(String calorieIntake){
        User currentUser = getCurrentUser();
        if(ValidatorUtil.isNumberValid(calorieIntake)){
            currentUser.setCalorieIntake(Integer.parseInt(calorieIntake));
            return true;
        }
        return false;
    }

    public String generateReports(){
        StringBuilder builder = new StringBuilder();

        User currentUSer = getCurrentUser();

        if(currentUSer.getCalorieIntake() > 0){

            Report monthlyReport = reportFactory.getReport("MONTHLY");
            Report weeklyReport = reportFactory.getReport("WEEKLY");



        } else return "User calorie intake not set!";

        return builder.toString();
    }

}
