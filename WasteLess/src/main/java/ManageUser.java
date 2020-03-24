import java.sql.Date;
import java.util.*;

import Model.GroceryItem;
import Model.GroceryList;
import Model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageUser {
    
    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ManageUser MU = new ManageUser();

        HashSet groceryItems = new HashSet();
        Calendar c = Calendar.getInstance();
        Date d = new Date(c.getTimeInMillis());
        GroceryItem item1 = new GroceryItem("Milk", 1, 10, d, d, d);
        GroceryItem item2 = new GroceryItem("Eggs", 5, 20, d, d, d);
        groceryItems.add(item1);
        groceryItems.add(item2);

        /* Let us have a set of grocery lists for the first user  */
        HashSet groceryLists = new HashSet();
        GroceryList g1 = new GroceryList("MCA");
        g1.setGroceryItems(groceryItems);

        groceryLists.add(g1);


        /*MU.addGroceryList(g1);
        MU.addGroceryList(g2);
        MU.addGroceryList(g3);*/

        MU.addUser("Will", "Johnson", groceryLists);
        MU.listUsers();
        //MU.updateUser(1, "new_pass");
        //MU.deleteUser(1);
        //MU.listUsers();

    }

    public Integer addGroceryList(GroceryList list){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer listID = null;

        try {
            tx = session.beginTransaction();
            listID = (Integer) session.save(list);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listID;
    }

    /* Method to add an user record in the database */
    public Integer addUser(String fname, String lname,  Set groceryLists){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            User user = new User(fname, lname, "pass");
            user.setGroceryLists(groceryLists);

            userID = (Integer) session.save(user);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userID;
    }

    /* Method to list all the users detail */
    public void listUsers( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List users = session.createQuery("FROM User").list();
            for (Iterator iterator1 = users.iterator(); iterator1.hasNext();){
                User user = (User) iterator1.next();
                System.out.print("First Name: " + user.getFirstName() + " ");
                System.out.print("Last Name: " + user.getLastName() + " ");
                System.out.print("Password: " + user.getPassword() + "\n");

                Set groceryLists = user.getGroceryLists();
                for (Iterator iterator2 = groceryLists.iterator(); iterator2.hasNext();){
                    GroceryList listsName = (GroceryList) iterator2.next();
                    System.out.println("GroceryList: " + listsName.getName());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to update salary for an user */
    public void updateUser(Integer UserID, String password ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = session.get(User.class, UserID);
            user.setPassword( password );
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to delete an user from the records */
    public void deleteUser(Integer UserID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = session.get(User.class, UserID);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}