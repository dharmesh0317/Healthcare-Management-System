import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class HealthManagementSystem extends Frame implements ActionListener {
    // Login components
    Label labelUser = new Label("User ID:");
    Label labelPass = new Label("Password:");
    TextField userField = new TextField();
    TextField passField = new TextField();
    Button loginBtn = new Button("Login");
    Label errorLabel = new Label("");

    // Panels
    Panel loginPanel = new Panel();
    Panel menuPanel = new Panel();
    Panel addPanel = new Panel();
    Panel searchPanel = new Panel();
    Panel resultPanel = new Panel();

    // Menu buttons
    Button addBtn = new Button("Add Patient");
    Button searchBtn = new Button("Search Patient");

    // Add Patient Fields
    TextField pidField = new TextField();
    TextField nameField = new TextField();
    TextField ageField = new TextField();
    TextField sexField = new TextField();
    TextField contactField = new TextField();
    TextField emailField = new TextField();
    TextField addressField = new TextField();
    TextField diagnosisField = new TextField();
    Button addSubmitBtn = new Button("Submit");
    Button addBackBtn = new Button("Back");
    Label addStatus = new Label("");

    // Search Panel
    TextField searchIdField = new TextField();
    Button searchSubmitBtn = new Button("Submit");
    Button searchBackBtn = new Button("Back");
    Label notFoundLabel = new Label("");

    // Search Result Panel
    Label[] labels = new Label[8];
    Label[] values = new Label[8];
    Button resultBackBtn = new Button("Back");

    public HealthManagementSystem() {
        setTitle("Health Management System");
        setSize(400, 450);
        setLayout(null);

        // Login Panel
        loginPanel.setBounds(50, 50, 300, 200);
        loginPanel.setLayout(new GridLayout(4, 2));
        passField.setEchoChar('*');
        loginPanel.add(labelUser);
        loginPanel.add(userField);
        loginPanel.add(labelPass);
        loginPanel.add(passField);
        loginPanel.add(loginBtn);
        loginPanel.add(errorLabel);
        errorLabel.setForeground(Color.RED);
        add(loginPanel);
        loginBtn.addActionListener(this);

        // Menu Panel
        menuPanel.setBounds(50, 100, 300, 200);
        menuPanel.setLayout(new GridLayout(2, 1));
        menuPanel.add(addBtn);
        menuPanel.add(searchBtn);
        menuPanel.setVisible(false);
        add(menuPanel);
        addBtn.addActionListener(this);
        searchBtn.addActionListener(this);

        // Add Patient Panel
        addPanel.setLayout(new GridLayout(10, 2));
        addPanel.setBounds(20, 30, 360, 350);
        addPanel.add(new Label("Patient ID:")); addPanel.add(pidField);
        addPanel.add(new Label("Name:")); addPanel.add(nameField);
        addPanel.add(new Label("Age:")); addPanel.add(ageField);
        addPanel.add(new Label("Sex:")); addPanel.add(sexField);
        addPanel.add(new Label("Contact No:")); addPanel.add(contactField);
        addPanel.add(new Label("Email ID:")); addPanel.add(emailField);
        addPanel.add(new Label("Address:")); addPanel.add(addressField);
        addPanel.add(new Label("Diagnosis:")); addPanel.add(diagnosisField);
        addSubmitBtn.setBackground(Color.GREEN);
        addBackBtn.setBackground(Color.BLACK);
        addBackBtn.setForeground(Color.WHITE);
        addPanel.add(addSubmitBtn); addPanel.add(addBackBtn);
        addPanel.add(addStatus);
        addPanel.setVisible(false);
        add(addPanel);
        addSubmitBtn.addActionListener(this);
        addBackBtn.addActionListener(this);

        // Search Panel
        searchPanel.setLayout(new GridLayout(4, 1));
        searchPanel.setBounds(50, 100, 300, 150);
        searchPanel.add(new Label("Enter Patient ID:"));
        searchPanel.add(searchIdField);
        searchSubmitBtn.setBackground(Color.GREEN);
        searchBackBtn.setBackground(Color.BLACK);
        searchBackBtn.setForeground(Color.WHITE);
        searchPanel.add(searchSubmitBtn);
        searchPanel.add(searchBackBtn);
        notFoundLabel.setForeground(Color.RED);
        searchPanel.add(notFoundLabel);
        searchPanel.setVisible(false);
        add(searchPanel);
        searchSubmitBtn.addActionListener(this);
        searchBackBtn.addActionListener(this);

        // Result Panel
        resultPanel.setLayout(new GridLayout(9, 2));
        resultPanel.setBounds(20, 30, 360, 330);
        String[] fieldNames = {
            "Patient ID", "Name", "Age", "Sex", "Contact No", "Email", "Address", "Diagnosis"
        };
        for (int i = 0; i < 8; i++) {
            labels[i] = new Label(fieldNames[i] + ":");
            values[i] = new Label("");
            resultPanel.add(labels[i]);
            resultPanel.add(values[i]);
        }
        resultBackBtn.setBackground(Color.BLACK);
        resultBackBtn.setForeground(Color.WHITE);
        resultPanel.add(resultBackBtn);
        resultPanel.add(new Label(""));
        resultPanel.setVisible(false);
        add(resultPanel);
        resultBackBtn.addActionListener(this);

        // Close handler
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String user = userField.getText().trim();
            String pass = passField.getText().trim();
            if (checkLogin(user, pass)) {
                loginPanel.setVisible(false);
                menuPanel.setVisible(true);
                errorLabel.setText("");
            } else {
                errorLabel.setText("Wrong Credentials");
            }
        } else if (e.getSource() == addBtn) {
            menuPanel.setVisible(false);
            addPanel.setVisible(true);
            addStatus.setText("");
        } else if (e.getSource() == addSubmitBtn) {
            String id = pidField.getText().trim();
            if (checkPatientExists(id)) {
                addStatus.setForeground(Color.RED);
                addStatus.setText("Patient already exists");
            } else {
                addPatient();
                addStatus.setForeground(Color.GREEN);
                addStatus.setText("Patient details saved");
            }
        } else if (e.getSource() == addBackBtn) {
            clearAddFields();
            addPanel.setVisible(false);
            menuPanel.setVisible(true);
        } else if (e.getSource() == searchBtn) {
            menuPanel.setVisible(false);
            searchPanel.setVisible(true);
            notFoundLabel.setText("");
        } else if (e.getSource() == searchSubmitBtn) {
            String id = searchIdField.getText().trim();
            String[] data = getPatientData(id);
            if (data != null) {
                for (int i = 0; i < 8; i++) {
                    values[i].setText(data[i]);
                }
                searchPanel.setVisible(false);
                resultPanel.setVisible(true);
                notFoundLabel.setText("");
            } else {
                notFoundLabel.setText("Patient not found");
            }
        } else if (e.getSource() == searchBackBtn) {
            searchPanel.setVisible(false);
            menuPanel.setVisible(true);
            notFoundLabel.setText("");
        } else if (e.getSource() == resultBackBtn) {
            resultPanel.setVisible(false);
            searchPanel.setVisible(true);
        }
    }

    private boolean checkLogin(String user, String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader("admin.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(user) && parts[1].trim().equals(pass)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading admin.csv");
        }
        return false;
    }

    private boolean checkPatientExists(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader("patient.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private void addPatient() {
        try (FileWriter fw = new FileWriter("patient.csv", true)) {
            StringBuilder sb = new StringBuilder();
            sb.append(pidField.getText().trim()).append(",");
            sb.append(nameField.getText().trim()).append(",");
            sb.append(ageField.getText().trim()).append(",");
            sb.append(sexField.getText().trim()).append(",");
            sb.append(contactField.getText().trim()).append(",");
            sb.append(emailField.getText().trim()).append(",");
            sb.append(addressField.getText().trim()).append(",");
            sb.append(diagnosisField.getText().trim()).append("\n");
            fw.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Error writing to patient.csv");
        }
    }

    private String[] getPatientData(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader("patient.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    return line.split(",", -1);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    private void clearAddFields() {
        pidField.setText("");
        nameField.setText("");
        ageField.setText("");
        sexField.setText("");
        contactField.setText("");
        emailField.setText("");
        addressField.setText("");
        diagnosisField.setText("");
    }

    public static void main(String[] args) {
        new HealthManagementSystem();
    }
}
