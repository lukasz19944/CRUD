package crud.view;

import crud.MainApp;
import crud.dao.UserDao;
import crud.model.User;
import crud.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserOverviewController {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label birthdayLabel;

    private MainApp mainApp;

    public UserOverviewController() {

    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));

        showUserDetails(null);

        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        userTable.setItems(mainApp.getUserData());
    }

    private void showUserDetails(User user) {
        if (user != null) {
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            emailLabel.setText(user.getEmail());
            birthdayLabel.setText(DateUtil.format(user.getBirthday()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            emailLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteUser() {
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            userTable.getItems().remove(selectedIndex);

            UserDao dao = new UserDao();
            dao.deleteUser(userTable.getSelectionModel().getSelectedItem().getId() + 1);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No User Selected");
            alert.setContentText("Please select an user in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewUser() {
        User tempUser = new User();
        boolean saveClicked = mainApp.showUserEditDialog(tempUser);

        if (saveClicked) {
            mainApp.getUserData().add(tempUser);
        }
    }

    @FXML
    private void handleEditUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            boolean saveClicked = mainApp.showUserEditDialog(selectedUser);

            if (saveClicked) {
                showUserDetails(selectedUser);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No User Selected");
            alert.setContentText("Please select an user in the table.");

            alert.showAndWait();
        }
    }
}
