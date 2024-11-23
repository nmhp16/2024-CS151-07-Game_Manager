package com.game.ui;

import com.game.GameManagerController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoginPageUI {
    private BorderPane loginPage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button createAccountButton;
    // Styling
    private String buttonStyle = "-fx-background-color: #4ca1af; -fx-text-fill: white; -fx-background-radius: 10px;";
    private String buttonHoverStyle = "-fx-background-color: #3b8a9a; -fx-text-fill: white; -fx-background-radius: 10px;";
    private String fieldStyle = "-fx-background-color: white; -fx-border-color: lightgrey; -fx-border-radius: 10px; -fx-background-radius: 10px;";
    private String backgroundStyle = "-fx-background-color: linear-gradient(to bottom right, #34495e, #5bc0de);";

    public LoginPageUI(GameManagerController controller) {
        // Initialize UI Components
        loginPage = new BorderPane();
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        createAccountButton = new Button("Create Account");

        setupUI(controller);
    }

    /**
     * Retrieves the login page layout.
     *
     * @return A BorderPane containing the login page UI components.
     */
    public BorderPane getLoginPage() {
        return loginPage;
    }

    /**
     * Sets up the UI components for the login page and adds them to the BorderPane
     * loginPage.
     * 
     * @param controller The GameManagerController to handle login and create
     *                   account events.
     */
    private void setupUI(GameManagerController controller) {
        // Center login page
        GridPane gridPane = createGridPane();

        addInputFields(gridPane);

        // Login Button
        configureButton(loginButton);

        // Create Account Button
        configureButton(createAccountButton);

        // Create title for login page
        Label title = new Label("Game Manager Login");
        title.setFont(new Font("Georgia", 30));
        title.setTextFill(Color.WHITE);

        // Add element to Border Pane login page
        VBox vbox = new VBox(title, gridPane);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        loginPage.setStyle(backgroundStyle);
        loginPage.setCenter(vbox);

        // Set up button actions with controller
        loginButton.setOnAction(event -> {
            GameManagerController.username = usernameField.getText(); // Set username controller
            controller.handleLogin(usernameField.getText(), passwordField.getText());
        });
        createAccountButton.setOnAction(event -> {
            controller.handleCreateAccount(usernameField.getText(), passwordField.getText());
        });
    }

    /**
     * Creates and configures a GridPane for the login page layout.
     * 
     * @return A GridPane aligned to the center with specified horizontal and
     *         vertical gaps.
     */
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10); // Gap between columns
        gridPane.setVgap(10); // Gap between rows
        return gridPane;
    }

    /**
     * Adds input fields to the GridPane for the login page.
     * 
     * Adds icons for username and password, and creates labels for the fields.
     * Puts the labels and fields into the GridPane in the specified format.
     * 
     * @param gridPane The GridPane to add the input fields to.
     */
    private void addInputFields(GridPane gridPane) {
        // Add icon for username and password
        Label usernameIcon = createIconLabel("👤");
        Label passwordIcon = createIconLabel("🔒");

        // Create account element
        Label username = createFieldLabel("Username");
        Label password = createFieldLabel("Password");

        usernameField.setStyle(fieldStyle);
        passwordField.setStyle(fieldStyle);

        // Put account element into Grid Pane in format (field, column, row)
        gridPane.add(username, 1, 1);
        gridPane.add(usernameIcon, 0, 2);
        gridPane.add(usernameField, 1, 2);
        gridPane.add(password, 1, 3);
        gridPane.add(passwordIcon, 0, 4);
        gridPane.add(passwordField, 1, 4);
        gridPane.add(loginButton, 1, 6);
        gridPane.add(createAccountButton, 1, 7);
    }

    /**
     * Creates a label with an icon represented by the given text.
     *
     * @param text The text representing the icon.
     * @return A Label with the specified icon text, styled with a font size of 24
     *         and white text color.
     */
    private Label createIconLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(24));
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Creates a label with a given text for a field on the login page.
     * 
     * @param text The text of the label.
     * @return A Label with the specified text, styled with the font "Georgia" with
     *         size 20 and white text color.
     */
    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Georgia", 20));
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Configures the visual properties of a button, including its size, font, and
     * style.
     * Also adds a hover effect that changes the button's style when the mouse is
     * over it,
     * and a focus effect that changes the button's style when it is focused.
     * 
     * @param button The button to be configured.
     */
    private void configureButton(Button button) {
        button.setPrefWidth(200);
        button.setFont(new Font("Georgia", 20));
        button.setStyle(buttonStyle);
        button.setPrefSize(200, 40);

        button.setOnMouseEntered(
                event -> button.setStyle(buttonHoverStyle));
        button.setOnMouseExited(
                event -> button.setStyle(buttonStyle));

        // Hover effect when focused
        button.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle(buttonHoverStyle);
            } else {
                button.setStyle(buttonStyle);
            }
        });
    }
}
