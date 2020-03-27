package UI;

import DB.UserRepo;
import Logic.ListsManager;
import Logic.ValidatorUtil;
import Model.GroceryItem;
import Model.GroceryList;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class ListsForm {
    private JPanel mainPanel;
    private JComboBox listSelector;
    private JTextField newItemNameTF;
    private JLabel newItemNameLabel;
    private JTextField newItemQuantityTF;
    private JTextField newItemCaloriesTF;
    private JLabel newItemQuantityLabel;
    private JLabel newItemCaloriesLabel;
    private JTextField newListNameTF;
    private JTextField newItemPurchaseDateTF;
    private JTextField newItemConsumptionDateTF;
    private JTextField newItemExpirationDateTF;
    private JButton addItemButton;
    private JButton newListButton;
    private JLabel newItemPurchaseLabel;
    private JLabel newItemConsumptionLabel;
    private JLabel newItemExpirationLabel;
    private JButton generateReportsButton;
    private JTextArea itemsTextArea;
    private ListsManager listsManager;

    public ListsForm(ListsManager manager){
        this.listsManager = manager;

        Set lists = listsManager.getCurrentUserLists();
        for(Object list : lists) {
            listSelector.addItem(list.toString());
        }

        if(lists.size() > 0) {
            fillTextArea(listSelector.getSelectedItem().toString());
        }

        newListButton.addActionListener(e ->  {
            String newListName = newListNameTF.getText();
            if(ValidatorUtil.isListNameValid(newListName)) {
                if (listsManager.addNewListToUser(newListName)) {
                    listSelector.addItem(newListName);
                    JOptionPane.showMessageDialog(null,
                            "New List " + "\"" + newListName + "\"" + " added successfully", "INFO",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // list with same name already present
                    JOptionPane.showMessageDialog(null,
                            "List already exists!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid new list name", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            newListNameTF.setText("");

        });

        addItemButton.addActionListener(e -> {
            String itemName = newItemNameTF.getText();
            String itemQuantity = newItemQuantityTF.getText();
            String itemCalorie = newItemCaloriesTF.getText();
            String purchaseDate = newItemPurchaseDateTF.getText();
            String consumptionDate = newItemConsumptionDateTF.getText();
            String expirationDate = newItemExpirationDateTF.getText();

            if(ValidatorUtil.isNameValid(itemName)
                && ValidatorUtil.isNumberValid(itemQuantity)
                && ValidatorUtil.isNumberValid(itemCalorie)
                && ValidatorUtil.isDateValid(purchaseDate)
                && ValidatorUtil.isDateValid(consumptionDate)
                && ValidatorUtil.isDateValid(expirationDate)
            ){
                if(listsManager.addNewItemToList(listSelector.getSelectedItem().toString(),
                        itemName, itemQuantity, itemCalorie, purchaseDate, consumptionDate, expirationDate)) {
                    fillTextArea(listSelector.getSelectedItem().toString());
                    JOptionPane.showMessageDialog(null,
                            "Item added successfully!", "INFO",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid data!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        generateReportsButton.addActionListener(e -> {

        });

        listSelector.addActionListener(e -> {
            System.out.println("Element " + listSelector.getSelectedItem().toString() + " selected.");
            fillTextArea(listSelector.getSelectedItem().toString());
        });
    }

    private void fillTextArea(String listName){
        GroceryList list = listsManager.getGroceryList(listName);
        StringBuilder text = new StringBuilder();

        for(Object o : list.getGroceryItems()){
            GroceryItem item = (GroceryItem)o;
            text.append(item.toString());
            text.append("\n");
        }
        itemsTextArea.setText(text.toString());
        itemsTextArea.setEditable(false);
        itemsTextArea.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
