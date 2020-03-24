package Model;

import java.util.Set;

public class GroceryList {

    private int id;
    private String name;
    private Set groceryItems;

    public GroceryList() {}

    public GroceryList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set getGroceryItems() {
        return groceryItems;
    }

    public void setGroceryItems(Set groceryItems) {
        this.groceryItems = groceryItems;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        GroceryList obj2 = (GroceryList)obj;
        return ((this.id == obj2.getId()) && (this.name.equals(obj2.getName())));
    }

    @Override
    public int hashCode() {
        int tmp;
        tmp = ( id + name ).hashCode();
        return tmp;
    }
}