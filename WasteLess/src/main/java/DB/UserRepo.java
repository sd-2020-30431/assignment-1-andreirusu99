package DB;

import Model.Entities.GroceryItem;
import Model.Entities.GroceryList;
import Model.Entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.picocontainer.MutablePicoContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserRepo {

    private SessionFactory sessionFactory;
    private MutablePicoContainer picoContainer;

    public UserRepo(MutablePicoContainer container) {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        picoContainer = container;
    }

    /**
     *  Method to add an user record in the database
     */
    public Integer addUserToDB(String fname, String lname, String password, Set groceryLists){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            User user = picoContainer.getComponent(User.class);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setPassword(password);
            user.setGroceryLists(groceryLists);

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

    /**
     * Method to get all users from the database
     */
    public List getAllUsers(){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List users = picoContainer.getComponent(ArrayList.class);

        try {
            tx = session.beginTransaction();
            users = session.createQuery("FROM User").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    public void updateUserPasswordInDB(Integer UserID, String password ){
        Session session = sessionFactory.openSession();
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

    public boolean updateUserCalorieIntake(Integer UserID, Integer calories){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean successful = false;
        try {
            tx = session.beginTransaction();
            User user = session.get(User.class, UserID);
            user.setCalorieIntake(calories);
            session.update(user);
            tx.commit();
            successful = true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  successful;
    }

    public User getUser(Integer userId){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        User u = null;

        try {
            tx = session.beginTransaction();

            u = session.get(User.class, userId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return u;
    }

    public Set getUsersLists(Integer userId){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Set sets = null;

        try {
            tx = session.beginTransaction();

            User user = session.get(User.class, userId);
            sets = user.getGroceryLists();
            System.out.println(sets);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return sets;
    }

    public boolean addListToUser(Integer UserID, GroceryList newList ){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean successful = false;

        try {
            tx = session.beginTransaction();

            User user = session.get(User.class, UserID);
            Set lists = user.getGroceryLists();
            lists.add(newList);
            user.setGroceryLists(lists);
            session.update(user);
            tx.commit();
            successful = true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return successful;
    }

    public boolean addItemToList(Integer UserID, GroceryList theList, GroceryItem newItem ){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean successful = false;

        try {
            tx = session.beginTransaction();

            User user = session.get(User.class, UserID);
            Set lists = user.getGroceryLists();

            for(Object o : lists){
                GroceryList list = (GroceryList)o;
                Set items = list.getGroceryItems();;
                if(list.equals(theList)){
                    items.add(newItem);
                }
                list.setGroceryItems(items);
            }
            user.setGroceryLists(lists);

            session.update(user);
            tx.commit();
            successful = true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return successful;
    }

    /* Method to delete an user from the records */
    public void deleteUserfromDB(Integer UserID){
        Session session = sessionFactory.openSession();
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
