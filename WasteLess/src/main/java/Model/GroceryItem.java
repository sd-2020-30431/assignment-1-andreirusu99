package Model;

import java.sql.Date;

public class GroceryItem {

    private int id;
    private String name;
    private Integer quantity;
    private Integer calorieValue;
    private Date purchaseDate;
    private Date consumptionDate;
    private Date expirationDate;

    public GroceryItem(){}

    public GroceryItem(String name, Integer quantity, Integer calorieValue, Date purchaseDate, Date consumptionDate, Date expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.calorieValue = calorieValue;
        this.purchaseDate = purchaseDate;
        this.consumptionDate = consumptionDate;
        this.expirationDate = expirationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCalorieValue(Integer calorieValue) {
        this.calorieValue = calorieValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCalorieValue() {
        return calorieValue;
    }

    public void setCalorieValue(int calorieValue) {
        this.calorieValue = calorieValue;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    @Override
    public int hashCode() {
        int tmp;
        tmp = ( id + name ).hashCode();
        return tmp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        GroceryItem obj2 = (GroceryItem) obj;
        return((this.id == obj2.getId()) && (this.name.equals(obj2.getName())));
    }

    @Override
    public String toString() {
        return name + ", "
                + quantity.toString() + ", "
                + calorieValue + " cal, "
                + purchaseDate + ", "
                + consumptionDate + ", "
                + expirationDate;
    }
}
