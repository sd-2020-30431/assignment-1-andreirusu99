package Model.Reports.ReportEntities;

import Interfaces.Report;
import Model.Entities.GroceryItem;
import Model.Entities.GroceryList;
import Model.Entities.User;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthlyReport implements Report {

    @Override
    public Integer computeWastedCalories(User user) {
        Integer calories = 0;
        ArrayList<GroceryItem> items = new ArrayList<>();

        for(Object o1 : user.getGroceryLists()){
            GroceryList list = (GroceryList) o1;
            for(Object o2 : list.getGroceryItems()){
                GroceryItem item = (GroceryItem) o2;
                items.add(item);
            }
        }

        Calendar now = Calendar.getInstance();

        for (GroceryItem item : items){
            Calendar then = Calendar.getInstance();
            then.setTime(item.getConsumptionDate());
            if(then.get(Calendar.MONTH) == now.get(Calendar.MONTH)) {
                calories += item.getCalorieValue() * item.getQuantity() - user.getCalorieIntake();
            }
        }

        return calories;
    }
}
