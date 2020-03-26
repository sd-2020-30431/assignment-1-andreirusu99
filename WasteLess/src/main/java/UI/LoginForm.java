package UI;

import Logic.Login;

import javax.swing.*;

public class LoginForm {

    private JButton submitButton;
    private JTextField firstNameTF;
    private JPasswordField passwordTF;
    private JTextField lastNameTF;
    private JLabel passwordLabel;
    private JLabel lastNameLabel;
    private JLabel firstNameLabel;
    private JLabel loginTextLabel;
    private JPanel mainPanel;


    public LoginForm(Login login) {

        submitButton.addActionListener(e -> {
            String fname = firstNameTF.getText();
            String lname = lastNameTF.getText();
            String password = passwordTF.getText();

            String response = login.attemptLogInUser(fname, lname, password);

            JOptionPane.showMessageDialog(null, response, "Login result", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
